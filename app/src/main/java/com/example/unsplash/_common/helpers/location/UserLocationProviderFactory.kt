package com.example.unsplash._common.helpers.location

import android.os.Build
import android.os.Build.VERSION_CODES.*
import javax.inject.Provider

class UserLocationProviderFactory(
    private val mapUserLocationProviders: Map<Class<*>, @JvmSuppressWildcards Provider<UserLocationProvider>>
) {

    fun createUserLocationProvider(): UserLocationProvider {
	  val osVersion = Build.VERSION.SDK_INT
	  val userLocationProviderClass = when (osVersion) {
		in LOLLIPOP..P -> UserLocationProviderImplMinSDK21::class.java
		in Q..TIRAMISU -> UserLocationProviderImplMinSDK29::class.java
		else -> throw IllegalArgumentException("Not implementation for the os version $osVersion")
	  } as Class<*>

	  return mapUserLocationProviders.getValue(userLocationProviderClass).get()
    }

}