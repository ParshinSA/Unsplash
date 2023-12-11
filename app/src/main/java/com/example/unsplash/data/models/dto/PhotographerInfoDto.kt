package com.example.unsplash.data.models.dto

import com.google.gson.annotations.SerializedName

data class PhotographerInfoDto(

    @SerializedName("name")
    val name: String?,

    @SerializedName("total_photos")
    val totalPhotos: String?,

    @SerializedName("bio")
    val bio: String?,

    @SerializedName("location")
    val location: String?,

    @SerializedName("profile_image")
    val profileImageDto: ProfileImageDto?,
)
