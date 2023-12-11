package com.example.unsplash.data.repositories

import android.content.Intent
import com.example.unsplash._common.helpers.app_auth.AuthHelper
import com.example.unsplash._common.helpers.connection_state.ConnectionStateProvider
import com.example.unsplash.data.models._common.TokenModel
import com.example.unsplash.data.storages.AuthStateStorage
import com.example.unsplash.data.storages.TokenStorage
import com.example.unsplash.presentation._external_dependencies.AuthRepository
import net.openid.appauth.AuthorizationException
import net.openid.appauth.TokenRequest

class AuthRepositoryImpl(
    private val connectionStateProvider: ConnectionStateProvider,
    private val authStateStorage: AuthStateStorage,
    private val tokenStorage: TokenStorage,
    private val authHelper: AuthHelper,
) : AuthRepository {

    override fun getLoginPageIntent(): Intent? {
	  connectionStateProvider.isDeviceOnline() ?: return null
	  return authHelper.getLoginPageIntent()
    }

    override fun getTokenExchangeRequest(intent: Intent): TokenRequest? {
	  return authHelper.getTokenExchangeRequest(intent)
    }

    override suspend fun performTokenRequestSuspend(tokenExchangeRequest: TokenRequest): TokenModel {
	  return authHelper.performTokenRequestSuspend(tokenExchangeRequest)
    }

    override fun getAuthException(intent: Intent): AuthorizationException? {
	  return authHelper.getAuthException(intent)
    }

    override fun saveTokenModel(tokenModel: TokenModel) {
	  tokenStorage.saveTokenModel(tokenModel)
    }

    override fun appAuthServiceDispose() {
	  authHelper.appAuthServiceDispose()
    }

    override fun getLogoutPageIntent(): Intent {
	  return authHelper.getLogoutPageIntent()
    }

    override fun commitLogin() {
	  authStateStorage.changeAuthStateUser(true)
    }

    override fun commitLogout() {
	  authStateStorage.changeAuthStateUser(false)
    }

    override fun isAuthUser(): Boolean {
	  return authStateStorage.isAuthUser()
    }

}