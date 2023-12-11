package com.example.unsplash.data.data_sources.photo

import com.example.unsplash.data.paging_source.AppPagingConfig
import com.example.unsplash.presentation.fragment_photo_details.models.PhotoDetails
import com.example.unsplash.presentation.fragment_photos_main.models.Photo

interface PhotoDatasourceRemote {

    suspend fun requestPhotosTypeSome(config: AppPagingConfig): List<Photo>

    suspend fun requestPhotosTypeSearch(config: AppPagingConfig): List<Photo>

    suspend fun requestPhotosTypeFromCollection(config: AppPagingConfig): List<Photo>

    suspend fun requestDetailedPhotoInfoById(photoId: String): PhotoDetails

    suspend fun requestLikedPhotos(config: AppPagingConfig): List<Photo>

}
