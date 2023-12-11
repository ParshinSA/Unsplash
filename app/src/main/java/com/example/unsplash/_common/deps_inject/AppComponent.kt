package com.example.unsplash._common.deps_inject

import android.content.Context
import com.example.unsplash._common.deps_inject.modules.*
import com.example.unsplash._common.deps_inject.scopes.AppScope
import com.example.unsplash.presentation.abstract_fragment_navigable_screen.NavigableScreenAbstractFragment
import com.example.unsplash.presentation.abstract_fragment_photos.PhotosAbstractFragment
import com.example.unsplash.presentation.activity_main.MainActivity
import com.example.unsplash.presentation.base.BaseActivity
import com.example.unsplash.presentation.fragment_collections.CollectionsFragment
import com.example.unsplash.presentation.fragment_exit.ExitFragment
import com.example.unsplash.presentation.fragment_login.LoginFragment
import com.example.unsplash.presentation.fragment_navigate_by_screens.NavigateByScreensFragment
import com.example.unsplash.presentation.fragment_onboarding.OnboardingFragment
import com.example.unsplash.presentation.fragment_photo_details.PhotoDetailsFragment
import com.example.unsplash.presentation.fragment_profile.ProfileFragment
import dagger.BindsInstance
import dagger.Component

@Component(
    modules = [
	  ConnectionStateProviderModule::class,
	  UserLocationProviderModule::class,
	  SomeComponentsModule::class,
	  UiComponentsModule::class,
	  NotificationModule::class,
	  NavigationModule::class,
	  ViewModelsModule::class,
	  RepositoryModule::class,
	  DataSourceModule::class,
	  ApiServiceModule::class,
	  StorageModule::class,
	  NetworkModule::class,
	  DbRoomModule::class,
	  PagerModule::class,
	  AppModule::class,
    ]
)
@AppScope
interface AppComponent {

    fun inject(screenFragment: NavigableScreenAbstractFragment)

    fun inject(absFragment: PhotosAbstractFragment)

    fun inject(fragment: NavigateByScreensFragment)
    fun inject(fragment: PhotoDetailsFragment)
    fun inject(fragment: CollectionsFragment)
    fun inject(fragment: OnboardingFragment)
    fun inject(fragment: ProfileFragment)
    fun inject(fragment: LoginFragment)
    fun inject(fragment: ExitFragment)

    fun inject(activity: MainActivity)
    fun inject(activity: BaseActivity)

    @Component.Builder
    interface Builder {
	  @BindsInstance
	  fun addContext(context: Context): Builder
	  fun build(): AppComponent
    }
}