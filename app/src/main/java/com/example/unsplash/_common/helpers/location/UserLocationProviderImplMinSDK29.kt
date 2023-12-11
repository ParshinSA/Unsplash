package com.example.unsplash._common.helpers.location

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.Context
import android.location.Location
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresPermission
import com.example.unsplash.R
import com.example.unsplash.presentation._common.some_components.local_app_messenger.AppMessenger
import com.google.android.gms.location.LocationServices
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class UserLocationProviderImplMinSDK29(
    private val appMessenger: AppMessenger,
    context: Context
) : UserLocationProvider {

    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    @RequiresApi(Build.VERSION_CODES.Q)
    @RequiresPermission(anyOf = [ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION])
    override suspend fun getCurrentLocation(): Location? {
	  return suspendCoroutine { emitter ->
		val lastLocation = fusedLocationClient.lastLocation

		lastLocation.addOnSuccessListener { location ->
		    emitter.resume(location)
		}

		lastLocation.addOnFailureListener { exception: Exception ->
		    exception.printStackTrace()
		    appMessenger.sendMessage(R.string.failed_to_get_user_location)
		    emitter.resume(null)
		}
	  }
    }

}