package com.example.unsplash.data.models.dto

import com.google.gson.annotations.SerializedName

data class PhotosCollectionDto(

    @SerializedName("id")
    val id: String?,

    @SerializedName("preview_photos")
    val previewPhotos: List<PreviewPhotosDto>?,

    @SerializedName("title")
    val title: String?,

    @SerializedName("total_photos")
    val totalPhotos: Int?,

    @SerializedName("user")
    val photoCollector: PhotosCollectorDto?,

    )

