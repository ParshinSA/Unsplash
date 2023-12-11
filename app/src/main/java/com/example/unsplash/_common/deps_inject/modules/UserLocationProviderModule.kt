package com.example.unsplash._common.deps_inject.modules

import android.content.Context
import com.example.unsplash._common.deps_inject.scopes.AppScope
import com.example.unsplash._common.helpers.location.UserLocationProvider
import com.example.unsplash._common.helpers.location.UserLocationProviderFactory
import com.example.unsplash._common.helpers.location.UserLocationProviderImplMinSDK21
import com.example.unsplash._common.helpers.location.UserLocationProviderImplMinSDK29
import com.example.unsplash.presentation._common.some_components.local_app_messenger.AppMessenger
import dagger.Module
import dagger.Provides
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap
import javax.inject.Provider

@Module
class UserLocationProviderModule {


    @Provides
    @[IntoMap ClassKey(UserLocationProviderImplMinSDK21::class)]
    fun providesUserLocationProviderImplMinSDK21(
	  appMessenger: AppMessenger,
	  context: Context
    ): UserLocationProvider {
	  return UserLocationProviderImplMinSDK21(
		appMessenger = appMessenger,
		context = context
	  )
    }

    @Provides
    @[IntoMap ClassKey(UserLocationProviderImplMinSDK29::class)]
    fun providesUserLocationProviderImplMinSDK29(
	  appMessenger: AppMessenger,
	  context: Context
    ): UserLocationProvider {
	  return UserLocationProviderImplMinSDK29(
		appMessenger = appMessenger,
		context = context
	  )
    }

    @Provides
    @AppScope
    fun providesUserLocationProvider(
	  mapUserLocationProviders: Map<Class<*>, @JvmSuppressWildcards Provider<UserLocationProvider>>
    ): UserLocationProvider {
	  return UserLocationProviderFactory(
		mapUserLocationProviders = mapUserLocationProviders
	  ).createUserLocationProvider()
    }
}