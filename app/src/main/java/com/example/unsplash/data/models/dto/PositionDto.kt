package com.example.unsplash.data.models.dto

import com.google.gson.annotations.SerializedName

data class PositionDto(

    @SerializedName("latitude")
    val latitude: Double?,

    @SerializedName("longitude")
    val longitude: Double?,
)
