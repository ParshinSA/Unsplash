package com.example.unsplash.presentation._external_dependencies

import com.example.unsplash.presentation.fragment_onboarding.models.ItemOnboarding

interface OnboardingRepository {

    fun confirmFirstLaunch()

    fun getOnboardingList(): List<ItemOnboarding>

}
