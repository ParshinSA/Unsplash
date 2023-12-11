package com.example.unsplash._common.deps_inject.modules

import com.example.unsplash.data.storages.LoadIdStorage
import android.content.Context
import com.example.unsplash._common.cicirone.Screens
import com.example.unsplash._common.deps_inject.scopes.AppScope
import com.example.unsplash._common.helpers.loading.DownloadService
import com.example.unsplash._common.helpers.loading.DownloadServiceImpl
import dagger.Module
import dagger.Provides

@Module
class AppModule {

    @Provides
    fun providesAppScreens(): Screens {
	  return Screens()
    }

    @Provides
    @AppScope
    fun providesDownloadService(
	  loadIdStorage: LoadIdStorage,
	  context: Context,
    ): DownloadService {
	  return DownloadServiceImpl(
		loadIdStorage = loadIdStorage,
		context = context,
	  )
    }

}