package com.example.unsplash.data.data_sources.collection

import com.example.unsplash.data.paging_source.AppPagingConfig
import com.example.unsplash.presentation.fragment_collections.models.PhotosCollection

interface CollectionsDatasourceRemote {

    suspend fun requestCollectionsTypeSome(config: AppPagingConfig): List<PhotosCollection>

    suspend fun requestCollectionsTypeSearch(config: AppPagingConfig): List<PhotosCollection>

}