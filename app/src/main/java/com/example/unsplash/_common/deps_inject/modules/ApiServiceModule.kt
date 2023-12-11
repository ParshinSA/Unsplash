package com.example.unsplash._common.deps_inject.modules

import com.example.unsplash.data.network.unsplash.api.CollectionsServiceRemote
import com.example.unsplash.data.network.unsplash.api.LikeServiceRemote
import com.example.unsplash.data.network.unsplash.api.PhotosServiceRemote
import com.example.unsplash.data.network.unsplash.api.ProfileServiceRemote
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.create

@Module
class ApiServiceModule {

    @Provides
    fun provideLikeServiceRemote(
	  retrofit: Retrofit
    ): LikeServiceRemote {
	  return retrofit
		.newBuilder()
		.baseUrl(API_UNSPLASH_URL)
		.build()
		.create()
    }

    @Provides
    fun providePhotosServiceRemote(
	  retrofit: Retrofit
    ): PhotosServiceRemote {
	  return retrofit
		.newBuilder()
		.baseUrl(API_UNSPLASH_URL)
		.build()
		.create()
    }

    @Provides
    fun provideProfileServiceRemote(
	  retrofit: Retrofit
    ): ProfileServiceRemote {
	  return retrofit
		.newBuilder()
		.baseUrl(API_UNSPLASH_URL)
		.build()
		.create()
    }

    @Provides
    fun provideCollectionsServiceRemote(
	  retrofit: Retrofit
    ): CollectionsServiceRemote {
	  return retrofit
		.newBuilder()
		.baseUrl(API_UNSPLASH_URL)
		.build()
		.create()
    }

    companion object {
	  private const val API_UNSPLASH_URL = "https://api.unsplash.com/"
    }
}