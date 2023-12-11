package com.example.unsplash._common.deps_inject.modules

import com.example.unsplash.data.storages.LoadIdStorage
import androidx.lifecycle.ViewModel
import com.example.unsplash._common.cicirone.Screens
import com.example.unsplash._common.cicirone.routers.GlobalRouter
import com.example.unsplash._common.deps_inject.scopes.AppScope
import com.example.unsplash.data.storages.FirstLaunchStorage
import com.example.unsplash.presentation._common.some_components.local_app_messenger.AppMessenger
import com.example.unsplash.presentation._external_dependencies.*
import com.example.unsplash.presentation.activity_main.MainViewModel
import com.example.unsplash.presentation.base.ViewModelsFactory
import com.example.unsplash.presentation.fragment_collections.CollectionsViewModel
import com.example.unsplash.presentation.fragment_exit.ExitViewModel
import com.example.unsplash.presentation.fragment_liked_photos.LikedPhotosViewModel
import com.example.unsplash.presentation.fragment_login.LoginViewModel
import com.example.unsplash.presentation.fragment_navigate_by_screens.NavigateByScreensViewModel
import com.example.unsplash.presentation.fragment_onboarding.OnboardingViewModel
import com.example.unsplash.presentation.fragment_photo_details.PhotoDetailsViewModel
import com.example.unsplash.presentation.fragment_photos_from_collections.PhotosFromCollectionViewModel
import com.example.unsplash.presentation.fragment_photos_main.MainPhotosViewModel
import com.example.unsplash.presentation.fragment_profile.ProfileViewModel
import dagger.Module
import dagger.Provides
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap
import javax.inject.Provider

@Module
class ViewModelsModule {

    @Provides
    @AppScope
    fun providesViewModelFactory(
	  viewModelMap: Map<Class<*>, @JvmSuppressWildcards Provider<ViewModel>>
    ): ViewModelsFactory {
	  return ViewModelsFactory(
		viewModelMap = viewModelMap
	  )
    }

    @Provides
    @[IntoMap ClassKey(MainViewModel::class)]
    fun providesMainViewModel(
	  firstLaunchStorage: FirstLaunchStorage,
	  appAuthRepository: AuthRepository,
	  globalRouter: GlobalRouter,
	  screens: Screens,
    ): ViewModel {
	  return MainViewModel(
		firstLaunchStorage = firstLaunchStorage,
		appAuthRepository = appAuthRepository,
		globalRouter = globalRouter,
		screens = screens
	  )
    }

    @Provides
    @[IntoMap ClassKey(LoginViewModel::class)]
    fun provideLoginViewModel(
	  authRepository: AuthRepository,
	  globalRouter: GlobalRouter,
	  appMessenger: AppMessenger,
	  screens: Screens,
    ): ViewModel {
	  return LoginViewModel(
		authRepository = authRepository,
		globalRouter = globalRouter,
		appMessenger = appMessenger,
		screens = screens
	  )
    }

    @Provides
    @[IntoMap ClassKey(OnboardingViewModel::class)]
    fun provideOnboardingViewModel(
	  globalRouter: GlobalRouter,
	  screens: Screens,
	  repository: OnboardingRepository,
    ): ViewModel {
	  return OnboardingViewModel(
		globalRouter = globalRouter,
		screens = screens,
		repository = repository
	  )
    }

    @Provides
    @[IntoMap ClassKey(NavigateByScreensViewModel::class)]
    fun provideNavigateViewModel(
	  screens: Screens
    ): ViewModel {
	  return NavigateByScreensViewModel(
		screens = screens
	  )
    }

    @Provides
    @[IntoMap ClassKey(MainPhotosViewModel::class)]
    fun provideMainPhotoViewModel(
	  repository: PhotosRepository
    ): ViewModel {
	  return MainPhotosViewModel(
		photosRepository = repository
	  )
    }

    @Provides
    @[IntoMap ClassKey(LikedPhotosViewModel::class)]
    fun provideLikedPhotosViewModel(
	  repository: PhotosRepository
    ): ViewModel {
	  return LikedPhotosViewModel(
		repository = repository
	  )
    }

    @Provides
    @[IntoMap ClassKey(CollectionsViewModel::class)]
    fun providePhotoCollectionsViewModel(
	  repository: CollectionsRepository
    ): ViewModel {
	  return CollectionsViewModel(
		repository = repository
	  )
    }

    @Provides
    @[IntoMap ClassKey(ProfileViewModel::class)]
    fun provideProfileViewModel(
	  repository: ProfileRepository
    ): ViewModel {
	  return ProfileViewModel(
		repository
	  )
    }

    @Provides
    @[IntoMap ClassKey(PhotoDetailsViewModel::class)]
    fun provideDetailedPhotoInfoViewModel(
	  repository: PhotosRepository,
	  loadIdStorage: LoadIdStorage,
	  appMessenger: AppMessenger,
    ): ViewModel {
	  return PhotoDetailsViewModel(
		loadIdStorage = loadIdStorage,
		appMessenger = appMessenger,
		repository = repository,
	  )
    }

    @Provides
    @[IntoMap ClassKey(PhotosFromCollectionViewModel::class)]
    fun providePhotosFromCollectionViewModel(
	  repository: PhotosRepository
    ): ViewModel {
	  return PhotosFromCollectionViewModel(
		repository = repository
	  )
    }

    @Provides
    @[IntoMap ClassKey(ExitViewModel::class)]
    fun provideExitViewModel(
	  databaseRepository: DatabaseRepository,
	  authRepository: AuthRepository,
	  globalRouter: GlobalRouter,
	  screens: Screens,
    ): ViewModel {
	  return ExitViewModel(
		databaseRepository = databaseRepository,
		authRepository = authRepository,
		globalRouter = globalRouter,
		screens = screens
	  )
    }

}