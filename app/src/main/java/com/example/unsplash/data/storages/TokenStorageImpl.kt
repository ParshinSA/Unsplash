package com.example.unsplash.data.storages

import android.content.Context
import com.example.unsplash.data.models._common.NULL_STUB
import com.example.unsplash.data.models._common.TokenModel

class TokenStorageImpl(
    private val context: Context
) : TokenStorage {

    private val storage by lazy {
	  context.getSharedPreferences(NAME_TOKEN_STORAGE, Context.MODE_PRIVATE)
    }

    override fun getAccessToken(): String {
	  return storage.getString(KEY_ACCESS_TOKEN, NULL_STUB) ?: NULL_STUB
    }

    override fun getRefreshToken(): String {
	  return storage.getString(KEY_REFRESH_TOKEN, NULL_STUB) ?: NULL_STUB
    }

    override fun saveTokenModel(tokenModel: TokenModel) {
	  storage.edit()
		.putString(KEY_ACCESS_TOKEN, tokenModel.accessToken)
		.putString(KEY_REFRESH_TOKEN, tokenModel.refreshToken)
		.apply()
    }

    companion object {
	  private const val NAME_TOKEN_STORAGE = "TOKEN_STORAGE"
	  private const val KEY_ACCESS_TOKEN = "KEY_ACCESS_TOKEN"
	  private const val KEY_REFRESH_TOKEN = "KEY_REFRESH_TOKEN"
    }
}