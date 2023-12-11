package com.example.unsplash._common.deps_inject.modules

import com.example.unsplash._common.helpers.loading.HelperWithCachingFile
import com.example.unsplash.data.data_sources.collection.CollectionsDatasourceLocal
import com.example.unsplash.data.data_sources.collection.CollectionsDatasourceLocalImpl
import com.example.unsplash.data.data_sources.collection.CollectionsDatasourceRemote
import com.example.unsplash.data.data_sources.collection.CollectionsDatasourceRemoteImpl
import com.example.unsplash.data.data_sources.onboarding.OnboardingListDatasource
import com.example.unsplash.data.data_sources.onboarding.OnboardingListDatasourceMockImpl
import com.example.unsplash.data.data_sources.photo.PhotoDatasourceLocal
import com.example.unsplash.data.data_sources.photo.PhotoDatasourceLocalImpl
import com.example.unsplash.data.data_sources.photo.PhotoDatasourceRemote
import com.example.unsplash.data.data_sources.photo.PhotoDatasourceRemoteImpl
import com.example.unsplash.data.network.unsplash.api.CollectionsServiceRemote
import com.example.unsplash.data.network.unsplash.api.PhotosServiceRemote
import com.example.unsplash.data.room_db.entity_list_photos.PhotosDao
import com.example.unsplash.data.room_db.entity_list_photos_collection.PhotosCollectionsDao
import dagger.Module
import dagger.Provides

@Module
class DataSourceModule {

    @Provides
    fun providesPhotosLocalDatasource(
	  helperWithCachingFile: HelperWithCachingFile,
	  photosDao: PhotosDao
    ): PhotoDatasourceLocal {
	  return PhotoDatasourceLocalImpl(
		helperWithCachingFile = helperWithCachingFile,
		photosDao = photosDao
	  )
    }

    @Provides
    fun providesPhotosRemoteDatasource(
	  photosServiceRemote: PhotosServiceRemote
    ): PhotoDatasourceRemote {
	  return PhotoDatasourceRemoteImpl(
		photosServiceRemote = photosServiceRemote
	  )
    }

    @Provides
    fun providesCollectionsDatasourceRemote(
	  collectionsServiceRemote: CollectionsServiceRemote
    ): CollectionsDatasourceRemote {
	  return CollectionsDatasourceRemoteImpl(
		collectionsServiceRemote = collectionsServiceRemote
	  )
    }

    @Provides
    fun providesCollectionsDatasourceLocal(
	  helperWithCachingFile: HelperWithCachingFile,
	  collectionsServiceDao: PhotosCollectionsDao,
    ): CollectionsDatasourceLocal {
	  return CollectionsDatasourceLocalImpl(
		helperWithCachingFile = helperWithCachingFile,
		collectionsServiceDao = collectionsServiceDao
	  )
    }

    @Provides
    fun providesOnboardingDatasource(): OnboardingListDatasource {
	  return OnboardingListDatasourceMockImpl()
    }

}