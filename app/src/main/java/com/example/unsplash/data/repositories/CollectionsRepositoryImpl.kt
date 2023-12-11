package com.example.unsplash.data.repositories

import androidx.paging.PagingData
import com.example.unsplash._common.helpers.connection_state.ConnectionStateProvider
import com.example.unsplash.presentation._common.some_components.Request
import com.example.unsplash.presentation._external_dependencies.CollectionsRepository
import com.example.unsplash.presentation.fragment_collections.models.PhotosCollection
import com.example.unsplash.presentation.fragment_collections.pagination.CollectionsLocalPagerFactory
import com.example.unsplash.presentation.fragment_collections.pagination.CollectionsRemotePagerFactory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class CollectionsRepositoryImpl(
    private val connectionStateProvider: ConnectionStateProvider,
    private val remotePager: CollectionsRemotePagerFactory,
    private val localPager: CollectionsLocalPagerFactory,
) : CollectionsRepository {

    override fun requestPagerForSimpleCollections(): Flow<PagingData<PhotosCollection>> {
	  return if (connectionStateProvider.isDeviceOnline() == true)
		remotePager.create(Request.SimpleRequest)
	  else localPager.create()
    }

    override fun requestPagerForSearchCollections(query: String): Flow<PagingData<PhotosCollection>> {
	  connectionStateProvider.isDeviceOnline() ?: return flowOf(PagingData.empty())
	  return remotePager.create(Request.SearchRequest(query))
    }

}
