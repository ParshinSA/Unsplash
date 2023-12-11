package com.example.unsplash.presentation._common.interfaces

import androidx.paging.PagingData
import com.example.unsplash.presentation.abstract_fragment_items_display.ItemDisplay
import kotlinx.coroutines.flow.Flow

interface LocalPagerFactory<_ItemDisplay : ItemDisplay> {
    fun create(): Flow<PagingData<_ItemDisplay>>
}