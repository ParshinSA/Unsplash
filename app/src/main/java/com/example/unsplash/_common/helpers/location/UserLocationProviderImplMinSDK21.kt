package com.example.unsplash._common.helpers.location

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.Context
import android.location.Location
import android.location.LocationManager
import android.location.LocationManager.GPS_PROVIDER
import androidx.annotation.RequiresPermission
import com.example.unsplash.R
import com.example.unsplash.presentation._common.some_components.local_app_messenger.AppMessenger
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class UserLocationProviderImplMinSDK21(
    private val appMessenger: AppMessenger,
    context: Context
) : UserLocationProvider {

    private val locationManager = context.getSystemService(Context.LOCATION_SERVICE)
	  as LocationManager

    @RequiresPermission(anyOf = [ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION])
    override suspend fun getCurrentLocation(): Location? {
	  return suspendCoroutine { emitter ->
		val location = locationManager.getLastKnownLocation(GPS_PROVIDER) ?: let {
		    appMessenger.sendMessage(R.string.failed_to_get_user_location)
		    emitter.resume(null)
		    return@suspendCoroutine
		}
		emitter.resume(location)
	  }
    }

}