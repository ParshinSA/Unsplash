package com.example.unsplash.presentation.fragment_profile

import android.content.Context
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.children
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.example.unsplash.R
import com.example.unsplash._common.extensions.launchMain
import com.example.unsplash._common.extensions.multiCollectToFlow
import com.example.unsplash._common.helpers.location.OpenLocationHandler
import com.example.unsplash._common.helpers.location.UserLocationProvider
import com.example.unsplash._common.helpers.permissions.LocationPermissionHelper
import com.example.unsplash.databinding.FragmentProfileBinding
import com.example.unsplash.presentation.AppApplication
import com.example.unsplash.presentation._common.interfaces.LogoutHandler
import com.example.unsplash.presentation.base.BaseFragment
import com.example.unsplash.presentation.base.ViewModelsFactory
import com.example.unsplash.presentation.fragment_liked_photos.LikedPhotosFragment
import com.example.unsplash.presentation.fragment_photo_details.models.ProfileImage
import com.example.unsplash.presentation.fragment_profile.models.CurrentUser
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class ProfileFragment :
    BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {

    @Inject
    lateinit var viewModelsFactory: ViewModelsFactory
    private val viewModel by viewModels<ProfileViewModel> { viewModelsFactory }

    @Inject
    lateinit var openLocationHandler: OpenLocationHandler

    @Inject
    lateinit var userLocationProvider: UserLocationProvider

    @Inject
    lateinit var locationPermissionHelperFactory: LocationPermissionHelper.Factory

    private val activityResultLauncher = registerForActivityResult(
	  ActivityResultContracts.RequestMultiplePermissions()
    ) { mapPermissionIsGranted: Map<String, Boolean> ->
	  if (mapPermissionIsGranted.values.all { it }) onPermissionGranted()
    }

    private val handlerLocationPermission by lazy {
	  locationPermissionHelperFactory.createLocationPermission(activityResultLauncher)
    }

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    //******************** lifecycle ********************//
    override fun onAttach(context: Context) {
	  injectDependency()
	  super.onAttach(context)
    }

    override fun onDestroy() {
	  activityResultLauncher.unregister()
	  super.onDestroy()
    }
    //******************** lifecycle ********************//

    override fun thisCustomActions() {
	  viewModel.requestInfoAboutMe()
    }

    override fun thisCustomSettings() {
	  initLocationServices()
	  settingsOpenLocationCurrentUser()
	  settingsInitLogout()
	  observeToData()
    }

    private fun initLocationServices() {
	  fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
    }

    private fun settingsOpenLocationCurrentUser() {
	  binding.imBtnLocation.setOnClickListener {
		with(handlerLocationPermission) {
		    when {
			  checkPermission() -> onPermissionGranted()
			  shouldShowRationale(requireActivity()) -> showRationale(requireView())
			  else -> requestPermission()
		    }
		}
	  }
    }

    private fun onPermissionGranted() {
	  if (handlerLocationPermission.checkPermission()) {
		lifecycleScope.launchMain(
		    action = {
			  val location = userLocationProvider.getCurrentLocation() ?: return@launchMain
			  openLocationHandler.openLocationByLatLong(
				context = requireContext(),
				latitude = location.latitude,
				longitude = location.longitude
			  )
		    }, onError = { it.printStackTrace() }
		)
	  }
    }

    private fun settingsInitLogout() {
	  val logoutBtn = binding.toolbar.menu.children.firstOrNull { menuItem: MenuItem ->
		menuItem.itemId == R.id.logout
	  } ?: return

	  logoutBtn.setOnMenuItemClickListener {
		(activity as? LogoutHandler)?.initLogout()
		return@setOnMenuItemClickListener true
	  }
    }

    fun updateInfo() {
	  viewModel.requestInfoAboutMe()
    }

    private fun addLikedFragment(userName: String) {
	  val fragments = childFragmentManager.fragments
	  val containsLikedPhotosFrg = fragments.mapNotNull { fragment: Fragment? ->
		fragment as? LikedPhotosFragment
	  }.isNotEmpty()

	  if (containsLikedPhotosFrg) return

	  childFragmentManager.beginTransaction()
		.add(binding.fLayLikedImagesContainer.id, LikedPhotosFragment.newInstance(userName))
		.commit()
    }

    private fun observeToData() {
	  lifecycleScope.launchMain(
		action = {
		    lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
			  multiCollectToFlow(
				viewModel.currentUserStateFlow.onEach(::handlesCurrentUsersData)
			  )
		    }
		}, onError = { it.printStackTrace() })
    }

    private fun handlesCurrentUsersData(currentUser: CurrentUser?) {
	  currentUser ?: return
	  setProfileImage(currentUser.profileImage)
	  setName(currentUser.name)
	  val info = "Email: ${currentUser.email}\n" +
		"Total likes: ${currentUser.totalLikes}"
	  setSomeInfo(info)
	  addLikedFragment(currentUser.userName)
	  binding.imBtnLocation.isVisible = true
    }

    private fun setSomeInfo(info: String) {
	  binding.textViewSomeInfo.text = info
    }

    private fun setName(name: String) {
	  binding.textViewUsersName.text = name
    }

    private fun setProfileImage(profileImage: ProfileImage) {
	  lifecycleScope.launchMain(
		action = {
		    Glide.with(requireContext())
			  .load(profileImage.medium)
			  .into(binding.shapeImageViewProfileImage)
		}, onError = { it.printStackTrace() }
	  )
    }

    private fun injectDependency() {
	  (requireActivity().applicationContext as AppApplication).component.inject(this)
    }

    companion object {
	  fun newInstance() = ProfileFragment()
    }
}