package com.example.unsplash._common.helpers.connection_state

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import com.example.unsplash.R
import com.example.unsplash._common.extensions.launchMain
import com.example.unsplash.presentation._common.some_components.local_app_messenger.AppMessenger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.*

class ConnectionStateProviderImplMinSDK29(
    private val appMessenger: AppMessenger,
    context: Context,
) : ConnectionStateProvider {

    private val coroutineScope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    @OptIn(FlowPreview::class)
    private val emitterConnectionState = MutableStateFlow<Boolean?>(true)
	  .apply { debounce(TIMEOUT_EMIT_INTERNET_STATE) }

    private var connectivityCollection = mutableMapOf<Int, Boolean>()

    private val connectivityManager = (
	  if (Build.VERSION.SDK_INT == Build.VERSION_CODES.M)
		context.getSystemService(ConnectivityManager::class.java)
	  else context.getSystemService(Context.CONNECTIVITY_SERVICE)
	  ) as ConnectivityManager

    private val networkRequest = NetworkRequest.Builder()
	  .addCapability(NetworkCapabilities.NET_CAPABILITY_NOT_METERED)
	  .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
	  .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
	  .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
	  .build()

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
	  override fun onAvailable(network: Network) {
		network.commitState(true)
		super.onAvailable(network)
	  }

	  override fun onLost(network: Network) {
		network.commitState(false)
		super.onLost(network)
	  }
    }

    init {
	  /** Регисрируемся на изменение состояния соединения*/
	  connectivityManager.registerNetworkCallback(networkRequest, networkCallback)
	  checkConnection()
    }


    private fun Network.commitState(state: Boolean) {
	  connectivityCollection[this.hashCode()] = state
	  checkConnection()
    }

    private fun checkConnection() {
	  coroutineScope.launchMain(
		action = {
		    /** Эмитим текущее состояние подключения */
		    emitterConnectionState.emit(connectivityCollection.containsValue(true))
		    /** Удаляем устаревшие потерянные соединения */
		    connectivityCollection = connectivityCollection.filter { it.value }.toMutableMap()
		}, { it.printStackTrace() })
    }

//    override fun getEmitterConnectionState(): Flow<Boolean?> {
//	  /** Подписка на состояние соединения */
//	  return emitterConnectionState
//    }

    override fun isDeviceOnline(): Boolean? {
	  /** Разовый запрос состояния соединения */
	  return if (emitterConnectionState.value == true) true else {
		appMessenger.sendMessage(R.string.connection_is_lost)
		null
	  }
    }

    companion object {
	  private const val TIMEOUT_EMIT_INTERNET_STATE = 500L
    }
}
