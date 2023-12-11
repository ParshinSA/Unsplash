package com.example.unsplash.data.models.converters

import com.example.unsplash.data.models._common.NULL_STUB
import com.example.unsplash.data.models.dto.PhotoLocationDto
import com.example.unsplash.presentation.fragment_photo_details.models.PhotoLocation

fun PhotoLocationDto?.toUiModel(): PhotoLocation {
    return if (this == null) todoPhotoLocation()
    else PhotoLocation(
	  city = city ?: NULL_STUB,
	  country = country ?: NULL_STUB,
	  latitude = position?.latitude,
	  longitude = position?.longitude,
    )
}

private fun todoPhotoLocation(): PhotoLocation {
    return PhotoLocation(
	  NULL_STUB,
	  NULL_STUB,
	  null,
	  null,
    )
}