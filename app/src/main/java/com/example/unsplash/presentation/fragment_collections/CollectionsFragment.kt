package com.example.unsplash.presentation.fragment_collections

import android.content.Context
import android.view.WindowManager
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.unsplash.R
import com.example.unsplash.presentation.AppApplication
import com.example.unsplash.presentation._common.interfaces.PhotosFromCollectionHandler
import com.example.unsplash.presentation.abstract_fragment_items_display.ItemsDisplayAbstractFragment
import com.example.unsplash.presentation.base.ViewModelsFactory
import com.example.unsplash.presentation.fragment_collections.adapters.CollectionsPagingDataAdapter
import com.example.unsplash.presentation.fragment_collections.adapters.CollectionsPagingDataAdapter.CollectionsViewHolder
import com.example.unsplash.presentation.fragment_collections.models.PhotosCollection
import javax.inject.Inject

class CollectionsFragment :
    ItemsDisplayAbstractFragment<PhotosCollection, CollectionsViewHolder>() {

    @Inject
    lateinit var viewModelsFactory: ViewModelsFactory

    override val titleToolbar by lazy { getString(R.string.collections) }

    override val pagingAdapterItemsRv by lazy { CollectionsPagingDataAdapter(::openPhotosFromCollection) }

    override val layoutManagerItemsRv by lazy { LinearLayoutManager(requireContext()) }

    override val viewModel by viewModels<CollectionsViewModel> { viewModelsFactory }

    override val refreshListener by lazy {
	  SwipeRefreshLayout.OnRefreshListener {
		viewModel.requestSomeCollections()
	  }
    }

    private val searchViewFromToolbar by lazy {
	  val menuItem = binding.toolbar.menu.findItem(R.id.search_photo)
	  menuItem.actionView as? SearchView
    }

    //******************** lifecycle ********************//
    override fun onAttach(context: Context) {
	  inject()
	  super.onAttach(context)
    }
    //******************** lifecycle ********************//

    override fun thisCustomActions() {
	  viewModel.requestSomeCollections()
    }

    override fun thisCustomSettings() {
	  super.thisCustomSettings()
	  settingsToolbar()
    }

    private fun openPhotosFromCollection(collection: PhotosCollection) {
	  (parentFragment as? PhotosFromCollectionHandler)
		?.openPhotosFromCollectionFrg(
		    collection.collectionId,
		    collection.commonInfo,
		    collection.title,
		)
    }

    private fun settingsToolbar() {
	  settingsItemsSearch()
    }

    private fun settingsItemsSearch() {
	  setMenuInToolbar()
	  setMenuHint()
	  setMenuSearchViewListeners()
    }

    private fun setMenuSearchViewListeners() {
	  onCloseListener()
	  onQueryTextListener()
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
		    viewModel.requestSearchCollections(query)
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

    private fun inject() {
	  (requireActivity().applicationContext as AppApplication).component.inject(this)
    }

    companion object {
	  fun newInstance() = CollectionsFragment()
    }

}