package com.example.unsplash.data.paging_source

import androidx.paging.Pager
import androidx.paging.PagingData
import com.example.unsplash._common.helpers.connection_state.ConnectionStateProvider
import com.example.unsplash.data.data_sources.collection.CollectionsDatasourceLocal
import com.example.unsplash.data.data_sources.collection.CollectionsDatasourceRemote
import com.example.unsplash.presentation._common.some_components.Request
import com.example.unsplash.presentation.fragment_collections.models.PhotosCollection
import com.example.unsplash.presentation.fragment_collections.pagination.CollectionsRemotePagerFactory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class CollectionsRemotePagerFactoryImpl(
    private val connectionStateProvider: ConnectionStateProvider,
    private val datasourceLocal: CollectionsDatasourceLocal,
    private val datasourceRemote: CollectionsDatasourceRemote
) : CollectionsRemotePagerFactory {


    override fun create(request: Request): Flow<PagingData<PhotosCollection>> {
	  if (request !is Request.CollectionsRequest) return flowOf(PagingData.empty())

	  return Pager(MainItemsDisplayPagingSource.pagerConfig()) {

		ItemsDisplayPagingSourceRemote(
		    connectionStateProvider = connectionStateProvider,
		    request = { pageNumber: Int, pageSize: Int ->
			  val config = AppPagingConfig(pageNumber, pageSize, request.param)

			  when (request) {
				is Request.SimpleRequest ->
				    datasourceRemote.requestCollectionsTypeSome(config)
				is Request.SearchRequest ->
				    datasourceRemote.requestCollectionsTypeSearch(config)

			  }.also { items -> datasourceLocal.saveCollections(items) }

		    }
		)

	  }.flow
    }
}
