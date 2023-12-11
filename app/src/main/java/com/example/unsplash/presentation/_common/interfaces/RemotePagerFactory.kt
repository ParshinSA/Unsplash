package com.example.unsplash.presentation._common.interfaces

import androidx.paging.PagingData
import com.example.unsplash.presentation._common.some_components.Request
import com.example.unsplash.presentation.abstract_fragment_items_display.ItemDisplay
import kotlinx.coroutines.flow.Flow

interface RemotePagerFactory<_ItemDisplay : ItemDisplay> {
    fun create(request: Request): Flow<PagingData<_ItemDisplay>>
}