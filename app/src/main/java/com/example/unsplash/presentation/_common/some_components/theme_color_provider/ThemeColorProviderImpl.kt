package com.example.unsplash.presentation._common.some_components.theme_color_provider

import android.content.Context
import android.content.res.Configuration
import androidx.core.content.ContextCompat
import com.example.unsplash.R

class ThemeColorProviderImpl(
    private val context: Context
) : ThemeColorProvider {

    private val configuration
	  get() = context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK

    override fun getColorPrimary(): Int {
	  val colorID = when (configuration) {
		Configuration.UI_MODE_NIGHT_YES -> R.color.darkDimGray
		Configuration.UI_MODE_NIGHT_NO -> R.color.lawnGreen
		else -> R.color.lawnGreen
	  }
	  return ContextCompat.getColor(context, colorID)
    }

    override fun getColorOnPrimary(): Int {
	  val colorID = when (configuration) {
		Configuration.UI_MODE_NIGHT_YES -> R.color.lawnGreen
		Configuration.UI_MODE_NIGHT_NO -> R.color.darkDimGray
		else -> R.color.darkDimGray
	  }
	  return ContextCompat.getColor(context, colorID)
    }

}