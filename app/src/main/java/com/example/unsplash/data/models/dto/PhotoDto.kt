package com.example.unsplash.data.models.dto

import com.google.gson.annotations.SerializedName
import java.util.*

data class PhotoDto(

    @SerializedName("id")
    val id: String?,

    @SerializedName("likes")
    val likes: String?,

    @SerializedName("urls")
    val urlsDto: UrlsDto?,

    @SerializedName("user")
    val photographerInfoDto: PhotographerInfoDto?,

    @SerializedName("liked_by_user")
    val likedByUser: Boolean?
)