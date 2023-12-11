package com.example.unsplash.data.models.converters

import com.example.unsplash.data.models._common.NULL_STUB
import com.example.unsplash.data.models.dto.PhotoDetailsDto
import com.example.unsplash.presentation._common.models.LikeInfo
import com.example.unsplash.presentation.fragment_photo_details.models.PhotoDetails

fun PhotoDetailsDto?.toUiModel(): PhotoDetails? {
    if (this == null) return null

    val cameraDescription = if (cameraDescription == null) NULL_STUB
    else {
	  with(cameraDescription) {
		"Device: $make\n" +
		    "Model: $model\n" +
		    "Exposure time: $exposureTime\n" +
		    "Aperture: $aperture\n" +
		    "Focal length : $focalLength\n" +
		    "ISO: $iso"
	  }
    }

    val photographerDescription = if (photographerInfoDto == null) NULL_STUB
    else {
	  with(photographerInfoDto) {
		"Bio: $bio\n" +
		    "Location: $location\n" +
		    "Total photos: $totalPhotos"
	  }
    }

    val tags = tags?.joinToString(",") { "#${it.title}" } ?: NULL_STUB

    return PhotoDetails(
	  id = id ?: return null,
	  downloads = downloads ?: NULL_STUB,
	  likeInfo = LikeInfo(
		photoId = id,
		likes = likes ?: NULL_STUB,
		likedByUser = likedByUser ?: false,
	  ),
	  blurHash = blurHash ?: NULL_STUB,
	  urlSmallPhoto = urlsDto?.small ?: return null,
	  urlNormalPhoto = urlsDto.regular ?: return null,
	  urlRawPhoto = urlsDto.raw ?: return null,
	  commonInfo = "$cameraDescription\n" +
		"$photographerDescription\n" + tags,
	  location = location.toUiModel(),
	  photographerInfo = photographerInfoDto.toUiModel(),
    )
}

