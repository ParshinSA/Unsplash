package com.example.unsplash.presentation._external_dependencies

import com.example.unsplash.presentation.fragment_profile.models.CurrentUser

interface ProfileRepository {

    suspend fun requestInfoAboutOwnerUser(): CurrentUser?

}
