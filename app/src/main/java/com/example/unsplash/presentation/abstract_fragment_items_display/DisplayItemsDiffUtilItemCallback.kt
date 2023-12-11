package com.example.unsplash.presentation.abstract_fragment_items_display

import androidx.recyclerview.widget.DiffUtil

abstract class DisplayItemsDiffUtilItemCallback<_DisplayItem : ItemDisplay> :
    DiffUtil.ItemCallback<_DisplayItem>() {

    override fun areItemsTheSame(oldItem: _DisplayItem, newItem: _DisplayItem) =
	  oldItem.displayItemId == newItem.displayItemId

    override fun getChangePayload(oldItem: _DisplayItem, newItem: _DisplayItem): Any? {
	  if (oldItem != newItem) return newItem
	  return super.getChangePayload(oldItem, newItem)
    }

    private fun <T : Any> checkEqualsItems(oldItem: T, newItem: T) = oldItem === newItem
}
