package com.example.unsplash.data.storages

interface FirstLaunchStorage {
    fun isFirstLaunch(): Boolean
    fun confirmFirstLaunch()
}