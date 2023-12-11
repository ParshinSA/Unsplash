package com.example.unsplash.presentation._external_dependencies

import androidx.paging.PagingData
import com.example.unsplash.presentation.fragment_collections.models.PhotosCollection
import kotlinx.coroutines.flow.Flow

interface CollectionsRepository {
    fun requestPagerForSimpleCollections(): Flow<PagingData<PhotosCollection>>
    fun requestPagerForSearchCollections(query: String): Flow<PagingData<PhotosCollection>>
}
