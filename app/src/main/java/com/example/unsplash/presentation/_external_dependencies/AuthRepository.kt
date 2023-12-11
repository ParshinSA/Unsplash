package com.example.unsplash.presentation._external_dependencies

import android.content.Intent
import com.example.unsplash.data.models._common.TokenModel
import net.openid.appauth.AuthorizationException
import net.openid.appauth.TokenRequest

interface AuthRepository {

    fun isAuthUser(): Boolean

    fun getLoginPageIntent(): Intent?
    fun getLogoutPageIntent(): Intent

    fun commitLogin()
    fun commitLogout()

    fun saveTokenModel(tokenModel: TokenModel)

    fun getTokenExchangeRequest(intent: Intent): TokenRequest?
    fun getAuthException(intent: Intent): AuthorizationException?

    fun appAuthServiceDispose()

    suspend fun performTokenRequestSuspend(tokenExchangeRequest: TokenRequest): TokenModel

}
