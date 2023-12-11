package com.example.unsplash.data.models.dto

import com.google.gson.annotations.SerializedName

data class ResponseSearchCollectionsByQueryDto(
    @SerializedName("results")
    val result: List<PhotosCollectionDto>?
)