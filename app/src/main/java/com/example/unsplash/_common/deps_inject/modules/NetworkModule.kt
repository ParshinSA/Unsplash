package com.example.unsplash._common.deps_inject.modules

import com.example.unsplash._common.cicirone.Screens
import com.example.unsplash._common.cicirone.routers.GlobalRouter
import com.example.unsplash._common.deps_inject.providers.HttpLoggingInterceptorProvider
import com.example.unsplash._common.deps_inject.scopes.AppScope
import com.example.unsplash.data.network.unsplash.UnsplashInterceptor
import com.example.unsplash.data.storages.TokenStorage
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
class NetworkModule {

    @Provides
    @AppScope
    fun providesUnsplashInterceptor(
	  tokenStorage: TokenStorage,
	  globalRouter: GlobalRouter,
	  screens: Screens,
    ): UnsplashInterceptor {
	  return UnsplashInterceptor(
		tokenStorage = tokenStorage,
		globalRouter = globalRouter,
		screens = screens
	  )
    }

    @Provides
    fun providesHttpLoggingInterceptor(
    ): HttpLoggingInterceptorProvider {
	  return HttpLoggingInterceptorProvider(
		HttpLoggingInterceptor()
		    .setLevel(HttpLoggingInterceptor.Level.BODY)
	  )
    }

    @Provides
    @AppScope
    fun provideOkHttpClient(
	  unsplashInterceptor: UnsplashInterceptor,
	  httpLoggingInterceptorProvider: HttpLoggingInterceptorProvider,
    ): OkHttpClient {
	  return OkHttpClient.Builder()
		.connectTimeout(5000, TimeUnit.MILLISECONDS)
		.addNetworkInterceptor(httpLoggingInterceptorProvider.interceptor)
		.addInterceptor(unsplashInterceptor)
		.build()
    }

    @Provides
    @AppScope
    fun providesRetrofit(
	  okhttpClient: OkHttpClient
    ): Retrofit {
	  return Retrofit.Builder()
		.baseUrl(BASE_URL)
		.addConverterFactory(GsonConverterFactory.create())
		.client(okhttpClient)
		.build()
    }

    companion object {
	  private const val DOWNLOAD_FILE_API = "https://google.com"
	  private const val BASE_URL = DOWNLOAD_FILE_API
    }
}