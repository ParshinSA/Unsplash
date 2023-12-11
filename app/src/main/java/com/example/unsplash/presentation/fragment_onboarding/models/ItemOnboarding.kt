package com.example.unsplash.presentation.fragment_onboarding.models

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class ItemOnboarding(
    @StringRes val title: Int,
    @DrawableRes val imageRes: Int
)
