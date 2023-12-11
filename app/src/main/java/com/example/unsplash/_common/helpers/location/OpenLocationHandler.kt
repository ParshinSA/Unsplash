package com.example.unsplash._common.helpers.location

import android.content.Context

interface OpenLocationHandler {

    fun openLocationByLatLong(context: Context, latitude: Double, longitude: Double)

}