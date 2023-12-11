package com.example.unsplash.data.repositories

import androidx.paging.PagingData
import com.example.unsplash._common.helpers.connection_state.ConnectionStateProvider
import com.example.unsplash._common.helpers.loading.DownloadService
import com.example.unsplash._common.helpers.local_like_state.LocalLikeStateProvider
import com.example.unsplash.data.models.converters.toUiModel
import com.example.unsplash.data.network.unsplash.api.PhotosServiceRemote
import com.example.unsplash.presentation._common.models.LikeInfo
import com.example.unsplash.presentation._common.models.PhotoToLoading
import com.example.unsplash.presentation._common.some_components.Request
import com.example.unsplash.presentation._external_dependencies.PhotosRepository
import com.example.unsplash.presentation.fragment_photo_details.models.PhotoDetails
import com.example.unsplash.presentation.fragment_photos_main.models.Photo
import com.example.unsplash.presentation.fragment_photos_main.pagination.PhotosLocalPagerFactory
import com.example.unsplash.presentation.fragment_photos_main.pagination.PhotosRemotePagerFactory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOf

class PhotosRepositoryImpl(
    private val connectionStateProvider: ConnectionStateProvider,
    private val localLikeStateProvider: LocalLikeStateProvider,
    private val remotePager: PhotosRemotePagerFactory,
    private val localPager: PhotosLocalPagerFactory,
    private val photosService: PhotosServiceRemote,
    private val downloadService: DownloadService,
) : PhotosRepository {

    override fun initStartLoading(photoToLoading: PhotoToLoading) {
	  downloadService.initStartLoading(photoToLoading)
    }

    override suspend fun requestPhotoDetailsById(photoId: String): PhotoDetails? {
	  connectionStateProvider.isDeviceOnline() ?: return null
	  return photosService.requestDetailsPhotoById(photoId).body().toUiModel()
    }

    override suspend fun changeLike(oldLikeInfo: LikeInfo) {
	  connectionStateProvider.isDeviceOnline() ?: return
	  localLikeStateProvider.changeLike(oldLikeInfo)
    }

    override suspend fun listenToNewLikeInfo(): StateFlow<LikeInfo?> {
	  return localLikeStateProvider.listenToNewLikeInfo()
    }

    override fun requestSimplePhotosPager(): Flow<PagingData<Photo>> {
	  return if (connectionStateProvider.isDeviceOnline() == true)
		remotePager.create(Request.SimpleRequest)
	  else localPager.create()
    }

    override fun requestSearchPhotosPager(query: String?): Flow<PagingData<Photo>> {
	  query ?: return flowOf(PagingData.empty())
	  connectionStateProvider.isDeviceOnline() ?: return flowOf(PagingData.empty())
	  return remotePager.create(Request.SearchRequest(query))
    }

    override fun requestPhotosFromCollection(id: String?): Flow<PagingData<Photo>> {
	  id ?: return flowOf(PagingData.empty())
	  connectionStateProvider.isDeviceOnline() ?: return flowOf(PagingData.empty())
	  return remotePager.create(Request.PhotosByCollectionIdRequest(id))
    }

    override fun requestLikedPhotosPager(username: String?): Flow<PagingData<Photo>> {
	  username ?: return flowOf(PagingData.empty())
	  connectionStateProvider.isDeviceOnline() ?: return flowOf(PagingData.empty())
	  return remotePager.create(Request.LikedPhotosRequest(username))
    }

}