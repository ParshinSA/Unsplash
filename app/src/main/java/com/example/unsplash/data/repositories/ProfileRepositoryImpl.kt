package com.example.unsplash.data.repositories

import com.example.unsplash.R
import com.example.unsplash.data.models.converters.toUiModel
import com.example.unsplash.data.network.unsplash.api.ProfileServiceRemote
import com.example.unsplash.presentation._common.some_components.local_app_messenger.AppMessenger
import com.example.unsplash.presentation._external_dependencies.ProfileRepository
import com.example.unsplash.presentation.fragment_profile.models.CurrentUser

class ProfileRepositoryImpl(
    private val profileServiceRemote: ProfileServiceRemote,
    private val appMessenger: AppMessenger,
) : ProfileRepository {

    override suspend fun requestInfoAboutOwnerUser(): CurrentUser? {
	  val result = profileServiceRemote.getMyInfo().body().toUiModel()
	  result ?: appMessenger.sendMessage(R.string.failed_to_display_data)
	  return result
    }

}