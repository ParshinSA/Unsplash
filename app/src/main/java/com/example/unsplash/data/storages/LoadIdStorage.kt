package com.example.unsplash.data.storages

import com.example.unsplash.presentation._common.models.LoadedPhoto
import com.example.unsplash.presentation._common.models.LoadingPhoto
import kotlinx.coroutines.flow.Flow

interface LoadIdStorage {

    fun getLoadingPhoto(loadID: Long): LoadingPhoto?

    fun commitLoadStarted(loadingPhoto: LoadingPhoto)

    fun commitLoadCompleted(loadedPhoto: LoadedPhoto)

    fun observeToStorage(): Flow<List<LoadingPhoto>>

    fun isLoadingPhoto(photoId: String): Boolean
}
