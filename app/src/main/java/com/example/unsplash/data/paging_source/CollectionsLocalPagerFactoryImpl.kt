package com.example.unsplash.data.paging_source

import androidx.paging.Pager
import androidx.paging.PagingData
import com.example.unsplash.data.data_sources.collection.CollectionsDatasourceLocal
import com.example.unsplash.presentation.fragment_collections.models.PhotosCollection
import com.example.unsplash.presentation.fragment_collections.pagination.CollectionsLocalPagerFactory
import kotlinx.coroutines.flow.Flow

class CollectionsLocalPagerFactoryImpl(
    private val datasource: CollectionsDatasourceLocal
) : CollectionsLocalPagerFactory {

    override fun create(): Flow<PagingData<PhotosCollection>> {
	  return Pager(MainItemsDisplayPagingSource.pagerConfig()) {
		ItemsDisplayPagingSourceLocal(request = {
		    datasource.requestCollections()
		})
	  }.flow
    }

}