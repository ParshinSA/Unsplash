package com.example.unsplash.data.models.dto

import com.google.gson.annotations.SerializedName

data class ProfileImageDto(

    @SerializedName("small")
    val small: String?,

    @SerializedName("medium")
    val medium: String?,

    @SerializedName("large")
    val large: String?,

    )
