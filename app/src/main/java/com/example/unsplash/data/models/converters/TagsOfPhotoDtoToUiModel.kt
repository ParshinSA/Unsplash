package com.example.unsplash.data.models.converters

import com.example.unsplash.data.models.dto.TagOfPhotoDto
import com.example.unsplash.presentation._common.models.TagOfPhoto

fun TagOfPhotoDto.toUiModel(): TagOfPhoto {
    return TagOfPhoto(title)
}