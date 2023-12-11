package com.example.unsplash.data.paging_source

import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.unsplash.data.paging_source.MainItemsDisplayPagingSource.PagingConfig.PAGE_SIZE
import com.example.unsplash.presentation.abstract_fragment_items_display.ItemDisplay

abstract class MainItemsDisplayPagingSource<_ItemDisplay : ItemDisplay> :
    PagingSource<Int, _ItemDisplay>() {

    override fun getRefreshKey(state: PagingState<Int, _ItemDisplay>): Int? {
	  val anchorPosition = state.anchorPosition ?: return null
	  val page = state.closestPageToPosition(anchorPosition) ?: return null
	  return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, _ItemDisplay> {
	  val pageNumber = params.key ?: 1
	  return loadResult(pageNumber, PAGE_SIZE)
    }

    override val keyReuseSupported: Boolean = true

    abstract suspend fun loadResult(pageNumber: Int, pageSize: Int): LoadResult<Int, _ItemDisplay>

    object PagingConfig {
	  const val PAGE_SIZE = 10
    }

    companion object {
	  fun pagerConfig() =
		PagingConfig(
		    pageSize = PAGE_SIZE,
		    initialLoadSize = 10
		)
    }
}