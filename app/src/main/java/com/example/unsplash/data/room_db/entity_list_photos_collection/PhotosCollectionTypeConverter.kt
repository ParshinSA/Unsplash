package com.example.unsplash.data.room_db.entity_list_photos_collection

import androidx.room.TypeConverter
import com.google.gson.Gson

class PhotosCollectionTypeConverter {

    @TypeConverter
    fun listStringToString(res: List<String>): String {
	  return Gson().toJson(res)
    }

    @TypeConverter
    fun stringToListString(res: String): List<String> {
	  return Gson().fromJson<List<String>>(res, List::class.java)
    }

}