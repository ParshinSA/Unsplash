package com.example.unsplash.data.storages

import com.example.unsplash.data.models._common.TokenModel

interface TokenStorage {

    fun getAccessToken(): String

    fun saveTokenModel(tokenModel: TokenModel)

    fun getRefreshToken(): String

}
