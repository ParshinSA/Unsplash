package com.example.unsplash._common.helpers.loading

interface HelperWithCachingFile {

    fun loadFile(url: String, nameFile: String): String?

    fun deleteFile(nameFile: String)

}
