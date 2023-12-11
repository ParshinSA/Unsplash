package com.example.unsplash.presentation

import android.app.Application
import android.os.StrictMode
import com.example.unsplash.BuildConfig
import com.example.unsplash._common.deps_inject.AppComponent
import com.example.unsplash._common.deps_inject.DaggerAppComponent

class AppApplication : Application() {

    val component: AppComponent by lazy {
	  DaggerAppComponent.builder()
		.addContext(context = this)
		.build()
    }

    override fun onCreate() {
	  super.onCreate()
	  setStrictMode()
    }

    private fun setStrictMode() {
	  if (BuildConfig.DEBUG) {
		StrictMode.setThreadPolicy(
		    StrictMode.ThreadPolicy.Builder()
			  .detectDiskWrites()
			  .detectNetwork()
			  .penaltyLog()
			  .build()
		)
	  }
    }
}