package com.example.unsplash.presentation.abstract_fragment_items_display

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.unsplash._common.extensions.launchMain
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

abstract class ItemsDisplayAbstractViewModel<_ItemDisplay : ItemDisplay> :
    ViewModel() {

    protected val itemsMutStateFlow = MutableStateFlow(PagingData.empty<_ItemDisplay>())
    val itemsStateFlow get() = itemsMutStateFlow.asStateFlow()

    private val isLoadingItemsMutStateFlow = MutableStateFlow<Boolean?>(null)
    val isLoadingItemsStateFlow get() = isLoadingItemsMutStateFlow.asStateFlow()

    private val anErrorHasMutStateFlow = MutableStateFlow<Boolean?>(null)
    val anErrorHasStateFlow get() = anErrorHasMutStateFlow.asStateFlow()

    private var itemsPagerJob: Job? = null

    protected fun collectForItemsPager(pager: Flow<PagingData<_ItemDisplay>>) {
	  itemsPagerJob?.cancel()
	  itemsPagerJob = viewModelScope.launchMain(
		action = {
		    pager.cachedIn(viewModelScope)
			  .collect() { pagingData: PagingData<_ItemDisplay> ->
				itemsMutStateFlow.value = pagingData
			  }
		}, onError = { it.printStackTrace() })
    }

    fun handleLoadState(listState: List<LoadState>) {
	  checkLoadingState(listState)
	  checkErrorState(listState)
    }

    private fun checkLoadingState(listState: List<LoadState>) {
	  val isLoading = listState.filterIsInstance<LoadState.Loading>().isNotEmpty()
	  isLoadingItemsMutStateFlow.value = isLoading
    }

    private fun checkErrorState(listState: List<LoadState>) {
	  val isError = listState.filterIsInstance<LoadState.Error>().isNotEmpty()
	  anErrorHasMutStateFlow.value = isError
    }

}