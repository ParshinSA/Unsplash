package com.example.unsplash.presentation.fragment_exit

import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unsplash._common.cicirone.Screens
import com.example.unsplash._common.cicirone.routers.GlobalRouter
import com.example.unsplash._common.extensions.launchIO
import com.example.unsplash.presentation._external_dependencies.AuthRepository
import com.example.unsplash.presentation._external_dependencies.DatabaseRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted.Companion.Lazily
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.shareIn

class ExitViewModel(
    private val databaseRepository: DatabaseRepository,
    private val authRepository: AuthRepository,
    private val globalRouter: GlobalRouter,
    private val screens: Screens,
) : ViewModel() {

    private val isLogoutMutStateFlow = MutableStateFlow<Boolean?>(null)
    val isLogoutStateFlow get() = isLogoutMutStateFlow.asStateFlow()

    private val logoutIntentMutSharedFlow = MutableSharedFlow<Intent>()
    val logoutIntentSharedFlow get() = logoutIntentMutSharedFlow.shareIn(viewModelScope, Lazily)

    fun saveTargetExit(isLogout: Boolean) {
	  isLogoutMutStateFlow.value = isLogout
    }

    fun openLogoutPage() {
	  viewModelScope.launchIO(
		action = { logoutIntentMutSharedFlow.emit(authRepository.getLogoutPageIntent()) },
		onError = { it.printStackTrace() }
	  )
    }

    fun saveLogoutState() {
	  viewModelScope.launchIO(
		action = {
		    authRepository.commitLogout()
		    globalRouter.router.newRootScreen(screens.LoginFragment())
		},
		onError = { it.printStackTrace() }
	  )
    }

    fun clearAllDatabaseFiles() {
	  viewModelScope.launchIO(action = { databaseRepository.clearAllDatabaseFiles() },
		onError = { it.printStackTrace() })
    }
}