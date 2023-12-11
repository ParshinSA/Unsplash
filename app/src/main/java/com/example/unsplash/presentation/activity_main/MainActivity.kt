package com.example.unsplash.presentation.activity_main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.IdRes
import com.example.unsplash.R
import com.example.unsplash.presentation.AppApplication
import com.example.unsplash.presentation._common.interfaces.LogoutHandler
import com.example.unsplash.presentation.base.BaseActivity
import com.example.unsplash.presentation.base.ViewModelsFactory
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.androidx.AppNavigator
import javax.inject.Inject

class MainActivity : BaseActivity(),
    LogoutHandler {

    @Inject
    lateinit var viewModelsFactory: ViewModelsFactory
    private val viewModel by viewModels<MainViewModel> { viewModelsFactory }

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    @IdRes
    private val appContainer = R.id.appScreensContainerIntoBaseActivity
    private val navigator = AppNavigator(this, appContainer)

    private var isNotRestartApp: Boolean = true

    //******************** lifecycle ********************//
    override fun onCreate(savedInstanceState: Bundle?) {
	  super.onCreate(savedInstanceState)
	  isNotRestartApp = savedInstanceState == null
	  actionThis()
    }

    override fun onResumeFragments() {
	  super.onResumeFragments()
	  navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
	  navigatorHolder.removeNavigator()
	  super.onPause()
    }
    //******************** lifecycle ********************//

    private fun actionThis() {
	  injectDependency()
	  showStartFragment()
    }

    private fun showStartFragment() {
	  if (isNotRestartApp) viewModel.showStartFragment()
    }

    private fun injectDependency() {
	  (applicationContext as AppApplication).component.inject(this)
    }

    override fun initLogout() {
	  showExitFrg(isLogout = true)
    }
}