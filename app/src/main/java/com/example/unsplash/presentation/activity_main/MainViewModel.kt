package com.example.unsplash.presentation.activity_main

import androidx.lifecycle.ViewModel
import com.example.unsplash._common.cicirone.Screens
import com.example.unsplash._common.cicirone.routers.GlobalRouter
import com.example.unsplash.data.storages.FirstLaunchStorage
import com.example.unsplash.presentation._external_dependencies.AuthRepository

class MainViewModel(
    private val firstLaunchStorage: FirstLaunchStorage,
    private val appAuthRepository: AuthRepository,
    private val globalRouter: GlobalRouter,
    private val screens: Screens,
) : ViewModel() {

    fun showStartFragment() {
	  when {
		firstLaunchStorage.isFirstLaunch() -> showOnboarding()
		appAuthRepository.isAuthUser() -> showNavigateScreen()
		else -> showLogin()
	  }
    }

    private fun showOnboarding() {
	  globalRouter.router.navigateTo(screens.OnboardingFragment())
    }

    private fun showLogin() {
	  globalRouter.router.navigateTo(screens.LoginFragment())
    }

    private fun showNavigateScreen() {
	  globalRouter.router.navigateTo(screens.NavigateFragment())
    }

}