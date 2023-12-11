package com.example.unsplash.data.models.converters

import com.example.unsplash.data.models._common.NULL_STUB
import com.example.unsplash.data.models.dto.CurrentUserDto
import com.example.unsplash.presentation.fragment_profile.models.CurrentUser

fun CurrentUserDto?.toUiModel(): CurrentUser? {
    this ?: return null
    return CurrentUser(
	  id = id ?: return null,
	  userName = userName ?: return null,
	  name = name ?: NULL_STUB,
	  profileImage = profileImage.toUiModel(),
	  totalLikes = totalLikes ?: 0,
	  email = email ?: NULL_STUB
    )
}