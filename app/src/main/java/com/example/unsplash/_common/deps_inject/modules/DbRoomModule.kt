package com.example.unsplash._common.deps_inject.modules

import android.content.Context
import androidx.room.Room
import com.example.unsplash._common.deps_inject.scopes.AppScope
import com.example.unsplash.data.room_db.AppDatabaseRoom
import com.example.unsplash.data.room_db.entity_list_photos.PhotosDao
import com.example.unsplash.data.room_db.entity_list_photos_collection.PhotosCollectionsDao
import com.example.unsplash.data.room_db.entity_loding_photos.LoadingPhotoDao
import dagger.Module
import dagger.Provides

@Module
class DbRoomModule {

    @Provides
    @AppScope
    fun providesAppDatabaseRoom(context: Context): AppDatabaseRoom {
	  return Room.databaseBuilder(
		context,
		AppDatabaseRoom::class.java,
		AppDatabaseRoom.NAME_DB
	  )
		.fallbackToDestructiveMigration()
		.build()
    }

    @Provides
    fun providesPhotoListDao(db: AppDatabaseRoom): PhotosDao {
	  return db.getPhotosDao()
    }

    @Provides
    fun providesPhotosCollectionDao(db: AppDatabaseRoom): PhotosCollectionsDao {
	  return db.getPhotosCollectionDao()
    }

    @Provides
    fun providesLoadingPhotoDao(db: AppDatabaseRoom): LoadingPhotoDao {
	  return db.getLoadingPhotoDao()
    }

}