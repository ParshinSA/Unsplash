package com.example.unsplash.data.models.dto

import com.google.gson.annotations.SerializedName

data class LikeInfoDto(

    @SerializedName("id")
    val photoId: String?,

    @SerializedName("likes")
    val likes: String?,

    @SerializedName("liked_by_user")
    val likedByUser: Boolean?,

    )
