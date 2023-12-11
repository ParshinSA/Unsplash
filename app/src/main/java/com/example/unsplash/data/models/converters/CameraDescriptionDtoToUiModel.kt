//package com.example.unsplash.data.models.converters
//
//import com.example.unsplash.data.models._common.NULL_STUB
//import com.example.unsplash.data.network.dto.CameraDescriptionDto
//import com.example.unsplash.presentation.screen_photo_details.models.CameraDescription
//
//fun CameraDescriptionDto?.toUiModel(): CameraDescription {
//    return if (this == null) todoCameraInformation()
//    else CameraDescription(
//	  make = make ?: NULL_STUB,
//	  model = model ?: NULL_STUB,
//	  exposureTime = exposureTime ?: NULL_STUB,
//	  aperture = aperture ?: NULL_STUB,
//	  focalLength = focalLength ?: NULL_STUB,
//	  iso = iso ?: NULL_STUB
//    )
//}
//
//private fun todoCameraInformation(): CameraDescription {
//    return CameraDescription(
//	  NULL_STUB,
//	  NULL_STUB,
//	  NULL_STUB,
//	  NULL_STUB,
//	  NULL_STUB,
//	  NULL_STUB,
//    )
//}