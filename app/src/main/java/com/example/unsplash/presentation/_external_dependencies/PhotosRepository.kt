package com.example.unsplash.presentation._external_dependencies

import androidx.paging.PagingData
import com.example.unsplash.presentation._common.models.LikeInfo
import com.example.unsplash.presentation._common.models.PhotoToLoading
import com.example.unsplash.presentation.fragment_photo_details.models.PhotoDetails
import com.example.unsplash.presentation.fragment_photos_main.models.Photo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface PhotosRepository {

    fun initStartLoading(photoToLoading: PhotoToLoading)

    suspend fun requestPhotoDetailsById(photoId: String): PhotoDetails?

    suspend fun changeLike(oldLikeInfo: LikeInfo)

    suspend fun listenToNewLikeInfo(): StateFlow<LikeInfo?>

    fun requestSimplePhotosPager(): Flow<PagingData<Photo>>

    fun requestSearchPhotosPager(query: String?): Flow<PagingData<Photo>>

    fun requestPhotosFromCollection(id: String?): Flow<PagingData<Photo>>

    fun requestLikedPhotosPager(username: String?): Flow<PagingData<Photo>>
}
