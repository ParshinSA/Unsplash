package com.example.unsplash.presentation._common.some_components.local_app_messenger

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes
import com.example.unsplash._common.extensions.launchMain
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob


class AppMessengerImpl(
    private val context: Context
) : AppMessenger {

    private var toast: Toast? = null
    private var lastTimeWhenShowedToast = 0L
    private var lastMessageWhichShowedInToast = ""

    private val coroutineScope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    override fun sendMessage(@StringRes resIdMessage: Int) {
	  val message = receiveString(resIdMessage)
	  sendMessage(message)
    }

    override fun sendMessage(message: String) {
	  sendToast(message)
    }

    private fun receiveString(resIdMessage: Int): String {
	  return context.resources.getString(resIdMessage)
    }

    private fun sendToast(newMessage: String) {
	  val currentTime = System.currentTimeMillis()

	  if (currentTime - lastTimeWhenShowedToast < TIMEOUT_BETWEEN_IDENTICAL_MESSAGES
		&& lastMessageWhichShowedInToast == newMessage
	  ) return

	  lastTimeWhenShowedToast = currentTime
	  lastMessageWhichShowedInToast = newMessage

	  coroutineScope.launchMain({
		toast?.cancel()
		toast = Toast.makeText(context, newMessage, Toast.LENGTH_SHORT)
		toast?.show()
	  }, { it.printStackTrace() })
    }

    private companion object {
	  private const val TIMEOUT_BETWEEN_IDENTICAL_MESSAGES = 3000
    }
}