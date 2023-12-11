package com.example.unsplash.data.models.dto

import com.google.gson.annotations.SerializedName

data class CameraDescriptionDto(

    @SerializedName("make")
    val make: String?,

    @SerializedName("model")
    val model: String?,

    @SerializedName("exposure_time")
    val exposureTime: String?,

    @SerializedName("aperture")
    val aperture: String?,

    @SerializedName("focal_length")
    val focalLength: String?,

    @SerializedName("iso")
    val iso: String?,
)
