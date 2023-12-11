package com.example.unsplash._common.helpers.location

import android.location.Location

interface UserLocationProvider {

    suspend fun getCurrentLocation(): Location?

}
