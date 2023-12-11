package com.example.unsplash.data.models.converters

import com.example.unsplash.data.room_db.entity_list_photos.PhotoEntity
import com.example.unsplash.presentation.fragment_photos_main.models.Photo

fun PhotoEntity.toUiModel(): Photo {
    return Photo(
	  photoID = photoID,
	  likes = counterLikes,
	  photoResource = urlImagePhoto,
	  photographerName = photographerName,
	  photoResourcePhotographerProfile = urlPhotoPhotographerProfile,
	  likedByUser = likedByUser,
    )
}
