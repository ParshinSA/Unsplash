package com.example.unsplash.data.models.dto

import com.google.gson.annotations.SerializedName

data class PhotosCollectorDto(

    @SerializedName("id")
    val id: String?,

    @SerializedName("name")
    val name: String?,

    )
