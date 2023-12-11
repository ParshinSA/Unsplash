package com.example.unsplash.data.data_sources.photo

import com.example.unsplash._common.extensions.launchIO
import com.example.unsplash._common.helpers.loading.HelperWithCachingFile
import com.example.unsplash.data.models.converters.toUiModel
import com.example.unsplash.data.room_db.entity_list_photos.PhotoEntity
import com.example.unsplash.data.room_db.entity_list_photos.PhotosDao
import com.example.unsplash.presentation.fragment_photos_main.models.Photo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

class PhotoDatasourceLocalImpl(
    private val helperWithCachingFile: HelperWithCachingFile,
    private val photosDao: PhotosDao,
) : PhotoDatasourceLocal {

    private val coroutineScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    override fun savePhotos(photoList: List<Photo>) {
	  coroutineScope.launchIO(
		action = {
		    checkCountItemsInDb(photoList.size)
		    saveListPhotosInDb(photoList)
		},
		onError = { it.printStackTrace() })
    }

    private suspend fun saveListPhotosInDb(photoList: List<Photo>) {
	  photosDao.saveListPhotos(photoList.map { itemPhoto: Photo ->
		val urlImagePhoto = helperWithCachingFile.loadFile(
		    url = itemPhoto.photoResource,
		    nameFile = getPhotoNameFile(itemPhoto)
		) ?: return

		val urlPhotoPhotographerProfile = helperWithCachingFile.loadFile(
		    url = itemPhoto.photoResourcePhotographerProfile,
		    nameFile = getPhotographerPhotoNameFile(itemPhoto)
		) ?: return

		PhotoEntity(
		    photoID = itemPhoto.photoID,
		    counterLikes = itemPhoto.likes,
		    urlImagePhoto = urlImagePhoto,
		    urlPhotoPhotographerProfile = urlPhotoPhotographerProfile,
		    photographerName = itemPhoto.photographerName,
		    likedByUser = itemPhoto.likedByUser
		)
	  })
    }

    override suspend fun requestPhotos(): List<Photo> {
	  return photosDao.requestAllPhotos()
		.map { photoEntity: PhotoEntity ->
		    photoEntity.toUiModel()
		}
    }

    /** Проверяем кол-во элементов в БД, если больше MAX_ITEMS удаляем первые sizeNewList элементов */
    private suspend fun checkCountItemsInDb(sizeNewList: Int) {
	  val countItems = photosDao.receiveCountOfItemsInTable()
	  if (countItems >= PhotoEntity.Table.MAX_ITEMS)
		deleteItemsFromTableAndDownloadDirectory(sizeNewList)
    }

    /** Удаляем скаченные файлы */
    private suspend fun deleteItemsFromTableAndDownloadDirectory(sizeNewList: Int) {
	  val listPhotoEntity = photosDao.requestAllPhotos().take(sizeNewList)
	  photosDao.deleteListPhotos(listPhotoEntity)
	  listPhotoEntity.forEach { photoEntity ->
		helperWithCachingFile.deleteFile(getPhotoNameFile(photoEntity))
		helperWithCachingFile.deleteFile(getPhotographerPhotoNameFile(photoEntity))
	  }
    }

    private fun getPhotoNameFile(photo: Photo): String {
	  return photo.photoID
    }

    private fun getPhotographerPhotoNameFile(photo: Photo): String {
	  return photo.photoID + photo.photographerName
    }

    private fun getPhotoNameFile(photo: PhotoEntity): String {
	  return photo.photoID
    }

    private fun getPhotographerPhotoNameFile(photo: PhotoEntity): String {
	  return photo.photoID + photo.photographerName
    }

}