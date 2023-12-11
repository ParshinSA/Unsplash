package com.example.unsplash._common.helpers.notifications

import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.media.RingtoneManager
import android.net.Uri
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.bumptech.glide.Glide
import com.example.unsplash.R
import com.example.unsplash._common.helpers.notifications.NotificationChannelIdProvider.TypeChannel.DOWNLOAD_CHANNEL
import com.example.unsplash.presentation._common.models.LoadedPhoto
import com.example.unsplash.presentation._common.models.LoadingPhoto
import com.example.unsplash.presentation._common.some_components.GlideListener

class DownloadNotificationImpl(
    notificationChannelIdProvider: NotificationChannelIdProvider,
    private val context: Context,
) : DownloadNotification {

    private val channelId = notificationChannelIdProvider.getChannelId(DOWNLOAD_CHANNEL)

    private val baseLoadNotificationBuilder
	  get() = NotificationCompat.Builder(context, channelId)
		.setSmallIcon(R.drawable.ic_logo_unsplash)
		.setPriority(DOWNLOAD_CHANNEL.priority)
		.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
		.setVibrate(longArrayOf(100))

    override fun sendLoadStarted(loadingPhoto: LoadingPhoto) {
	  val notificationId = loadingPhoto.loadId.toInt()
	  val title = context.getString(R.string.load_started)

	  val notificationBuilder = baseLoadNotificationBuilder
		.setContentTitle(title)
		.setContentText(loadingPhoto.photoId)
		.setTimeoutAfter(CLOSED_TIMEOUT)

	  NotificationManagerCompat.from(context)
		.notify(notificationId, notificationBuilder.build())
    }

    override fun sendLoadCompleted(loadedPhoto: LoadedPhoto) {
	  val intent = Intent().apply {
		action = Intent.ACTION_VIEW
		setDataAndType(Uri.parse(loadedPhoto.urlToOpening), "image/jpeg")
	  }
	  val id = loadedPhoto.loadId.toInt()

	  val chooserIntent = Intent.createChooser(intent, loadedPhoto.photoId)
		.apply { flags = Intent.FLAG_ACTIVITY_NEW_TASK }

	  val pendingIntent = PendingIntent.getActivity(context, id, chooserIntent, 0)

	  val title = context.getString(R.string.load_completed)

	  val builder = baseLoadNotificationBuilder
		.setContentTitle(title)
		.setContentText(loadedPhoto.fileName)
		.setContentIntent(pendingIntent)
		.setAutoCancel(true)

	  Glide.with(context)
		.asBitmap()
		.load(loadedPhoto.urlToPreview)
		.addListener(
		    GlideListener<Bitmap>(
			  onLoadFailed = {

				Glide.with(context)
				    .asBitmap()
				    .load(loadedPhoto.urlToOpening)
				    .addListener(
					  GlideListener<Bitmap>(
						onLoadFailed = {
						    showNotification(id, builder.build())
						},
						onLoadReady = { result: Bitmap? ->
						    result?.let {
							  builder.setLargeIcon(it)
							  showNotification(id, builder.build())
						    }
						}
					  )
				    ).submit()

			  },
			  onLoadReady = { result: Bitmap? ->
				result?.let {
				    builder.setLargeIcon(it)
				    showNotification(id, builder.build())
				}
			  }
		    )
		).submit()
    }

    private fun showNotification(id: Int, notification: Notification) {
	  NotificationManagerCompat.from(context).cancel(id)
	  NotificationManagerCompat.from(context).notify(id, notification)
    }

    companion object {
	  private const val CLOSED_TIMEOUT = 1000L // Таймаут для закрытия уведомления
    }
}