package com.example.unsplash._common.helpers.permissions

import android.content.Context
import android.content.pm.PackageManager.*
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.*
import androidx.fragment.app.FragmentActivity
import com.example.unsplash.R
import com.example.unsplash._common.helpers.permissions.WriteExternalStoragePermissionHelper.Companion.ACTIVITY_RESULT_LAUNCHER
import com.example.unsplash.presentation.ui.BaseSnackbarFactory
import com.google.android.material.snackbar.Snackbar
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

class WriteExternalStoragePermissionHelperImpl @AssistedInject constructor(
    @Assisted(ACTIVITY_RESULT_LAUNCHER)
    private val activityResultLauncher: ActivityResultLauncher<String>,
    private val baseSnackbarFactory: BaseSnackbarFactory,
    private val context: Context,
) : WriteExternalStoragePermissionHelper {

    private val writePermission = android.Manifest.permission.WRITE_EXTERNAL_STORAGE

    override fun requestPermission() {
	  activityResultLauncher.launch(writePermission)
    }

    override fun checkPermission(): Boolean {
	  return checkSelfPermission(context, writePermission) == PERMISSION_GRANTED
    }

    override fun shouldShowRationale(activity: FragmentActivity): Boolean {
	  return ActivityCompat.shouldShowRequestPermissionRationale(activity, writePermission)
    }

    override fun showRationale(view: View) {
	  val snackbar = baseSnackbarFactory.create(
		view = view,
		text = context.resources.getString(R.string.load_started),
		duration = Snackbar.LENGTH_INDEFINITE
	  ).apply { setAction(context.resources.getString(R.string.ok)) { requestPermission() } }
	  snackbar.show()
    }

}