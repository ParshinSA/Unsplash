package com.example.unsplash.data.data_sources.onboarding

import com.example.unsplash.presentation.fragment_onboarding.models.ItemOnboarding

interface OnboardingListDatasource {
    fun getOnboardingList(): List<ItemOnboarding>
}