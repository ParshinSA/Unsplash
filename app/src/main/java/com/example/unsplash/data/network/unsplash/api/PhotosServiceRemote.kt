package com.example.unsplash.data.network.unsplash.api

import com.example.unsplash.data.models.*
import com.example.unsplash.data.models.dto.PhotoDetailsDto
import com.example.unsplash.data.models.dto.PhotoDto
import com.example.unsplash.data.models.dto.ResponseSearchPhotosByQueryDto
import retrofit2.Response
import retrofit2.http.*

interface PhotosServiceRemote {

    /**
     * Get a single page from the Editorial feed.
     * @param pageNumber Page number to retrieve. (Optional; default: 1)
     * @param pageSize Number of items per page. (Optional; default: 10)
     * */
    @GET("/photos")
    suspend fun requestPhotos(
	  @Query("page") pageNumber: Int,
	  @Query("per_page") pageSize: Int,
    ): Response<List<PhotoDto>>

    /**
     * Retrieve a single photo
     * @param  param The photo’s ID. Required.*/
    @GET("/photos/{id}")
    suspend fun requestDetailsPhotoById(
	  @Path("id") param: String
    ): Response<PhotoDetailsDto>

    /**
     * Get a single page of photo results for a query.
     * @param param Search terms. Required.
     * @param pageNumber Page number to retrieve. (Optional; default: 1)
     * @param pageSize Number of items per page. (Optional; default: 10)
     * */
    @GET("/search/photos")
    suspend fun requestPhotosByQuery(
	  @Query("query") param: String,
	  @Query("page") pageNumber: Int,
	  @Query("per_page") pageSize: Int,
    ): Response<ResponseSearchPhotosByQueryDto>

    /**
     * Retrieve a collection’s photos
     * @param param The collection’s ID. Required.
     * @param pageNumber Page number to retrieve. (Optional; default: 1)
     * @param pageSize Number of items per page. (Optional; default: 10)
     * */
    @GET("/collections/{id}/photos")
    suspend fun requestPhotosFromCollection(
	  @Path("id") param: String,
	  @Query("page") pageNumber: Int,
	  @Query("per_page") pageSize: Int,
    ): Response<List<PhotoDto>>

    /**
     * Get a list of photos liked by a user.
     * @param param The user’s username. Required.
     * @param pageNumber Page number to retrieve. (Optional; default: 1)
     * @param pageSize Number of items per page. (Optional; default: 10)
     * */
    @GET("/users/{username}/likes")
    suspend fun requestLikedPhotos(
	  @Path("username") param: String,
	  @Query("page") pageNumber: Int,
	  @Query("per_page") pageSize: Int,
    ): Response<List<PhotoDto>>

}



