package com.example.unsplash.data.models.converters

import com.example.unsplash.data.models._common.NULL_STUB
import com.example.unsplash.data.models.dto.PhotographerInfoDto
import com.example.unsplash.presentation.fragment_photo_details.models.PhotographerInfo

fun PhotographerInfoDto?.toUiModel(): PhotographerInfo {
    return if (this == null) todoPhotographerInfo()
    else {
	  val profilePhoto = profileImageDto.toUiModel()
	  PhotographerInfo(
		name = name ?: NULL_STUB,
		urlProfilePhotoSmall = profilePhoto.small,
		urlProfilePhotoMedium = profilePhoto.medium,
	  )
    }
}

private fun todoPhotographerInfo(): PhotographerInfo {
    return PhotographerInfo(
	  name = NULL_STUB,
	  urlProfilePhotoSmall = NULL_STUB,
	  urlProfilePhotoMedium = NULL_STUB,
    )
}