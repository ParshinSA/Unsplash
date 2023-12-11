package com.example.unsplash.data.models.dto

import com.google.gson.annotations.SerializedName


data class TagOfPhotoDto(
    @SerializedName("title")
    val title: String
)