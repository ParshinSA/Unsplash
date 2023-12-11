package com.example.unsplash._common.helpers.loading

import com.example.unsplash.presentation._common.models.PhotoToLoading

interface DownloadService {

     fun initStartLoading(photoToLoading: PhotoToLoading)
}
