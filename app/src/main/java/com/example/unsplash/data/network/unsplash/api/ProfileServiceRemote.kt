package com.example.unsplash.data.network.unsplash.api

import com.example.unsplash.data.models.dto.CurrentUserDto
import retrofit2.Response
import retrofit2.http.GET

interface ProfileServiceRemote {

    /**
     * Get the user’s profile
     * */
    @GET("/me")
    suspend fun getMyInfo(): Response<CurrentUserDto>

}