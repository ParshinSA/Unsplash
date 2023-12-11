package com.example.unsplash._common.helpers.notifications

import android.app.NotificationManager
import android.os.Build
import androidx.annotation.StringRes
import androidx.core.app.NotificationCompat
import com.example.unsplash.R

interface NotificationChannelIdProvider {

    fun getChannelId(typeChannel: TypeChannel): String

    enum class TypeChannel(
	  internal val id: String,
	  internal val priority: Int,
	  @StringRes internal val stringResName: Int,
	  @StringRes internal val stringResDescription: Int,
    ) {

	  DOWNLOAD_CHANNEL(
		id = "DOWNLOAD_CHANNEL",
		priority = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
		    NotificationManager.IMPORTANCE_MAX
		} else {
		    NotificationCompat.PRIORITY_HIGH
		},
		stringResName = R.string.name_download_channel,
		stringResDescription = R.string.description_download_channel
	  );

    }

}