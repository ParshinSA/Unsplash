package com.example.unsplash.data.models.converters

import com.example.unsplash.data.models._common.NULL_STUB
import com.example.unsplash.data.models.dto.ProfileImageDto
import com.example.unsplash.presentation.fragment_photo_details.models.ProfileImage

fun ProfileImageDto?.toUiModel(): ProfileImage {
    return if (this == null) stubPhotographerProfilePhoto()
    else ProfileImage(
	  small = small ?: NULL_STUB,
	  medium = medium ?: NULL_STUB,
	  large = large ?: NULL_STUB,
    )
}

private fun stubPhotographerProfilePhoto(): ProfileImage {
    return ProfileImage(
	  NULL_STUB,
	  NULL_STUB,
	  NULL_STUB,
    )
}