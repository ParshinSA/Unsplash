package com.example.unsplash._common.helpers.notifications

import com.example.unsplash.presentation._common.models.LoadedPhoto
import com.example.unsplash.presentation._common.models.LoadingPhoto

interface DownloadNotification {

    fun sendLoadStarted(loadingPhoto: LoadingPhoto)

    fun sendLoadCompleted(loadedPhoto: LoadedPhoto)

}
