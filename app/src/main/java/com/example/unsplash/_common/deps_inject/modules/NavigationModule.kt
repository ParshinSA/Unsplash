package com.example.unsplash._common.deps_inject.modules

import com.example.unsplash._common.cicirone.routers.CiceroneProvider
import com.example.unsplash._common.cicirone.routers.GlobalRouter
import com.example.unsplash._common.deps_inject.scopes.AppScope
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import dagger.Module
import dagger.Provides

@Module
class NavigationModule {
    private val cicerone: Cicerone<Router> = Cicerone.create()

    @Provides
    @AppScope
    fun provideRouterAppActivity(): GlobalRouter {
	  return GlobalRouter(cicerone.router)
    }

    @Provides
    @AppScope
    fun provideRouterContainerFrg(): CiceroneProvider {
	  return CiceroneProvider()
    }

    @Provides
    @AppScope
    fun provideNavigatorHolder(): NavigatorHolder {
	  return cicerone.getNavigatorHolder()
    }

}