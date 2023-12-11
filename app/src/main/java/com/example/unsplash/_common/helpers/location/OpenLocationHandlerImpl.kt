package com.example.unsplash._common.helpers.location

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.example.unsplash.R
import com.example.unsplash.presentation._common.some_components.local_app_messenger.AppMessenger

class OpenLocationHandlerImpl(
    private val appMessenger: AppMessenger,
) : OpenLocationHandler {

    override fun openLocationByLatLong(context: Context, latitude: Double, longitude: Double) {
	  val geoUri = Uri.parse("geo:$latitude,$longitude?q=$latitude,$longitude")

	  val intent = Intent(Intent.ACTION_VIEW).apply {
		setPackage(PACKAGE_GOOGLE_MAPS)
		data = geoUri
	  }

	  try {
		// Trying to find the Google Map app
		if (intent.resolveActivity(context.packageManager) != null) {
		    context.startActivity(intent)
		} else {
		    // If the application is not found or for some reason does not open, then open the maps in the browser
		    context.startActivity(Intent(Intent.ACTION_VIEW).apply {
			  data = Uri.parse("$PATH_GOOGLE_MAPS$latitude+$longitude")
		    })
		}
	  } catch (t: Throwable) {
		t.printStackTrace()
		appMessenger.sendMessage(R.string.open_location_went_wrong)
	  }
    }

    companion object {
	  private const val PATH_GOOGLE_MAPS = "https://maps.google.com/maps?z=12&t=m&q=loc:"
	  private const val PACKAGE_GOOGLE_MAPS = "com.google.android.apps.maps"
    }
}