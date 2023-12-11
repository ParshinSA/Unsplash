package com.example.unsplash.data.models.dto

import com.google.gson.annotations.SerializedName

data class PhotoDetailsDto(

    @SerializedName("id")
    val id: String?,

    @SerializedName("downloads")
    val downloads: String?,

    @SerializedName("likes")
    val likes: String?,

    @SerializedName("liked_by_user")
    val likedByUser: Boolean?,

    @SerializedName("blur_hash")
    val blurHash: String?,

    @SerializedName("urls")
    val urlsDto: UrlsDto?,

    @SerializedName("exif")
    val cameraDescription: CameraDescriptionDto?,

    @SerializedName("tags")
    val tags: List<TagOfPhotoDto>?,

    @SerializedName("location")
    val location: PhotoLocationDto?,

    @SerializedName("user")
    val photographerInfoDto: PhotographerInfoDto?

)