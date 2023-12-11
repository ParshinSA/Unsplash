package com.example.unsplash.presentation._common.models

data class LoadedPhoto(
    val loadId: Long,
    val photoId: String,
    val fileName: String,
    val urlToOpening: String,
    val urlToPreview: String,
)
