package com.example.unsplash.data.paging_source

import com.example.unsplash.presentation.abstract_fragment_items_display.ItemDisplay

class ItemsDisplayPagingSourceLocal<_ItemDisplay : ItemDisplay>(
    private val request: suspend () -> List<_ItemDisplay>,
) : MainItemsDisplayPagingSource<_ItemDisplay>() {

    override suspend fun loadResult(pageNumber: Int, pageSize: Int): LoadResult<Int, _ItemDisplay> {
	  return try {
		val items = request.invoke()
		LoadResult.Page(items, null, null)
	  } catch (t: Throwable) {
		LoadResult.Error(t)
	  }
    }

}