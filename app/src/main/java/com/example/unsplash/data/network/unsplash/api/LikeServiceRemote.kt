package com.example.unsplash.data.network.unsplash.api

import com.example.unsplash.data.models.dto.AnswerForChangeLikeDto
import com.example.unsplash.data.models.dto.LikeInfoDto
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface LikeServiceRemote {

    @POST("/photos/{id}/like")
    suspend fun likePhoto(@Path("id") id: String): Response<AnswerForChangeLikeDto>

    @DELETE("/photos/{id}/like")
    suspend fun unlikePhoto(@Path("id") id: String): Response<AnswerForChangeLikeDto>

    @GET("/photos/{id}")
    suspend fun getLikeInfoById(@Path("id") id: String): Response<LikeInfoDto>

}