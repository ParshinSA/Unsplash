package com.example.unsplash.presentation.fragment_navigate_by_screens

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.lifecycle.ViewModel
import com.example.unsplash.R
import com.example.unsplash._common.cicirone.Screens

class NavigateByScreensViewModel(
    private val screens: Screens,
) : ViewModel() {

    fun getFragmentById(itemId: Int): Fragment? {
	  return when (itemId) {
		R.id.photos -> screens.PhotosNavigableScreen()
		R.id.collections -> screens.CollectionNavigableScreen()
		R.id.profile -> screens.ProfileNavigableScreen()
		else -> null
	  }?.createFragment(FragmentFactory())
    }

}