package com.example.unsplash.presentation.abstract_fragment_navigable_screen

import android.content.Context
import androidx.fragment.app.FragmentTransaction
import com.example.unsplash.R
import com.example.unsplash._common.cicirone.Screens
import com.example.unsplash._common.cicirone.routers.CiceroneProvider
import com.example.unsplash._common.helpers.connection_state.ConnectionStateProvider
import com.example.unsplash.databinding.AbsFragmentNavigableScreenBinding
import com.example.unsplash.presentation.AppApplication
import com.example.unsplash.presentation._common.interfaces.BackPressedListener
import com.example.unsplash.presentation._common.some_components.CustomNavigator
import com.example.unsplash.presentation._common.some_components.local_app_messenger.AppMessenger
import com.example.unsplash.presentation.base.BaseFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen
import javax.inject.Inject

abstract class NavigableScreenAbstractFragment :
    BaseFragment<AbsFragmentNavigableScreenBinding>(AbsFragmentNavigableScreenBinding::inflate),
    BackPressedListener {

    abstract val routerTag: String

    @Inject
    lateinit var appMessenger: AppMessenger

    @Inject
    lateinit var screens: Screens

    @Inject
    lateinit var ciceroneProvider: CiceroneProvider

    @Inject
    lateinit var connectionStateProvider: ConnectionStateProvider

    private val cicerone by lazy { ciceroneProvider.getCicerone(routerTag) }

    private val navigatorHolder by lazy { cicerone.getNavigatorHolder() }

    private val navigator by lazy {
	  CustomNavigator(requireActivity(), R.id.fmLayScreen, childFragmentManager)
    }

    private val router by lazy { cicerone.router }

    //******************** lifecycle ********************//
    override fun onAttach(context: Context) {
	  inject()
	  super.onAttach(context)
    }

    override fun onResume() {
	  super.onResume()
	  navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
	  navigatorHolder.removeNavigator()
	  super.onPause()
    }
    //******************** lifecycle ********************//

    @Synchronized
    fun navigateTo(
	  targetFragmentScreen: FragmentScreen,
	  clearContainer: Boolean,
	  settingsTransactions: (FragmentTransaction.() -> Unit)? = null
    ): Boolean {
	  return try {
		settingsTransactions?.let { navigator.setSettingsTransaction(it) }
		router.navigateTo(targetFragmentScreen, clearContainer)
		true
	  } catch (t: Throwable) {
		t.printStackTrace()
		false
	  }
    }

    override fun onBackPressed(): Boolean {
	  val fragments = childFragmentManager.fragments
	  val initialFragment = fragments.firstOrNull() ?: return false
	  return if (initialFragment.isVisible) false
	  else {
		router.exit()
		true
	  }
    }

    private fun inject() {
	  (requireContext().applicationContext as AppApplication).component.inject(this)
    }

}