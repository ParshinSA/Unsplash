package com.example.unsplash.presentation._common.some_components.local_app_messenger

import androidx.annotation.StringRes

interface AppMessenger {
    fun sendMessage(@StringRes resIdMessage: Int)
    fun sendMessage(message: String)
}
