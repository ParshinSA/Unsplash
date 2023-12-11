package com.example.unsplash._common.deps_inject.modules

import com.example.unsplash.presentation._common.some_components.theme_color_provider.ThemeColorProvider
import com.example.unsplash.presentation.ui.BaseSnackbarFactory
import dagger.Module
import dagger.Provides

@Module
class UiComponentsModule {

    @Provides
    fun providesBaseSnackbar(
	  themeColorProvider: ThemeColorProvider,
    ): BaseSnackbarFactory {
	  return BaseSnackbarFactory(
		themeColorProvider = themeColorProvider,
	  )
    }
}