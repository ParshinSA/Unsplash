package com.example.unsplash.data.data_sources.onboarding

import com.example.unsplash.R
import com.example.unsplash.presentation.fragment_onboarding.models.ItemOnboarding

class OnboardingListDatasourceMockImpl() :
    OnboardingListDatasource {

    override fun getOnboardingList(): List<ItemOnboarding> {
	  return listOf(
		ItemOnboarding(
		    title = R.string.view_photos_and_collections,
		    imageRes = R.drawable.view_photos_and_collections
		),
		ItemOnboarding(
		    title = R.string.search_photos_share_friends,
		    imageRes = R.drawable.search_photos_share_friends
		),
		ItemOnboarding(
		    title = R.string.view_photo_location,
		    imageRes = R.drawable.view_photo_location
		),
		ItemOnboarding(
		    title = R.string.two_color_theme,
		    imageRes = R.drawable.two_color_theme
		),
	  )
    }
}