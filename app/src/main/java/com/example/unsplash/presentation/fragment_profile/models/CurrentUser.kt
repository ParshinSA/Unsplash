package com.example.unsplash.presentation.fragment_profile.models

import com.example.unsplash.presentation.fragment_photo_details.models.ProfileImage

data class CurrentUser(

    val id: String,

    val name: String,

    val userName: String,

    val profileImage: ProfileImage,

    val totalLikes: Int,

    val email: String,

    )