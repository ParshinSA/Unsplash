package com.example.unsplash.presentation._common.some_components

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.github.terrakok.cicerone.androidx.AppNavigator

class CustomNavigator(
    activity: FragmentActivity,
    containerId: Int,
    fragmentManager: FragmentManager,
) : AppNavigator(activity, containerId, fragmentManager) {

    private var settingsTransaction: (FragmentTransaction.() -> Unit)? = null

    fun setSettingsTransaction(settings: FragmentTransaction.() -> Unit) {
	  settingsTransaction = settings
    }

    override fun setupFragmentTransaction(
	  fragmentTransaction: FragmentTransaction,
	  currentFragment: Fragment?,
	  nextFragment: Fragment?
    ) {
	  settingsTransaction?.let { settings -> fragmentTransaction.apply(settings) }
	  currentFragment?.let { fragmentTransaction.hide(currentFragment) }
	  super.setupFragmentTransaction(fragmentTransaction, currentFragment, nextFragment)
	  settingsTransaction = null
    }

}