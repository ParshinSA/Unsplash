package com.example.unsplash.data.paging_source

import com.example.unsplash._common.helpers.connection_state.ConnectionStateProvider
import com.example.unsplash.presentation.abstract_fragment_items_display.ItemDisplay
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.io.IOException

class ItemsDisplayPagingSourceRemote<_ItemDisplay : ItemDisplay>(
    private val connectionStateProvider: ConnectionStateProvider,
    private val request: suspend (pageNumber: Int, pageSize: Int) -> List<_ItemDisplay>
) : MainItemsDisplayPagingSource<_ItemDisplay>() {

    private var numberRepeat = 0

    override suspend fun loadResult(pageNumber: Int, pageSize: Int): LoadResult<Int, _ItemDisplay> {
	  return try {
		withContext(Dispatchers.IO) {
		    connectionStateProvider.isDeviceOnline() ?: pendingDeviceConnection(pageNumber)

		    val items = request.invoke(pageNumber, pageSize)

		    val prevKey = if (pageNumber == 1) null else pageNumber - 1
		    val nextKey = if (items.size < pageSize) null else pageNumber + 1
		    LoadResult.Page(items, prevKey, nextKey)
		}
	  } catch (t: Throwable) {
		LoadResult.Error(t)
	  }
    }

    private suspend fun pendingDeviceConnection(pageNumber: Int): LoadResult<Int, _ItemDisplay> {
	  val prevKey = if (pageNumber <= 2) null else pageNumber - 2
	  val nextKey = pageNumber
	  return if (numberRepeat <= 5) {
		delay(1000L * numberRepeat)
		numberRepeat++
		LoadResult.Page(emptyList(), prevKey, nextKey)
	  } else LoadResult.Error(IOException("Not internet"))
    }

}