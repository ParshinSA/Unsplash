package com.example.unsplash.presentation.fragment_onboarding

import androidx.lifecycle.ViewModel
import com.example.unsplash._common.cicirone.Screens
import com.example.unsplash._common.cicirone.routers.GlobalRouter
import com.example.unsplash.presentation._external_dependencies.OnboardingRepository
import com.example.unsplash.presentation.fragment_onboarding.models.ItemOnboarding

class OnboardingViewModel(
    private val repository: OnboardingRepository,
    private val globalRouter: GlobalRouter,
    private val screens: Screens,
) : ViewModel() {

    fun closeOnboarding() {
	  repository.confirmFirstLaunch()
	  showLoginScreen()
    }

    private fun showLoginScreen() {
	  globalRouter.router.newRootScreen(screens.LoginFragment())
    }

    fun getOnboardingList(): List<ItemOnboarding> {
	  return repository.getOnboardingList()
    }

}