package com.example.unsplash._common.helpers.connection_state

interface ConnectionStateProvider {
    fun isDeviceOnline(): Boolean?
}