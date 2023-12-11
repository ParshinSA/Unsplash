package com.example.unsplash.data.models.converters

import com.example.unsplash.data.models.dto.PhotoDto
import com.example.unsplash.presentation.fragment_photos_main.models.Photo


fun PhotoDto?.toUiModel(): Photo? {
    if (this == null) return null
    if (urlsDto == null) return null
    if (photographerInfoDto == null) return null
    if (photographerInfoDto.profileImageDto == null) return null

    return Photo(
	  photoID = id ?: return null,
	  likes = likes ?: return null,
	  photoResource = urlsDto.small ?: return null,
	  photographerName = photographerInfoDto.name ?: return null,
	  photoResourcePhotographerProfile = photographerInfoDto.profileImageDto.small ?: return null,
	  likedByUser = likedByUser ?: return null,
    )
}

