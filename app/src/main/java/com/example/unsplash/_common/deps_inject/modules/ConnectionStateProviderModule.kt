package com.example.unsplash._common.deps_inject.modules

import android.content.Context
import com.example.unsplash._common.deps_inject.scopes.AppScope
import com.example.unsplash._common.helpers.connection_state.ConnectionStateProvider
import com.example.unsplash._common.helpers.connection_state.ConnectionStateProviderFactory
import com.example.unsplash._common.helpers.connection_state.ConnectionStateProviderImplMinSDK21
import com.example.unsplash._common.helpers.connection_state.ConnectionStateProviderImplMinSDK29
import com.example.unsplash.presentation._common.some_components.local_app_messenger.AppMessenger
import dagger.Module
import dagger.Provides
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap
import javax.inject.Provider

@Module
class ConnectionStateProviderModule {

    @Provides
    @[IntoMap ClassKey(ConnectionStateProviderImplMinSDK21::class)]
    fun providesConnectionStateProviderImplMinSDK21(
	  appMessenger: AppMessenger,
	  context: Context
    ): ConnectionStateProvider {
	  return ConnectionStateProviderImplMinSDK21(
		appMessenger = appMessenger,
		context = context
	  )
    }

    @Provides
    @[IntoMap ClassKey(ConnectionStateProviderImplMinSDK29::class)]
    fun providesConnectionStateProviderImplMinSDK29(
	  appMessenger: AppMessenger,
	  context: Context
    ): ConnectionStateProvider {
	  return ConnectionStateProviderImplMinSDK29(
		appMessenger = appMessenger,
		context = context
	  )
    }

    @Provides
    @AppScope
    fun providesConnectionStateProvider(
	  mapConnectionStateProviders: Map<Class<*>, @JvmSuppressWildcards Provider<ConnectionStateProvider>>
    ): ConnectionStateProvider {
	  return ConnectionStateProviderFactory(
		mapConnectionStateProviders = mapConnectionStateProviders
	  ).createConnectionStateProvider()
    }

}