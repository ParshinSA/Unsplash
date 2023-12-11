package com.example.unsplash._common.deps_inject.modules

import com.example.unsplash._common.helpers.connection_state.ConnectionStateProvider
import com.example.unsplash.data.data_sources.collection.CollectionsDatasourceLocal
import com.example.unsplash.data.data_sources.collection.CollectionsDatasourceRemote
import com.example.unsplash.data.data_sources.photo.PhotoDatasourceLocal
import com.example.unsplash.data.data_sources.photo.PhotoDatasourceRemote
import com.example.unsplash.data.paging_source.CollectionsLocalPagerFactoryImpl
import com.example.unsplash.data.paging_source.CollectionsRemotePagerFactoryImpl
import com.example.unsplash.data.paging_source.PhotosLocalPagerFactoryImpl
import com.example.unsplash.data.paging_source.PhotosRemotePagerFactoryImpl
import com.example.unsplash.presentation.fragment_collections.pagination.CollectionsLocalPagerFactory
import com.example.unsplash.presentation.fragment_collections.pagination.CollectionsRemotePagerFactory
import com.example.unsplash.presentation.fragment_photos_main.pagination.PhotosLocalPagerFactory
import com.example.unsplash.presentation.fragment_photos_main.pagination.PhotosRemotePagerFactory
import dagger.Module
import dagger.Provides

@Module
class PagerModule {

    @Provides
    fun providesPhotosRemotePagerFactory(
	  connectionStateProvider: ConnectionStateProvider,
	  datasourceRemote: PhotoDatasourceRemote,
	  datasourceLocal: PhotoDatasourceLocal,
    ): PhotosRemotePagerFactory {
	  return PhotosRemotePagerFactoryImpl(
		connectionStateProvider = connectionStateProvider,
		datasourceRemote = datasourceRemote,
		datasourceLocal = datasourceLocal
	  )
    }

    @Provides
    fun providesCollectionsRemotePagerFactory(
	  connectionStateProvider: ConnectionStateProvider,
	  datasourceLocal: CollectionsDatasourceLocal,
	  datasourceRemote: CollectionsDatasourceRemote
    ): CollectionsRemotePagerFactory {
	  return CollectionsRemotePagerFactoryImpl(
		connectionStateProvider = connectionStateProvider,
		datasourceLocal = datasourceLocal,
		datasourceRemote = datasourceRemote
	  )
    }

    @Provides
    fun providesPhotosLocalPagerFactory(
	  datasource: PhotoDatasourceLocal
    ): PhotosLocalPagerFactory {
	  return PhotosLocalPagerFactoryImpl(
		datasource = datasource
	  )
    }

    @Provides
    fun providesCollectionsLocalPagerFactory(
	  datasource: CollectionsDatasourceLocal
    ): CollectionsLocalPagerFactory {
	  return CollectionsLocalPagerFactoryImpl(
		datasource = datasource
	  )
    }

}