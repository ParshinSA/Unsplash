@file:Suppress("FunctionName")

package com.example.unsplash._common.cicirone

import com.example.unsplash.presentation.fragment_collections.CollectionsFragment
import com.example.unsplash.presentation.fragment_login.LoginFragment
import com.example.unsplash.presentation.fragment_navigate_by_screens.NavigateByScreensFragment
import com.example.unsplash.presentation.fragment_onboarding.OnboardingFragment
import com.example.unsplash.presentation.fragment_photo_details.PhotoDetailsFragment
import com.example.unsplash.presentation.fragment_photos_from_collections.PhotosFromCollectionFragment
import com.example.unsplash.presentation.fragment_photos_main.MainPhotosFragment
import com.example.unsplash.presentation.fragment_profile.ProfileFragment
import com.example.unsplash.presentation.screens.collections.CollectionsNavigableScreenFragment
import com.example.unsplash.presentation.screens.photos.PhotosNavigableScreenFragment
import com.example.unsplash.presentation.screens.profile.ProfileNavigableScreenFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

class Screens {

    fun OnboardingFragment() = FragmentScreen(OnboardingFragment::class.java.name) {
	  OnboardingFragment.newInstance()
    }

    fun LoginFragment() = FragmentScreen(LoginFragment::class.java.name) {
	  LoginFragment.newInstance()
    }

    fun NavigateFragment() = FragmentScreen(NavigateByScreensFragment::class.java.name) {
	  NavigateByScreensFragment.newInstance()
    }

    fun PhotosNavigableScreen() = FragmentScreen(PhotosNavigableScreenFragment::class.java.name) {
	  PhotosNavigableScreenFragment.newInstance()
    }

    fun PhotosFragment() = FragmentScreen(MainPhotosFragment::class.java.name) {
	  MainPhotosFragment.newInstance()
    }

    fun PhotosFromCollectionsFragment(
	  collectionsId: String,
	  commonInfo: String,
	  title: String
    ) = FragmentScreen(PhotosFromCollectionFragment::class.java.name) {
	  PhotosFromCollectionFragment.newInstance(
		collectionCommonInfo = commonInfo,
		collectionId = collectionsId,
		collectionTitle = title,
	  )
    }

    fun PhotoDetailsFragment(photoId: String) =
	  FragmentScreen(PhotoDetailsFragment::class.java.name) {
		PhotoDetailsFragment.newInstance(photoId = photoId)
	  }

    fun CollectionNavigableScreen() =
	  FragmentScreen(CollectionsNavigableScreenFragment::class.java.name) {
		CollectionsNavigableScreenFragment.newInstance()
	  }

    fun CollectionsFragment() = FragmentScreen(CollectionsFragment::class.java.name) {
	  CollectionsFragment.newInstance()
    }

    fun ProfileNavigableScreen() = FragmentScreen(ProfileNavigableScreenFragment::class.java.name) {
	  ProfileNavigableScreenFragment.newInstance()
    }

    fun ProfileFragment() = FragmentScreen(ProfileFragment::class.java.name) {
	  ProfileFragment.newInstance()
    }

}