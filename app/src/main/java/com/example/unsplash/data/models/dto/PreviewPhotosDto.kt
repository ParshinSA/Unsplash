package com.example.unsplash.data.models.dto

import com.google.gson.annotations.SerializedName

data class PreviewPhotosDto(

    @SerializedName("id")
    val id: String?,

    @SerializedName("urls")
    val urlsDto: UrlsDto?,

    )