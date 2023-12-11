package com.example.unsplash.data.network.unsplash.api

import com.example.unsplash.data.models.dto.PhotosCollectionDto
import com.example.unsplash.data.models.dto.ResponseSearchCollectionsByQueryDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface CollectionsServiceRemote {

    /**
     * @param pageNumber Page number to retrieve. (Optional; default: 1)
     * @param pageSize Number of items per page. (Optional; default: 10)
     * */
    @GET("/collections")
    suspend fun requestCollections(
	  @Query("page") pageNumber: Int,
	  @Query("per_page") pageSize: Int,
    ): Response<List<PhotosCollectionDto>>

    /**
     * @param query Search terms.
     * @param pageNumber Page number to retrieve. (Optional; default: 1)
     * @param pageSize Number of items per page. (Optional; default: 10)
     * */
    @GET("/search/collections")
    suspend fun requestCollectionsByQuery(
	  @Query("query") query: String,
	  @Query("page") pageNumber: Int,
	  @Query("per_page") pageSize: Int,
    ): Response<ResponseSearchCollectionsByQueryDto>

}