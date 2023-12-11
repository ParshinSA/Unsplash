package com.example.unsplash.data.models.dto

import com.google.gson.annotations.SerializedName

data class ResponseSearchPhotosByQueryDto(

    @SerializedName("results")
    val result: List<PhotoDto>?

)
