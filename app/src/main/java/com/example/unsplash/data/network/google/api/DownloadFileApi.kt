package com.example.unsplash.data.network.google.api

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Url

interface DownloadFileApi {
    @GET
    fun getFile(@Url url: String): ResponseBody
}
