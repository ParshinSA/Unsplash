package com.example.unsplash.presentation.fragment_photos_main

import android.view.WindowManager
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.unsplash.R
import com.example.unsplash.presentation.abstract_fragment_photos.PhotosAbstractFragment

open class MainPhotosFragment :
    PhotosAbstractFragment() {

    override val viewModel by viewModels<MainPhotosViewModel> { viewModelsFactory }

    override val refreshListener by lazy {
	  SwipeRefreshLayout.OnRefreshListener {
		connectionStateProvider.isDeviceOnline() ?: return@OnRefreshListener
		val query = searchViewFromToolbar?.query?.toString() ?: ""
		if (query == "") viewModel.requestPhotosRemote()
		else viewModel.requestSearchPhotos(query)
	  }
    }

    private val searchViewFromToolbar by lazy {
	  val menuItem = binding.toolbar.menu.findItem(R.id.search_photo)
	  menuItem.actionView as? SearchView
    }

    override fun thisCustomActions() {
	  viewModel.requestPhotosRemote()
    }

    override fun thisCustomSettings() {
	  super.thisCustomSettings()
	  settingsToolbar()
    }

    private fun settingsToolbar() {
	  settingsItemsSearch()
    }

    private fun setMenuSearchViewListeners() {
	  onCloseListener()
	  onQueryTextListener()
    }

    private fun settingsItemsSearch() {
	  setMenuInToolbar()
	  setMenuHint()
	  setMenuSearchViewListeners()
    }

    private fun setMenuInToolbar() {
	  binding.toolbar.inflateMenu(R.menu.toolbar_menu_search_display_items)
    }

    private fun onCloseListener() {
	  searchViewFromToolbar?.setOnCloseListener {
		closeKeyBoard()
		true
	  }
    }

    private fun onQueryTextListener() {
	  searchViewFromToolbar?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
		override fun onQueryTextSubmit(query: String?): Boolean {
		    if (query == null) return false
		    viewModel.requestSearchPhotos(query)
		    searchViewFromToolbar?.clearFocus()
		    closeKeyBoard()
		    return true
		}

		override fun onQueryTextChange(newText: String?) = false
	  })
    }

    private fun closeKeyBoard() {
	  requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
    }

    private fun setMenuHint() {
	  val resource = getString(R.string.enter_query)
	  searchViewFromToolbar?.queryHint = resource
    }

    companion object {
	  fun newInstance() = MainPhotosFragment()
    }

}