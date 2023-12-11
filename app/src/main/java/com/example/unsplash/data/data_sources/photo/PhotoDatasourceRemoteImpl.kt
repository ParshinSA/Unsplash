package com.example.unsplash.data.data_sources.photo

import com.example.unsplash.data.models.converters.toUiModel
import com.example.unsplash.data.models.dto.PhotoDto
import com.example.unsplash.data.network.unsplash.api.PhotosServiceRemote
import com.example.unsplash.data.paging_source.AppPagingConfig
import com.example.unsplash.presentation.fragment_photo_details.models.PhotoDetails
import com.example.unsplash.presentation.fragment_photos_main.models.Photo
import java.io.IOException

class PhotoDatasourceRemoteImpl(
    private val photosServiceRemote: PhotosServiceRemote,
) : PhotoDatasourceRemote {

    override suspend fun requestPhotosTypeSome(config: AppPagingConfig): List<Photo> {
	  val response = photosServiceRemote
		.requestPhotos(
		    pageNumber = config.pageNumber,
		    pageSize = config.pageSize
		)
	  return if (response.isSuccessful) response.body().toListPhoto()
	  else throw IOException(response.message())
    }

    override suspend fun requestDetailedPhotoInfoById(photoId: String): PhotoDetails {
	  val response = photosServiceRemote.requestDetailsPhotoById(photoId)
	  return if (response.isSuccessful) response.body()?.toUiModel()
		?: throw IOException(response.message())
	  else throw IOException(response.message())
    }

    override suspend fun requestLikedPhotos(config: AppPagingConfig): List<Photo> {
	  val response = photosServiceRemote
		.requestLikedPhotos(
		    param = config.param,
		    pageNumber = config.pageNumber,
		    pageSize = config.pageSize,
		)
	  return if (response.isSuccessful) response.body().toListPhoto()
	  else throw IOException(response.message())
    }

    override suspend fun requestPhotosTypeFromCollection(config: AppPagingConfig): List<Photo> {
	  val response = photosServiceRemote
		.requestPhotosFromCollection(
		    param = config.param,
		    pageNumber = config.pageNumber,
		    pageSize = config.pageSize
		)
	  return if (response.isSuccessful) response.body().toListPhoto()
	  else throw IOException(response.message())
    }

    override suspend fun requestPhotosTypeSearch(config: AppPagingConfig): List<Photo> {
	  val response = photosServiceRemote
		.requestPhotosByQuery(
		    param = config.param,
		    pageNumber = config.pageNumber,
		    pageSize = config.pageSize
		)
	  return if (response.isSuccessful) response.body()?.result.toListPhoto()
	  else throw IOException(response.message())
    }

    private fun List<PhotoDto>?.toListPhoto(): List<Photo> {
	  if (this == null) return emptyList()
	  return this.mapNotNull { photoDto: PhotoDto ->
		photoDto.toUiModel()
	  }
    }

}