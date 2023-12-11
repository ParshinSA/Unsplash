package com.example.unsplash.presentation.abstract_fragment_items_display

import android.content.Context
import androidx.core.view.doOnPreDraw
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.CombinedLoadStates
import androidx.paging.PagingData
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.unsplash.R
import com.example.unsplash._common.extensions.launchMain
import com.example.unsplash._common.extensions.multiCollectToFlow
import com.example.unsplash.databinding.IncludeLayoutItemsDisplayBinding
import com.example.unsplash.presentation.base.BaseFragment
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

abstract class ItemsDisplayAbstractFragment<
    _ItemDisplay : ItemDisplay,
    _ItemDisplayHolder : RecyclerView.ViewHolder
    > : BaseFragment<IncludeLayoutItemsDisplayBinding>(IncludeLayoutItemsDisplayBinding::inflate) {

    protected abstract val pagingAdapterItemsRv: PagingDataAdapter<_ItemDisplay, _ItemDisplayHolder>
    protected abstract val viewModel: ItemsDisplayAbstractViewModel<_ItemDisplay>
    protected abstract val layoutManagerItemsRv: LayoutManager
    protected abstract val titleToolbar: String?
    protected abstract val refreshListener: SwipeRefreshLayout.OnRefreshListener

    //******************** lifecycle ********************//
    override fun onAttach(context: Context) {
	  super.onAttach(context)
	  postponeEnterTransition()
    }
    //******************** lifecycle ********************//

    override fun thisCustomSettings() {
	  settingsRvListState()
	  settingsItemsRv()
	  settingsIncludeToolbarWithInfo()
	  settingsRefreshItemsList()
	  settingsItemsLoadStateListener()
	  observeData()
    }

    private fun settingsIncludeToolbarWithInfo() {
	  settingsTitleToolbar()
    }

    private fun settingsRvListState() {
	  pagingAdapterItemsRv.addOnPagesUpdatedListener {
		if (pagingAdapterItemsRv.itemCount == 0) {
		    val anErrorHas = viewModel.anErrorHasStateFlow.value ?: false
		    binding.rvItemList.isVisible = false

		    if (anErrorHas) {
			  val txtMessage = getString(R.string.occur_error_for_load_data)
			  binding.tvWithImageNoContent.text = txtMessage
			  binding.tvWithImageNoContent.isVisible = true
		    } else {
			  binding.tvWithImageNoContent.text = getString(R.string.here_is_empty)
			  binding.tvWithImageNoContent.isVisible = true
		    }

		} else {
		    binding.tvWithImageNoContent.isVisible = false
		    binding.rvItemList.isVisible = true
		}
	  }
    }

    private fun settingsTitleToolbar() {
	  titleToolbar?.let {
		binding.toolbar.isVisible = true
		binding.toolbar.title = titleToolbar
	  }
    }

    private fun settingsItemsRv() {
	  with(binding.rvItemList) {
		layoutManager = layoutManagerItemsRv
		adapter = pagingAdapterItemsRv
		setHasFixedSize(true)

		doOnPreDraw {
		    startPostponedEnterTransition()
		}
	  }
    }

    private fun setItems(pagingData: PagingData<_ItemDisplay>) {
	  pagingAdapterItemsRv.submitData(lifecycle, pagingData)
    }

    private fun observeData() {
	  lifecycleScope.launchMain(action = {
		lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
		    multiCollectToFlow(
			  viewModel.isLoadingItemsStateFlow.onEach { handleLoadingState(it) },
			  viewModel.itemsStateFlow.onEach { setItems(it) },
		    )
		}
	  }, onError = { it.printStackTrace() })
    }

    private fun handleLoadingState(loadingState: Boolean?) {
	  loadingState ?: return
	  changeStateProgressBars(loadingState)
    }

    private fun changeStateProgressBars(loadingState: Boolean) {
	  progressMainLayout(loadingState)
	  progressSwipeLayout(loadingState)
    }

    private fun progressSwipeLayout(loadingState: Boolean) {
	  if (!loadingState) binding.swipeLayout.isRefreshing = false
    }

    private fun progressMainLayout(loadingState: Boolean) {
	  binding.progressBar.isVisible = loadingState
    }

    private fun settingsItemsLoadStateListener() {
	  lifecycleScope.launchMain(action = {
		lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
		    pagingAdapterItemsRv.loadStateFlow.onEach { stateHandler(it) }.launchIn(this)
		}
	  }, onError = { it.printStackTrace() })
    }

    private fun stateHandler(state: CombinedLoadStates) {
	  val listState = listOf(state.prepend, state.append, state.refresh)
	  viewModel.handleLoadState(listState)
    }

    private fun settingsRefreshItemsList() {
	  binding.swipeLayout.setOnRefreshListener(refreshListener)
    }

}