package com.example.unsplash.presentation._common.some_components

import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

class GlideListener<T>(
    private val onLoadFailed: (() -> Unit)?,
    private val onLoadReady: ((T?) -> Unit)?,
) : RequestListener<T> {

    override fun onLoadFailed(
	  e: GlideException?,
	  model: Any?,
	  target: Target<T>?,
	  isFirstResource: Boolean
    ): Boolean {
	  onLoadFailed?.invoke()
	  return false
    }

    override fun onResourceReady(
	  resource: T?,
	  model: Any?,
	  target: Target<T>?,
	  dataSource: DataSource?,
	  isFirstResource: Boolean
    ): Boolean {
	  onLoadReady?.invoke(resource)
	  return false
    }
}
