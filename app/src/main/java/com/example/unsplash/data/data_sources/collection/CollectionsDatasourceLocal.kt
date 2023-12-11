package com.example.unsplash.data.data_sources.collection

import com.example.unsplash.presentation.fragment_collections.models.PhotosCollection

interface CollectionsDatasourceLocal {

    fun saveCollections(collections: List<PhotosCollection>)

    suspend fun requestCollections(): List<PhotosCollection>

}