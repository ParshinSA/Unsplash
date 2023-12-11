package com.example.unsplash.data.models.converters

import com.example.unsplash.data.models.dto.LikeInfoDto
import com.example.unsplash.presentation._common.models.LikeInfo


fun LikeInfoDto?.toUiModel(): LikeInfo? {
    if (this == null) return null
    return LikeInfo(
	  photoId = photoId ?: return null,
	  likes = likes ?: return null,
	  likedByUser = likedByUser ?: return null,
    )

}
