package com.example.unsplash.data.data_sources.photo

import com.example.unsplash.presentation.fragment_photos_main.models.Photo

interface PhotoDatasourceLocal {

    fun savePhotos(photoList: List<Photo>)

    suspend fun requestPhotos(): List<Photo>

}
