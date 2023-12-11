package com.example.unsplash._common.helpers.permissions

import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.fragment.app.FragmentActivity
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory

interface LocationPermissionHelper {

    fun requestPermission()
    fun checkPermission(): Boolean
    fun showRationale(view: View)
    fun shouldShowRationale(activity: FragmentActivity): Boolean

    @AssistedFactory
    interface Factory {

	  fun createLocationPermission(
		@Assisted(ACTIVITY_RESULT_LAUNCHER)
		activityResultLauncher: ActivityResultLauncher<Array<String>>
	  ): LocationPermissionHelperImpl
    }

    companion object {
	  const val ACTIVITY_RESULT_LAUNCHER = "ASSISTED_BASE_ACTIVITY"
    }
}
