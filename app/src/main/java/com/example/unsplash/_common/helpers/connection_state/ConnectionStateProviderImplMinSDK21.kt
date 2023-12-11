package com.example.unsplash._common.helpers.connection_state

import android.content.Context
import android.net.ConnectivityManager
import com.example.unsplash.R
import com.example.unsplash.presentation._common.some_components.local_app_messenger.AppMessenger

class ConnectionStateProviderImplMinSDK21(
    private val appMessenger: AppMessenger,
    context: Context
) : ConnectionStateProvider {

    private val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE)
	  as ConnectivityManager

    override fun isDeviceOnline(): Boolean? {
	  val networkInfo = connectivityManager.activeNetworkInfo
	  return if (networkInfo != null && networkInfo.isConnected) true
	  else {
		appMessenger.sendMessage(R.string.connection_is_lost)
		null
	  }
    }

}