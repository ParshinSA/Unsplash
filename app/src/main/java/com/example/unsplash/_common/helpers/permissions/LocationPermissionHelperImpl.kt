package com.example.unsplash._common.helpers.permissions

import android.content.Context
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.fragment.app.FragmentActivity
import com.example.unsplash.R
import com.example.unsplash._common.helpers.permissions.LocationPermissionHelper.Companion.ACTIVITY_RESULT_LAUNCHER
import com.example.unsplash.presentation.ui.BaseSnackbarFactory
import com.google.android.material.snackbar.Snackbar
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

class LocationPermissionHelperImpl @AssistedInject constructor(
    @Assisted(ACTIVITY_RESULT_LAUNCHER)
    private val activityResultLauncher: ActivityResultLauncher<Array<String>>,
    private val baseSnackbarFactory: BaseSnackbarFactory,
    private val context: Context,
) : LocationPermissionHelper {

    private val coarseLocPerm = android.Manifest.permission.ACCESS_COARSE_LOCATION
    private val fineLocPerm = android.Manifest.permission.ACCESS_FINE_LOCATION

    override fun requestPermission() {
	  activityResultLauncher.launch(arrayOf(coarseLocPerm, fineLocPerm))
    }

    override fun checkPermission(): Boolean {
	  return checkSelfPermission(context, coarseLocPerm) == PERMISSION_GRANTED &&
		checkSelfPermission(context, fineLocPerm) == PERMISSION_GRANTED
    }

    override fun showRationale(view: View) {
	  val snackbar = baseSnackbarFactory.create(
		view = view,
		text = context.resources.getString(R.string.need_access_to_device_location),
		duration = Snackbar.LENGTH_INDEFINITE
	  ).apply { setAction(context.resources.getString(R.string.ok)) { requestPermission() } }
	  snackbar.show()
    }

    override fun shouldShowRationale(activity: FragmentActivity): Boolean {
	  return ActivityCompat.shouldShowRequestPermissionRationale(activity, coarseLocPerm)
    }

}