package com.example.unsplash.data.repositories

import com.example.unsplash.data.data_sources.onboarding.OnboardingListDatasource
import com.example.unsplash.data.storages.FirstLaunchStorage
import com.example.unsplash.presentation._external_dependencies.OnboardingRepository
import com.example.unsplash.presentation.fragment_onboarding.models.ItemOnboarding

class OnboardingRepositoryImpl(
    private val onboardingListDatasource: OnboardingListDatasource,
    private val firstLaunchStorage: FirstLaunchStorage,
) : OnboardingRepository {

    override fun confirmFirstLaunch() {
	  firstLaunchStorage.confirmFirstLaunch()
    }

    override fun getOnboardingList(): List<ItemOnboarding> {
	  return onboardingListDatasource.getOnboardingList()
    }
}
