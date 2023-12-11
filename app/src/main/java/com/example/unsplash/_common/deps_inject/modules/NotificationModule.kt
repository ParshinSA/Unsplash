package com.example.unsplash._common.deps_inject.modules

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.unsplash._common.deps_inject.scopes.AppScope
import com.example.unsplash._common.helpers.notifications.DownloadNotification
import com.example.unsplash._common.helpers.notifications.DownloadNotificationImpl
import com.example.unsplash._common.helpers.notifications.NotificationChannelIdProvider
import com.example.unsplash._common.helpers.notifications.NotificationChannelIdProviderImpl
import dagger.Module
import dagger.Provides

@Module
class NotificationModule {

    @Provides
    @AppScope
    @RequiresApi(Build.VERSION_CODES.O)
    fun providesNotificationChannels(context: Context): NotificationChannelIdProvider {
	  return NotificationChannelIdProviderImpl(context = context)
    }

    @Provides
    fun providesDownloadNotification(
	  notificationChannelIdProvider: NotificationChannelIdProvider,
	  context: Context,
    ): DownloadNotification {
	  return DownloadNotificationImpl(
		notificationChannelIdProvider = notificationChannelIdProvider,
		context = context
	  )
    }

}