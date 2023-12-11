package com.example.unsplash.data.paging_source

import androidx.paging.Pager
import androidx.paging.PagingData
import com.example.unsplash._common.helpers.connection_state.ConnectionStateProvider
import com.example.unsplash.data.data_sources.photo.PhotoDatasourceLocal
import com.example.unsplash.data.data_sources.photo.PhotoDatasourceRemote
import com.example.unsplash.presentation._common.some_components.Request
import com.example.unsplash.presentation.fragment_photos_main.models.Photo
import com.example.unsplash.presentation.fragment_photos_main.pagination.PhotosRemotePagerFactory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class PhotosRemotePagerFactoryImpl(
    private val connectionStateProvider: ConnectionStateProvider,
    private val datasourceRemote: PhotoDatasourceRemote,
    private val datasourceLocal: PhotoDatasourceLocal,
) : PhotosRemotePagerFactory {

    override fun create(request: Request): Flow<PagingData<Photo>> {
	  if (request !is Request.PhotosRequest) return flowOf(PagingData.empty())

	  return Pager(MainItemsDisplayPagingSource.pagerConfig()) {

		ItemsDisplayPagingSourceRemote(
		    connectionStateProvider = connectionStateProvider,
		    request = { pageNumber: Int, pageSize: Int ->
			  val config = AppPagingConfig(pageNumber, pageSize, request.param)

			  when (request) {
				is Request.SimpleRequest ->
				    datasourceRemote.requestPhotosTypeSome(config)
				is Request.SearchRequest ->
				    datasourceRemote.requestPhotosTypeSearch(config)
				is Request.PhotosByCollectionIdRequest ->
				    datasourceRemote.requestPhotosTypeFromCollection(config)
				is Request.LikedPhotosRequest ->
				    datasourceRemote.requestLikedPhotos(config)

			  }.also { items -> (datasourceLocal.savePhotos(items)) }

		    }
		)

	  }.flow
    }

}
