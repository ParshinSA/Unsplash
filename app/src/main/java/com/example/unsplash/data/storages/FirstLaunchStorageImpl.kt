package com.example.unsplash.data.storages

import android.content.Context

class FirstLaunchStorageImpl(
    context: Context
) : FirstLaunchStorage {

    private val storage by lazy {
	  context.getSharedPreferences(KEY_FIRST_LAUNCH_STORAGE, Context.MODE_PRIVATE)
    }

    override fun isFirstLaunch(): Boolean {
	  return storage.getBoolean(KEY_IS_LAUNCH_START, true)
    }

    override fun confirmFirstLaunch() {
	  storage.edit().putBoolean(KEY_IS_LAUNCH_START, false).apply()
    }

    companion object {
	  private const val KEY_FIRST_LAUNCH_STORAGE = "KEY_FIRST_LAUNCH_STORAGE"
	  private const val KEY_IS_LAUNCH_START = "KEY_IS_LAUNCH_START"
    }
}