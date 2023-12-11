package com.example.unsplash._common.helpers.loading

import com.example.unsplash.data.storages.LoadIdStorage
import android.app.DownloadManager
import android.app.DownloadManager.COLUMN_LOCAL_URI
import android.app.DownloadManager.COLUMN_MEDIAPROVIDER_URI
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import androidx.core.database.getStringOrNull
import com.example.unsplash._common.extensions.launchIO
import com.example.unsplash.presentation._common.models.LoadedPhoto
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay

class LoadCompletedBroadcastHelperImpl(
    private val loadIdStorage: LoadIdStorage,
    private val context: Context,
) : LoadCompletedBroadcastHelper {

    private val coroutineScope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    private val downloadManager by lazy {
	  context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
    }

    private val broadcastDownloadComplete = object : BroadcastReceiver() {
	  override fun onReceive(context: Context?, intent: Intent?) {
		coroutineScope.launchIO(
		    action = {
			  delay(BEFORE_RETRIEVING_DATA)
			  if (context == null || intent == null) return@launchIO
			  val loadId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
			  val loadingPhoto = loadIdStorage.getLoadingPhoto(loadId) ?: return@launchIO
			  val fileName = getFileName(loadId) ?: return@launchIO
			  val urlToOpening = getMediaProviderUri(loadId) ?: return@launchIO
			  val urlToPreview = loadingPhoto.urlToPreview

			  loadIdStorage.commitLoadCompleted(
				LoadedPhoto(
				    loadId = loadId,
				    photoId = loadingPhoto.photoId,
				    fileName = fileName,
				    urlToOpening = urlToOpening,
				    urlToPreview = urlToPreview,
				)
			  )

		    }, onError = { it.printStackTrace() }
		)
	  }
    }

    override fun register() {
	  context.registerReceiver(
		broadcastDownloadComplete,
		IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE)
	  )
    }

    override fun unregister() {
	  context.unregisterReceiver(broadcastDownloadComplete)
    }

    private fun getFileName(loadId: Long): String? {
	  return downloadManager.query(DownloadManager.Query().setFilterById(loadId)).use { cursor ->
		if (!cursor.moveToFirst()) return@use null

		cursor.getStringOrNull(cursor.getColumnIndex(COLUMN_LOCAL_URI))?.let { localUri ->
		    return@use Uri.parse(localUri).lastPathSegment
		}
	  }
    }

    private fun getMediaProviderUri(loadId: Long): String? {
	  return downloadManager.query(DownloadManager.Query().setFilterById(loadId)).use { cursor ->
		if (!cursor.moveToFirst()) return@use null

		cursor.getStringOrNull(cursor.getColumnIndex(COLUMN_MEDIAPROVIDER_URI))?.let { uri ->
		    return@use uri
		}
	  }
    }

    companion object {
	  private const val BEFORE_RETRIEVING_DATA = 1000L
    }
}