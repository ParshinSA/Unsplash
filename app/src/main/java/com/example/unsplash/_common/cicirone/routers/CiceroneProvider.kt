package com.example.unsplash._common.cicirone.routers

import com.example.unsplash._common.deps_inject.scopes.AppScope
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Router

@AppScope
class CiceroneProvider() {

    private val containers = HashMap<String, Cicerone<Router>>()

    fun getCicerone(containerTag: String): Cicerone<Router> =
	  containers.getOrPut(containerTag) {
		Cicerone.create()
	  }

}