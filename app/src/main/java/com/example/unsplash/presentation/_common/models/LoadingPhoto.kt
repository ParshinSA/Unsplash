package com.example.unsplash.presentation._common.models

data class LoadingPhoto(
    val loadId: Long,
    val photoId: String,
    val urlToPreview: String,
)