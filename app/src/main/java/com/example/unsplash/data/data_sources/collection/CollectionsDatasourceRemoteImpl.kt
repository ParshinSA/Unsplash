package com.example.unsplash.data.data_sources.collection

import com.example.unsplash.data.models.converters.toUiModel
import com.example.unsplash.data.models.dto.PhotosCollectionDto
import com.example.unsplash.data.network.unsplash.api.CollectionsServiceRemote
import com.example.unsplash.data.paging_source.AppPagingConfig
import com.example.unsplash.presentation.fragment_collections.models.PhotosCollection
import java.io.IOException

class CollectionsDatasourceRemoteImpl(
    private val collectionsServiceRemote: CollectionsServiceRemote,
) : CollectionsDatasourceRemote {

    override suspend fun requestCollectionsTypeSome(config: AppPagingConfig): List<PhotosCollection> {
	  return collectionsServiceRemote.requestCollections(
		config.pageNumber,
		config.pageSize
	  ).body()?.mapNotNull { photosCollectionDto: PhotosCollectionDto ->
		photosCollectionDto.toUiModel()
	  } ?: emptyList()
    }

    override suspend fun requestCollectionsTypeSearch(config: AppPagingConfig): List<PhotosCollection> {
	  val response = collectionsServiceRemote.requestCollectionsByQuery(
		query = config.param,
		pageNumber = config.pageNumber,
		pageSize = config.pageSize
	  )

	  return if (response.isSuccessful) response.body()?.result?.mapNotNull { photosCollectionDto: PhotosCollectionDto ->
		photosCollectionDto.toUiModel()
	  } ?: emptyList()
	  else throw IOException(response.message())
    }

}