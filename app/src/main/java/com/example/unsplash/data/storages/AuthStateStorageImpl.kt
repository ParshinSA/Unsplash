package com.example.unsplash.data.storages

import android.content.Context

class AuthStateStorageImpl(
    context: Context
) : AuthStateStorage {

    private val storage by lazy {
	  context.getSharedPreferences(NAME_AUTH_STATE_STORAGE, Context.MODE_PRIVATE)
    }

    override fun isAuthUser(): Boolean {
	  return storage.getBoolean(KEY_AUTH_STATE_USER, false)
    }

    override fun changeAuthStateUser(state: Boolean) {
	  storage.edit().putBoolean(KEY_AUTH_STATE_USER, state)
		.apply()
    }

    companion object {
	  private const val NAME_AUTH_STATE_STORAGE = "NAME_AUTH_STATE_STORAGE"      // name storage
	  private const val KEY_AUTH_STATE_USER = "KEY_AUTH_STATE_USER"      // is auth user?
    }
}