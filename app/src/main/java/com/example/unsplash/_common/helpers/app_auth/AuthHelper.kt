package com.example.unsplash._common.helpers.app_auth

import android.content.Context
import android.content.Intent
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.net.toUri
import com.example.unsplash.data.models._common.TokenModel
import net.openid.appauth.*
import kotlin.coroutines.suspendCoroutine

class AuthHelper(
    context: Context
) {
    private val appAuthService = AuthorizationService(context)

    fun appAuthServiceDispose() {
	  appAuthService.dispose()
    }

    private val serviceConfiguration = AuthorizationServiceConfiguration(
	  AuthConfig.AUTH_URI.toUri(),
	  AuthConfig.URI_TOKEN.toUri(),
	  null,
	  AuthConfig.END_SESSION_URI.toUri()
    )

    private fun getAuthRequest(): AuthorizationRequest {
	  return AuthorizationRequest.Builder(
		serviceConfiguration,
		AuthConfig.ACCESS_KEY,
		AuthConfig.RESPONSE_TYPE,
		AuthConfig.LOGIN_REDIRECT_URI.toUri()
	  ).setScope(AuthConfig.PERMISSIONS_SCOPES)
		.build()
    }

    private fun getEndSessionRequest(): EndSessionRequest {
	  return EndSessionRequest.Builder(serviceConfiguration)
		.setPostLogoutRedirectUri(AuthConfig.LOGOUT_REDIRECT_URI.toUri())
		.build()
    }

    private fun getClientAuthentication(): ClientAuthentication {
	  return ClientSecretPost(AuthConfig.SECRET_KEY)
    }

    suspend fun performTokenRequestSuspend(
	  tokenRequest: TokenRequest
    ): TokenModel {
	  return suspendCoroutine { continuation ->
		appAuthService.performTokenRequest(
		    tokenRequest, getClientAuthentication()
		) { response, exception ->
		    when {
			  response != null -> {
				val token = TokenModel(
				    accessToken = response.accessToken.orEmpty(),
				    refreshToken = response.refreshToken.orEmpty(),
				)
				continuation.resumeWith(Result.success(token))
			  }
			  exception != null -> continuation.resumeWith(Result.failure(exception))
			  else -> error("unavailable")
		    }
		}
	  }
    }

    fun getTokenExchangeRequest(intent: Intent): TokenRequest? {
	  // формируем запрос для обмена кода на токен, null == request failed
	  return AuthorizationResponse.fromIntent(intent)
		?.createTokenExchangeRequest()
    }

    fun getAuthException(intent: Intent): AuthorizationException? {
	  return AuthorizationException.fromIntent(intent)
    }

    fun getLoginPageIntent(): Intent {
	  val customTabsIntent = CustomTabsIntent.Builder().build()
	  val authRequest = getAuthRequest()
	  return appAuthService.getAuthorizationRequestIntent(authRequest, customTabsIntent)
    }

    fun getLogoutPageIntent(): Intent {
	  val customTabsIntent = CustomTabsIntent.Builder().build()
	  val endSessionRequest = getEndSessionRequest()
	  return appAuthService.getEndSessionRequestIntent(endSessionRequest, customTabsIntent)
    }

    private object AuthConfig {
	  const val AUTH_URI = "https://unsplash.com/oauth/authorize"
	  const val URI_TOKEN = "https://unsplash.com/oauth/token"
	  const val PERMISSIONS_SCOPES = "public read_user write_user read_photos write_likes"
	  const val END_SESSION_URI = "https://unsplash.com/logout"
	  const val RESPONSE_TYPE = ResponseTypeValues.CODE

	  const val ACCESS_KEY = "uKi-bbxKqzRn1GMH1ZD1dmTri2oAvsB8-aY45zP6AxI" // client_id
	  const val SECRET_KEY = "oNRBTtwsavomonJ3lSVIyf8B_yaX-pOKkIjx2SRFaxU" // client_secret
	  const val LOGIN_REDIRECT_URI = "unsplash://com.example.unsplash/redirect_login"
	  const val LOGOUT_REDIRECT_URI = "unsplash://com.example.unsplash/redirect_logout"
    }
}

