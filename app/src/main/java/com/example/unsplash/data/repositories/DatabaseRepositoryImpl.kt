package com.example.unsplash.data.repositories

import com.example.unsplash.data.room_db.AppDatabaseRoom
import com.example.unsplash.presentation._external_dependencies.DatabaseRepository

class DatabaseRepositoryImpl(
    private val appDatabaseRoom: AppDatabaseRoom
) : DatabaseRepository {

    override fun clearAllDatabaseFiles() {
	  appDatabaseRoom.clearAllDatabaseFiles()
    }

}