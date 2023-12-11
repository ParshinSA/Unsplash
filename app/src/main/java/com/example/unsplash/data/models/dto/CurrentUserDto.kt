package com.example.unsplash.data.models.dto

import com.google.gson.annotations.SerializedName

data class CurrentUserDto(

    @SerializedName("id")
    val id: String?,

    @SerializedName("username")
    val userName: String?,

    @SerializedName("name")
    val name: String?,

    @SerializedName("profile_image")
    val profileImage: ProfileImageDto?,

    @SerializedName("total_likes")
    val totalLikes: Int?,

    @SerializedName("email")
    val email: String?,

    )