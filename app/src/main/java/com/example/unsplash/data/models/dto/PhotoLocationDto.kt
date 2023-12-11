package com.example.unsplash.data.models.dto

import com.google.gson.annotations.SerializedName

data class PhotoLocationDto(

    @SerializedName("city")
    val city: String?,

    @SerializedName("country")
    val country: String?,

    @SerializedName("position")
    val position: PositionDto?

)
