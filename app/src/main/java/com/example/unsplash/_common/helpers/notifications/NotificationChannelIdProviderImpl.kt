package com.example.unsplash._common.helpers.notifications

import android.app.NotificationChannel
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationManagerCompat
import com.example.unsplash._common.helpers.notifications.NotificationChannelIdProvider.TypeChannel

class NotificationChannelIdProviderImpl(
    private val context: Context,
) : NotificationChannelIdProvider {

    private val mapChannelId = mutableMapOf<TypeChannel, NotificationChannel>()

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createChannel(typeChannel: TypeChannel) {
	  val id = typeChannel.id
	  val priority = typeChannel.priority
	  val name = context.resources.getString(typeChannel.stringResName)
	  val description = context.resources.getString(typeChannel.stringResDescription)

	  val channel = NotificationChannel(id, name, priority).apply {
		this.description = description
	  }

	  NotificationManagerCompat.from(context).createNotificationChannel(channel)
	  mapChannelId[typeChannel] = channel
    }

    override fun getChannelId(typeChannel: TypeChannel): String {
	  return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
		mapChannelId[typeChannel]?.id ?: let {
		    createChannel(typeChannel)
		    getChannelId(typeChannel)
		}
	  } else DEFAULT_CHANNEL_ID
    }

    companion object {
	  private const val DEFAULT_CHANNEL_ID = "DEFAULT_CHANNEL_ID"
    }
}
