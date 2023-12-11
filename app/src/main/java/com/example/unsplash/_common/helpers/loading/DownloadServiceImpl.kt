package com.example.unsplash._common.helpers.loading

import android.app.DownloadManager
import android.app.DownloadManager.Request
import android.content.Context
import android.net.Uri
import android.os.Environment
import com.example.unsplash.data.storages.LoadIdStorage
import com.example.unsplash.presentation._common.models.LoadingPhoto
import com.example.unsplash.presentation._common.models.PhotoToLoading

class DownloadServiceImpl(
    private val loadIdStorage: LoadIdStorage,
    private val context: Context,
) : DownloadService {

    private val downloadManager by lazy {
	  context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
    }

    override fun initStartLoading(photoToLoading: PhotoToLoading) {
	  val loadingRequest = createDownloadRequest(photoToLoading)
	  val loadId = downloadManager.enqueue(loadingRequest)
	  loadIdStorage.commitLoadStarted(
		LoadingPhoto(
		    loadId = loadId,
		    photoId = photoToLoading.id,
		    urlToPreview = photoToLoading.urlToPreview
		)
	  )
    }

    private fun createDownloadRequest(photoToLoading: PhotoToLoading): Request {
	  return Request(Uri.parse(photoToLoading.urlToLoading))
		.setAllowedOverMetered(true)
		.setDestinationInExternalPublicDir(
		    Environment.DIRECTORY_PICTURES,
		    photoToLoading.id + ".jpeg"
		)
		.setMimeType("image/jpeg")
    }

}