package com.example.unsplash.presentation.screens.profile

import com.example.unsplash.presentation.abstract_fragment_navigable_screen.NavigableScreenAbstractFragment

class ProfileNavigableScreenFragment :
    NavigableScreenAbstractFragment() {

    override val routerTag = ROUTER_TAG

    override fun thisCustomActions() {
	  navigateTo(screens.ProfileFragment(), true)
    }

    override fun thisCustomSettings() {
	  // yet not settings
    }

    companion object {
	  private const val ROUTER_TAG = "ProfileNavigableScreenFragment"

	  fun newInstance() = ProfileNavigableScreenFragment()
    }

}
