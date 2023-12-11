package com.example.unsplash._common.deps_inject.modules

import com.example.unsplash.data.storages.LoadIdStorage
import android.content.Context
import com.example.unsplash._common.deps_inject.scopes.AppScope
import com.example.unsplash._common.helpers.app_auth.AuthHelper
import com.example.unsplash._common.helpers.loading.HelperWithCachingFile
import com.example.unsplash._common.helpers.loading.HelperWithCachingFileImpl
import com.example.unsplash._common.helpers.loading.LoadCompletedBroadcastHelper
import com.example.unsplash._common.helpers.loading.LoadCompletedBroadcastHelperImpl
import com.example.unsplash._common.helpers.local_like_state.LocalLikeStateProvider
import com.example.unsplash._common.helpers.local_like_state.LocalLikeStateProviderImpl
import com.example.unsplash._common.helpers.location.OpenLocationHandler
import com.example.unsplash._common.helpers.location.OpenLocationHandlerImpl
import com.example.unsplash.data.network.unsplash.api.LikeServiceRemote
import com.example.unsplash.presentation._common.some_components.local_app_messenger.AppMessenger
import com.example.unsplash.presentation._common.some_components.local_app_messenger.AppMessengerImpl
import com.example.unsplash.presentation._common.some_components.theme_color_provider.ThemeColorProvider
import com.example.unsplash.presentation._common.some_components.theme_color_provider.ThemeColorProviderImpl
import dagger.Module
import dagger.Provides

@Module
class SomeComponentsModule {

    @Provides
    fun providesHelperWithCachingFile(
	  context: Context
    ): HelperWithCachingFile {
	  return HelperWithCachingFileImpl(
		context = context,
	  )
    }

    @Provides
    @AppScope
    fun providesLoadCompletedBroadcastHelper(
	  loadIdStorage: LoadIdStorage,
	  context: Context,
    ): LoadCompletedBroadcastHelper {
	  return LoadCompletedBroadcastHelperImpl(
		loadIdStorage = loadIdStorage,
		context = context,
	  )
    }

    @Provides
    fun providesAppMessenger(
	  context: Context
    ): AppMessenger {
	  return AppMessengerImpl(
		context = context
	  )
    }

    @Provides
    fun providesThemeColorProvider(
	  context: Context
    ): ThemeColorProvider {
	  return ThemeColorProviderImpl(
		context = context
	  )
    }

    @Provides
    fun providesAuthHandler(
	  context: Context
    ): AuthHelper {
	  return AuthHelper(
		context = context
	  )
    }

    @Provides
    fun providesOpeningLocationHandler(
	  appMessenger: AppMessenger,
    ): OpenLocationHandler {
	  return OpenLocationHandlerImpl(
		appMessenger = appMessenger,
	  )
    }

    @Provides
    @AppScope
    fun providesLocalLikeStateProvider(
	  likeServiceRemote: LikeServiceRemote
    ): LocalLikeStateProvider {
	  return LocalLikeStateProviderImpl(
		likeServiceRemote = likeServiceRemote
	  )
    }

}