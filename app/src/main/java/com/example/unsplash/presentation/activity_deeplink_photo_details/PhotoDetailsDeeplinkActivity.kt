package com.example.unsplash.presentation.activity_deeplink_photo_details

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import com.example.unsplash.R
import com.example.unsplash.presentation._common.interfaces.PhotoDetailsHandler
import com.example.unsplash.presentation._common.some_components.local_app_messenger.AppMessenger
import com.example.unsplash.presentation.activity_main.MainActivity
import com.example.unsplash.presentation.base.BaseActivity
import com.example.unsplash.presentation.fragment_photo_details.PhotoDetailsFragment
import javax.inject.Inject

class PhotoDetailsDeeplinkActivity :
    BaseActivity(),
    PhotoDetailsHandler {

    @Inject
    lateinit var appMessenger: AppMessenger

    //******************** lifecycle ********************//
    override fun onCreate(savedInstanceState: Bundle?) {
	  super.onCreate(savedInstanceState)
	  handleIntentData()
	  overrideBaseBackPressCallback()
    }
    //******************** lifecycle ********************//

    private fun handleIntentData() {
	  val photoID = intent?.data?.lastPathSegment
	  photoID?.let {
		openPhotoDetailsFrg(photoID, null)
	  } ?: appMessenger.sendMessage(R.string.failed_to_open_photo)
    }

    override fun openPhotoDetailsFrg(photoId: String, sharedView: View?) {
	  supportFragmentManager.beginTransaction()
		.add(
		    R.id.appScreensContainerIntoBaseActivity,
		    PhotoDetailsFragment.newInstance(photoId)
		)
		.commit()
    }

    private fun overrideBaseBackPressCallback() {
	  onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
		override fun handleOnBackPressed() {
		    val intent = Intent(this@PhotoDetailsDeeplinkActivity, MainActivity::class.java)
		    startActivity(intent)
		    this@PhotoDetailsDeeplinkActivity.finish()
		}
	  })
    }
}