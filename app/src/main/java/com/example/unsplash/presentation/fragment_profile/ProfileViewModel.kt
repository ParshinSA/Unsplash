package com.example.unsplash.presentation.fragment_profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unsplash._common.extensions.launchIO
import com.example.unsplash.presentation._external_dependencies.ProfileRepository
import com.example.unsplash.presentation.fragment_profile.models.CurrentUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext

class ProfileViewModel(
    private val repository: ProfileRepository
) : ViewModel() {

    private val currentUserMutStateFlow = MutableStateFlow<CurrentUser?>(null)
    val currentUserStateFlow get() = currentUserMutStateFlow.asStateFlow()

    fun requestInfoAboutMe() {
	  viewModelScope.launchIO(
		action = {
		    val info = repository.requestInfoAboutOwnerUser()
		    withContext(Dispatchers.Main) {
			  currentUserMutStateFlow.value = info
		    }
		},
		onError = { it.printStackTrace() }
	  )
    }

}