package com.example.unsplash.presentation.screens.collections

import com.example.unsplash.presentation._common.interfaces.PhotosFromCollectionHandler
import com.example.unsplash.presentation.screens.photos.PhotosNavigableScreenFragment

class CollectionsNavigableScreenFragment :
    PhotosNavigableScreenFragment(),
    PhotosFromCollectionHandler {

    override val routerTag = ROUTER_TAG

    override fun thisCustomActions() {
	  navigateTo(screens.CollectionsFragment(), true)
    }

    override fun thisCustomSettings() {
	  // yet not settings
    }

    override fun openPhotosFromCollectionFrg(
	  collectionsId: String, commonInfo: String, title: String
    ) {
	  connectionStateProvider.isDeviceOnline() ?: return
	  navigateTo(screens.PhotosFromCollectionsFragment(collectionsId, commonInfo, title), false)
    }

    companion object {
	  private const val ROUTER_TAG = "CollectionsNavigableScreenFragment"

	  fun newInstance() = CollectionsNavigableScreenFragment()
    }

}