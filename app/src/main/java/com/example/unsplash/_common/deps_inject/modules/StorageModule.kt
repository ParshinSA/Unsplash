package com.example.unsplash._common.deps_inject.modules

import com.example.unsplash.data.storages.LoadIdStorage
import com.example.unsplash.data.storages.LoadIdStorageImpl
import android.content.Context
import com.example.unsplash._common.helpers.notifications.DownloadNotification
import com.example.unsplash.data.room_db.entity_loding_photos.LoadingPhotoDao
import com.example.unsplash.data.storages.*
import dagger.Module
import dagger.Provides

@Module
class StorageModule {

    @Provides
    fun providesFirstLaunchStorage(
	  context: Context
    ): FirstLaunchStorage {
	  return FirstLaunchStorageImpl(
		context = context
	  )
    }

    @Provides
    fun providesTokenStorage(
	  context: Context
    ): TokenStorage {
	  return TokenStorageImpl(
		context = context
	  )
    }

    @Provides
    fun providesAuthStateStorage(
	  context: Context
    ): AuthStateStorage {
	  return AuthStateStorageImpl(
		context = context
	  )
    }

    @Provides
    fun providesLoadIdStorage(
	  downloadNotification: DownloadNotification,
	  loadingPhotoDao: LoadingPhotoDao,
	  context: Context
    ): LoadIdStorage {
	  return LoadIdStorageImpl(
		downloadNotification = downloadNotification,
		loadingPhotoDao = loadingPhotoDao,
		context = context,
	  )
    }

}