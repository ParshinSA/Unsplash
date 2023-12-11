package com.example.unsplash.data.storages

import android.app.DownloadManager
import android.app.DownloadManager.*
import android.content.Context
import androidx.core.database.getIntOrNull
import com.example.unsplash._common.helpers.notifications.DownloadNotification
import com.example.unsplash.data.models.converters.toLoadingPhoto
import com.example.unsplash.data.models.converters.toLoadingPhotoEntity
import com.example.unsplash.data.room_db.entity_loding_photos.LoadingPhotoDao
import com.example.unsplash.data.room_db.entity_loding_photos.LoadingPhotoEntity
import com.example.unsplash.presentation._common.models.LoadedPhoto
import com.example.unsplash.presentation._common.models.LoadingPhoto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LoadIdStorageImpl(
    private val downloadNotification: DownloadNotification,
    private val loadingPhotoDao: LoadingPhotoDao,
    private val context: Context,
) : LoadIdStorage {

    override fun observeToStorage(): Flow<List<LoadingPhoto>> {
	  checkLoadingStorage()
	  return loadingPhotoDao.observeToChangeData()
		.map { listLoadingPhotoEntity: List<LoadingPhotoEntity> ->
		    listLoadingPhotoEntity.map { loadingPhotoEntity: LoadingPhotoEntity ->
			  loadingPhotoEntity.toLoadingPhoto()
		    }
		}
    }

    private fun checkLoadingStorage() {
	  val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager

	  for (loadingPhotoEntity in loadingPhotoDao.retrieveAllLoadings()) {
		try {
		    val query = Query().setFilterById(loadingPhotoEntity.loadId)
		    downloadManager.query(query).use { cursor ->
			  if (cursor.moveToFirst()) {
				val status = cursor.getIntOrNull(cursor.getColumnIndex(COLUMN_STATUS))

				if (status == STATUS_FAILED || status == STATUS_SUCCESSFUL) {
				    loadingPhotoDao.removeLoading(loadingPhotoEntity)
				}
			  }
		    }
		} catch (t: Throwable) {
		    continue
		}
	  }
    }

    override fun isLoadingPhoto(photoId: String): Boolean {
	  val allLoadings = loadingPhotoDao.retrieveAllLoadings()
	  return allLoadings.firstOrNull { it.photoId == photoId } != null
    }

    override fun commitLoadStarted(loadingPhoto: LoadingPhoto) {
	  downloadNotification.sendLoadStarted(loadingPhoto)
	  loadingPhotoDao.addLoading(loadingPhoto.toLoadingPhotoEntity())
    }

    override fun commitLoadCompleted(loadedPhoto: LoadedPhoto) {
	  downloadNotification.sendLoadCompleted(loadedPhoto)
	  loadingPhotoDao.removeLoading(loadedPhoto.toLoadingPhotoEntity())
    }

    override fun getLoadingPhoto(loadID: Long): LoadingPhoto? {
	  val allLoadings = loadingPhotoDao.retrieveAllLoadings()
	  val loadingPhotoEntity = allLoadings.firstOrNull { it.loadId == loadID } ?: return null
	  return loadingPhotoEntity.toLoadingPhoto()
    }

}