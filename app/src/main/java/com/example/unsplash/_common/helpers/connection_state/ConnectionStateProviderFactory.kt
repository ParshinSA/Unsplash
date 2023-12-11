package com.example.unsplash._common.helpers.connection_state

import android.os.Build
import android.os.Build.VERSION_CODES.*
import javax.inject.Provider

class ConnectionStateProviderFactory(
    private val mapConnectionStateProviders: Map<Class<*>, @JvmSuppressWildcards Provider<ConnectionStateProvider>>,
) {

    fun createConnectionStateProvider(): ConnectionStateProvider {
	  val osVersion = Build.VERSION.SDK_INT
	  val connectionStateServiceClass = when (osVersion) {
		in LOLLIPOP..P -> ConnectionStateProviderImplMinSDK21::class.java
		in Q..TIRAMISU -> ConnectionStateProviderImplMinSDK29::class.java
		else -> throw IllegalArgumentException("Implementation \"ConnectionStateProvider\" for SDK-$osVersion not fond")
	  } as Class<*>

	  return mapConnectionStateProviders.getValue(connectionStateServiceClass).get()
    }

}