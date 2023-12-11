package com.example.unsplash.presentation.ui

import android.view.View
import com.example.unsplash.R
import com.example.unsplash.presentation._common.some_components.theme_color_provider.ThemeColorProvider
import com.google.android.material.snackbar.Snackbar

class BaseSnackbarFactory(
    themeColorProvider: ThemeColorProvider,
) {

    private val colorPrimary = themeColorProvider.getColorPrimary()

    fun create(view: View, text: CharSequence, duration: Int): Snackbar {
	  return createInternal(view, text, duration)
    }

    private fun createInternal(view: View, text: CharSequence, duration: Int): Snackbar {
	  return Snackbar.make(view, text, duration).apply {
		getView().apply {
		    setBackgroundResource(R.drawable.bg_base_snackbar)
		    setActionTextColor(colorPrimary)
		    setTextColor(colorPrimary)
		}
	  }
    }

}
