package com.example.unsplash.data.data_sources.collection

import com.example.unsplash._common.extensions.launchIO
import com.example.unsplash._common.helpers.loading.HelperWithCachingFile
import com.example.unsplash.data.models.converters.toUiModel
import com.example.unsplash.data.room_db.entity_list_photos.PhotoEntity
import com.example.unsplash.data.room_db.entity_list_photos_collection.PhotosCollectionsEntity
import com.example.unsplash.data.room_db.entity_list_photos_collection.PhotosCollectionsDao
import com.example.unsplash.presentation.fragment_collections.models.PhotosCollection
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

class CollectionsDatasourceLocalImpl(
    private val helperWithCachingFile: HelperWithCachingFile,
    private val collectionsServiceDao: PhotosCollectionsDao,
) : CollectionsDatasourceLocal {

    private val coroutineScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    override fun saveCollections(collections: List<PhotosCollection>) {
	  coroutineScope.launchIO(
		action = {
		    checkCountItemsInDb(collections.size)
		    saveCollectionsInDb(collections)
		},
		onError = { it.printStackTrace() })
    }

    override suspend fun requestCollections(): List<PhotosCollection> {
	  return collectionsServiceDao.requestAllPhotosCollections()
		.map { photosCollectionsEntity: PhotosCollectionsEntity ->
		    photosCollectionsEntity.toUiModel()
		}
    }

    private suspend fun saveCollectionsInDb(collections: List<PhotosCollection>) {
	  collectionsServiceDao.saveCollections(collections.map { photosCollection: PhotosCollection ->

		val listPhotosCollectionEntity =
		    photosCollection.previewPhotos.mapNotNull { url: String ->
			  helperWithCachingFile.loadFile(
				url = url,
				nameFile = getFileName(photosCollection, url)
			  )
		    }

		PhotosCollectionsEntity(
		    collectionId = photosCollection.collectionId,
		    title = photosCollection.title,
		    commonInfo = photosCollection.commonInfo,
		    previewPhotos = listPhotosCollectionEntity
		)
	  })
    }

    private fun getFileName(collection: PhotosCollection, url: String): String {
	  val index = collection.previewPhotos.indexOf(url)
	  val collectionId = collection.collectionId
	  return collectionId + index.toString()
    }

    private fun getFileName(collection: PhotosCollectionsEntity, url: String): String {
	  val index = collection.previewPhotos.indexOf(url)
	  val collectionId = collection.collectionId
	  return collectionId + index.toString()
    }

    /** Проверяем кол-во элементов в БД, если больше MAX_ITEMS удаляем первые sizeNewList элементов */
    private suspend fun checkCountItemsInDb(sizeNewList: Int) {
	  val countItems = collectionsServiceDao.receiveCountOfItemsInTable()
	  if (countItems >= PhotoEntity.Table.MAX_ITEMS)
		deleteItemsFromTableAndDownloadDirectory(sizeNewList)
    }

    /** Удаляем скаченные файлы */
    private suspend fun deleteItemsFromTableAndDownloadDirectory(sizeNewList: Int) {
	  val listPhotoEntity = collectionsServiceDao.requestAllPhotosCollections().take(sizeNewList)
	  collectionsServiceDao.deleteListPhotos(listPhotoEntity)
	  listPhotoEntity.forEach { photoEntity ->
		photoEntity.previewPhotos.forEach { urlToFile ->
		    helperWithCachingFile.deleteFile(getFileName(photoEntity, urlToFile))
		}
	  }
    }

}