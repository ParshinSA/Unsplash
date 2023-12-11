package com.example.unsplash._common.extensions

import com.example.unsplash.presentation._common.models.LikeInfo
import com.example.unsplash.presentation.fragment_photos_main.models.Photo

fun Photo.copyWithChangesLikeInfo(info: LikeInfo): Photo {
    return this.copy(
	  likedByUser = info.likedByUser,
	  likes = info.likes
    )
}