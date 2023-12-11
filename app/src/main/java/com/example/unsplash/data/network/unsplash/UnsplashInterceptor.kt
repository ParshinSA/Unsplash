package com.example.unsplash.data.network.unsplash

import com.example.unsplash._common.cicirone.Screens
import com.example.unsplash._common.cicirone.routers.GlobalRouter
import com.example.unsplash._common.extensions.launchMain
import com.example.unsplash.data.storages.TokenStorage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import okhttp3.Interceptor
import okhttp3.Response

class UnsplashInterceptor(
    private val tokenStorage: TokenStorage,
    private val globalRouter: GlobalRouter,
    private val screens: Screens,
) : Interceptor {

    private var counterAttempts = 0

    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    private val tokenFromStorage get() = tokenStorage.getAccessToken()

    override fun intercept(chain: Interceptor.Chain): Response {
	  val request = chain.request()
	  val requestBuilder = request.newBuilder()
	  val titleAuthHeader = "Authorization"
	  val sessionToken = "Bearer $tokenFromStorage"

	  if (request.header(titleAuthHeader) == null) {
		if (tokenFromStorage == "") {
		    coroutineScope.launchMain(
			  action = { globalRouter.router.newRootScreen(screens.LoginFragment()) },
			  onError = { it.printStackTrace() }
		    )
		} else requestBuilder.addHeader(titleAuthHeader, sessionToken)
	  }

	  return try {
		chain.proceed(requestBuilder.build())
	  } catch (t: Throwable) {
		return if (counterAttempts < 10) {
		    counterAttempts++
		    t.printStackTrace()
		    intercept(chain)
		} else {
		    throw t
		}
	  }
    }
}