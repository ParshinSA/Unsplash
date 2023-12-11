package com.example.unsplash._common.deps_inject.modules

import com.example.unsplash._common.helpers.app_auth.AuthHelper
import com.example.unsplash._common.helpers.connection_state.ConnectionStateProvider
import com.example.unsplash._common.helpers.loading.DownloadService
import com.example.unsplash._common.helpers.local_like_state.LocalLikeStateProvider
import com.example.unsplash.data.data_sources.onboarding.OnboardingListDatasource
import com.example.unsplash.data.network.unsplash.api.PhotosServiceRemote
import com.example.unsplash.data.network.unsplash.api.ProfileServiceRemote
import com.example.unsplash.data.repositories.*
import com.example.unsplash.data.room_db.AppDatabaseRoom
import com.example.unsplash.data.storages.AuthStateStorage
import com.example.unsplash.data.storages.FirstLaunchStorage
import com.example.unsplash.data.storages.TokenStorage
import com.example.unsplash.presentation._common.some_components.local_app_messenger.AppMessenger
import com.example.unsplash.presentation._external_dependencies.*
import com.example.unsplash.presentation.fragment_collections.pagination.CollectionsLocalPagerFactory
import com.example.unsplash.presentation.fragment_collections.pagination.CollectionsRemotePagerFactory
import com.example.unsplash.presentation.fragment_photos_main.pagination.PhotosLocalPagerFactory
import com.example.unsplash.presentation.fragment_photos_main.pagination.PhotosRemotePagerFactory
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {

    @Provides
    fun providesAuthRepository(
	  connectionStateProvider: ConnectionStateProvider,
	  authStateStorage: AuthStateStorage,
	  tokenStorage: TokenStorage,
	  authHelper: AuthHelper,
    ): AuthRepository {
	  return AuthRepositoryImpl(
		connectionStateProvider = connectionStateProvider,
		authStateStorage = authStateStorage,
		tokenStorage = tokenStorage,
		authHelper = authHelper,
	  )
    }

    @Provides
    fun providesPhotosRepository(
	  connectionStateProvider: ConnectionStateProvider,
	  localLikeStateProvider: LocalLikeStateProvider,
	  remotePager: PhotosRemotePagerFactory,
	  localPager: PhotosLocalPagerFactory,
	  photosService: PhotosServiceRemote,
	  downloadService: DownloadService,
    ): PhotosRepository {
	  return PhotosRepositoryImpl(
		connectionStateProvider = connectionStateProvider,
		localLikeStateProvider = localLikeStateProvider,
		remotePager = remotePager,
		localPager = localPager,
		photosService = photosService,
		downloadService = downloadService,
	  )
    }

    @Provides
    fun providesProfileRepository(
	  profileServiceRemote: ProfileServiceRemote,
	  appMessenger: AppMessenger,
    ): ProfileRepository {
	  return ProfileRepositoryImpl(
		profileServiceRemote = profileServiceRemote,
		appMessenger = appMessenger
	  )
    }

    @Provides
    fun providesCollectionsRepository(
	  connectionStateProvider: ConnectionStateProvider,
	  remotePager: CollectionsRemotePagerFactory,
	  localPager: CollectionsLocalPagerFactory,
    ): CollectionsRepository {
	  return CollectionsRepositoryImpl(
		connectionStateProvider = connectionStateProvider,
		remotePager = remotePager,
		localPager = localPager
	  )
    }

    @Provides
    fun providesOnboardingRepository(
	  onboardingListDatasource: OnboardingListDatasource,
	  firstLaunchStorage: FirstLaunchStorage,
    ): OnboardingRepository {
	  return OnboardingRepositoryImpl(
		onboardingListDatasource = onboardingListDatasource,
		firstLaunchStorage = firstLaunchStorage,
	  )
    }


    @Provides
    fun providesDatabaseRepository(
	  appDatabaseRoom: AppDatabaseRoom
    ): DatabaseRepository {
	  return DatabaseRepositoryImpl(
		appDatabaseRoom = appDatabaseRoom
	  )
    }

}