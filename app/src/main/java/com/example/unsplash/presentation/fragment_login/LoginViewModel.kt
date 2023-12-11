package com.example.unsplash.presentation.fragment_login

import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unsplash.R
import com.example.unsplash._common.cicirone.Screens
import com.example.unsplash._common.cicirone.routers.GlobalRouter
import com.example.unsplash._common.extensions.launchIO
import com.example.unsplash._common.extensions.launchMain
import com.example.unsplash.data.models._common.TokenModel
import com.example.unsplash.presentation._common.some_components.local_app_messenger.AppMessenger
import com.example.unsplash.presentation._external_dependencies.AuthRepository
import com.example.unsplash.presentation.fragment_login.models.LoginFrgUiState.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import net.openid.appauth.TokenRequest

class LoginViewModel(
    private val authRepository: AuthRepository,
    private val globalRouter: GlobalRouter,
    private val appMessenger: AppMessenger,
    private val screens: Screens,
) : ViewModel() {

    private val uiStateMutStateFlow = MutableStateFlow(ON_STARTED_FRAGMENT)
    private val loginPageIntentMutSharedFlow = MutableSharedFlow<Intent?>()

    val loginPageIntentSharedFlow
	  get() = loginPageIntentMutSharedFlow.shareIn(
		viewModelScope,
		SharingStarted.Lazily
	  )

    val uiStateStateFlow get() = uiStateMutStateFlow.asStateFlow()

    fun openLoginPage() {
	  viewModelScope.launchIO(
		action = {
		    uiStateMutStateFlow.emit(ON_INITIALIZED_LOGIN)
		    val pageIntent = authRepository.getLoginPageIntent()
		    if (pageIntent == null) uiStateMutStateFlow.emit(ON_UNSUCCESSFUL_LOGIN)
		    else loginPageIntentMutSharedFlow.emit(pageIntent)
		}, onError = { it.printStackTrace() }
	  )
    }

    fun handleAuthResponseIntent(intent: Intent) {
	  viewModelScope.launchMain(
		action = {
		    val tokenExchangeRequest = authRepository.getTokenExchangeRequest(intent)

		    if (tokenExchangeRequest == null) {
			  val exception = authRepository.getAuthException(intent) ?: return@launchMain
			  onAuthFailed(exception)
		    } else {
			  onAuthCodReceived(tokenExchangeRequest)
		    }
		}, onError = { exception ->
		    exception.printStackTrace()
		    onAuthFailed(exception)
		}
	  )
    }

    private suspend fun onAuthCodReceived(tokenExchangeRequest: TokenRequest) {
	  val tokenModel = authRepository.performTokenRequestSuspend(tokenExchangeRequest)
	  onAuthSuccess(tokenModel)
    }

    private suspend fun onAuthFailed(exception: Throwable) {
	  exception.printStackTrace()
	  uiStateMutStateFlow.emit(ON_UNSUCCESSFUL_LOGIN)
	  appMessenger.sendMessage(R.string.app_auth_failed)
    }

    private suspend fun onAuthSuccess(tokenModel: TokenModel) {
	  withContext(Dispatchers.Main) {
		uiStateMutStateFlow.emit(ON_SUCCESSFUL_LOGIN)
		appMessenger.sendMessage(R.string.app_auth_success)
		openNavFragment()
	  }

	  authRepository.saveTokenModel(tokenModel)
	  authRepository.commitLogin()
    }

    private fun openNavFragment() {
	  globalRouter.router.newRootScreen(screens.NavigateFragment())
    }

    override fun onCleared() {
	  authRepository.appAuthServiceDispose()
	  super.onCleared()
    }

}