package com.example.unsplash.presentation.fragment_photo_details.models

import com.example.unsplash.presentation._common.interfaces.ContainsBlurHash
import com.example.unsplash.presentation._common.models.LikeInfo

data class PhotoDetails(
    val id: String,
    val downloads: String,
    val likeInfo: LikeInfo,
    val urlSmallPhoto: String,
    val urlNormalPhoto: String,
    val urlRawPhoto: String,
    val commonInfo: String,
    val location: PhotoLocation,
    val photographerInfo: PhotographerInfo,
    override val blurHash: String,
) : ContainsBlurHash