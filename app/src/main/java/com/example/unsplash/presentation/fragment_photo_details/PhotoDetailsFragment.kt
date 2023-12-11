package com.example.unsplash.presentation.fragment_photo_details

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.children
import androidx.core.view.isVisible
import androidx.fragment.app.clearFragmentResult
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.example.unsplash.R
import com.example.unsplash._common.extensions.launchMain
import com.example.unsplash._common.extensions.multiCollectToFlow
import com.example.unsplash._common.extensions.withArguments
import com.example.unsplash._common.helpers.location.OpenLocationHandler
import com.example.unsplash._common.helpers.permissions.WriteExternalStoragePermissionHelper
import com.example.unsplash.databinding.FragmentPhotoDetailsBinding
import com.example.unsplash.presentation.AppApplication
import com.example.unsplash.presentation._common.interfaces.PhotoDetailsHandler
import com.example.unsplash.presentation._common.models.LikeInfo
import com.example.unsplash.presentation._common.some_components.BlurHashDecoder
import com.example.unsplash.presentation._common.some_components.GlideListener
import com.example.unsplash.presentation._common.some_components.local_app_messenger.AppMessenger
import com.example.unsplash.presentation.base.BaseFragment
import com.example.unsplash.presentation.base.ViewModelsFactory
import com.example.unsplash.presentation.fragment_navigate_by_screens.NavigateByScreensFragment
import com.example.unsplash.presentation.fragment_photo_details.models.PhotographerInfo
import com.google.android.material.transition.MaterialContainerTransform
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

open class PhotoDetailsFragment :
    BaseFragment<FragmentPhotoDetailsBinding>(FragmentPhotoDetailsBinding::inflate) {

    @Inject
    lateinit var viewModelsFactory: ViewModelsFactory
    private val viewModel by viewModels<PhotoDetailsViewModel> { viewModelsFactory }

    private val parentFrg by lazy { parentFragment as? PhotoDetailsHandler }

    @Inject
    lateinit var appMessenger: AppMessenger

    @Inject
    lateinit var openLocationHandler: OpenLocationHandler

    @Inject
    lateinit var writeExternalStoragePermissionHelperFactory: WriteExternalStoragePermissionHelper.Factory

    private val handlerDownloadPermission by lazy {
	  writeExternalStoragePermissionHelperFactory
		.createHandlerPermission(loadPermissionsResultLauncher)
    }

    private val loadPermissionsResultLauncher = registerForActivityResult(
	  ActivityResultContracts.RequestPermission()
    ) { onDownloadPermissionGranted() }

    //******************** lifecycle ********************//
    override fun onAttach(context: Context) {
	  injectDependency()
	  super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
	  super.onViewCreated(view, savedInstanceState)
	  parentFrg?.let { postponeEnterTransition() }
    }

    override fun onDestroy() {
	  loadPermissionsResultLauncher.unregister()
	  endProcessOpening()
	  super.onDestroy()
    }
    //******************** lifecycle ********************//

    override fun thisCustomSettings() {
	  settingsExitThisFrg()
	  settingsEnterThisFrg()
	  settingsDownloadPhoto()
	  settingsOpenLocation()
	  settingsChangeLike()
	  settingsShowInfo()
	  settingsSharePhoto()
	  observeData() // over all setting
    }

    override fun thisCustomActions() {
	  retrieveArguments()
	  requestPhotoDetailsById()
    }

    private fun settingsDownloadPhoto() {
	  binding.imBtnDownload.setOnClickListener {
		with(handlerDownloadPermission) {
		    when {
			  checkPermission() -> onDownloadPermissionGranted()
			  shouldShowRationale(requireActivity()) -> showRationale(requireView())
			  else -> requestPermission()
		    }
		}
	  }
    }

    private fun onDownloadPermissionGranted() {
	  setStateBtnDownloadPhoto(true)
	  viewModel.downloadPhoto()
    }

    private fun endProcessOpening() {
	  setFragmentResult(viewModel.photoId, Bundle())
	  clearFragmentResult(viewModel.photoId)
    }

    private fun settingsSharePhoto() {
	  val sharedBtn = binding.toolbar.menu.children
		.firstOrNull { menuItem: MenuItem -> menuItem.itemId == R.id.sharedPhoto } ?: return

	  sharedBtn.setOnMenuItemClickListener {
		sharePhoto()
		return@setOnMenuItemClickListener true
	  }
    }

    private fun sharePhoto() {
	  val shareIntent = Intent().apply {
		action = Intent.ACTION_SEND
		putExtra(Intent.EXTRA_TEXT, "https://unsplash.com/photos/${viewModel.info.id}")
		type = "text/plain"
	  }

	  if (shareIntent.resolveActivity(requireContext().packageManager) != null) {
		startActivity(Intent.createChooser(shareIntent, "Share with"))
	  } else appMessenger.sendMessage(R.string.send_url_went_wrong)
    }

    private fun settingsOpenLocation() {
	  binding.imBtnLocation.setOnClickListener {
		val latitude = viewModel.info.location.latitude ?: return@setOnClickListener
		val longitude = viewModel.info.location.longitude ?: return@setOnClickListener

		openLocationHandler.openLocationByLatLong(requireContext(), latitude, longitude)
	  }
    }

    private fun settingsShowInfo() {
	  binding.imBtnChangeStateInfo.setOnClickListener {
		val currentRotationArrow = it.rotation
		it.rotation = currentRotationArrow + 180

		val isVisibleInfo = binding.tvCommonInfo.isVisible
		binding.tvCommonInfo.isVisible = !isVisibleInfo
		binding.tvPhotographerInfo.isVisible = !isVisibleInfo
	  }
    }

    private fun settingsExitThisFrg() {
	  binding.toolbar.setNavigationOnClickListener {
		requireActivity().onBackPressedDispatcher.onBackPressed()
	  }
    }

    private fun settingsChangeLike() {
	  binding.imBtnImageLike.setOnClickListener { viewModel.changeLike() }
    }

    private fun observeData() {
	  lifecycleScope.launchMain(action = {
		lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
		    multiCollectToFlow(
			  viewModel.currentPhotoIsLoadingStateFlow.onEach { setStateBtnDownloadPhoto(it) },
			  viewModel.photographerInfoStateFlow.onEach { setInfoAboutPhotographer(it) },
			  viewModel.isErrorOpenedPhotoDetailsStateFlow.onEach { handlesIsError(it) },
			  viewModel.containsLocationStateFlow.onEach { changeStateLocationBtn(it) },
			  viewModel.downloadsInfoStateFlow.onEach { setDownloadInfo(it) },
			  viewModel.likeInfoStateFlow.onEach { changeStateLike(it) },
			  viewModel.commonInfoStateFlow.onEach { setCommonInfo(it) },
			  viewModel.photoLinkStateFlow.onEach { setImagePhoto(it) },
		    )
		}
	  }, onError = { it.printStackTrace() })
    }

    private fun setStateBtnDownloadPhoto(isLoaded: Boolean?) {
	  val btnDownload = binding.imBtnDownload
	  val progressLoading = binding.progressDownload
	  if (isLoaded == true) {
		btnDownload.isEnabled = false
		btnDownload.visibility = View.INVISIBLE
		progressLoading.isVisible = true
	  } else {
		btnDownload.isEnabled = true
		btnDownload.visibility = View.VISIBLE
		progressLoading.isVisible = false
	  }
    }

    private fun handlesIsError(isError: Boolean) {
	  if (isError) {
		endProcessOpening()
		requireActivity().onBackPressedDispatcher.onBackPressed()
	  }
    }

    private fun setDownloadInfo(counter: String?) {
	  counter ?: return
	  binding.tvDownloadCounter.text = counter
    }

    private fun setCommonInfo(info: String?) {
	  info?.let { binding.tvCommonInfo.text = info }
    }

    private fun changeStateLike(likeInfo: LikeInfo?) {
	  likeInfo ?: return
	  changeImageLike(likeInfo.likedByUser)
	  setNumberLikes(likeInfo.likes)
    }

    private fun changeStateLocationBtn(state: Boolean?) {
	  state ?: return
	  binding.imBtnLocation.isVisible = state
    }

    private fun changeImageLike(likeByUser: Boolean) {
	  setImageLike(likeByUser)
    }

    private fun setInfoAboutPhotographer(info: PhotographerInfo?) {
	  info ?: return
	  setPhotographerPhotoProfile(info.urlProfilePhotoMedium)
	  setPhotographerName(info.name)
    }

    private fun setPhotographerName(name: String) {
	  binding.tvPhotographerName.text = name
    }

    private fun setNumberLikes(numberLikes: String) {
	  binding.tvLikesCounter.text = numberLikes
    }

    private fun setImageLike(likeByUser: Boolean) {
	  if (likeByUser) setImage(R.drawable.ic_like_true)
	  else setImage(R.drawable.ic_like_false)
    }

    private fun setImage(@DrawableRes id: Int) {
	  binding.imBtnImageLike.setImageDrawable(
		AppCompatResources.getDrawable(requireContext(), id)
	  )
    }

    private fun setPhotographerPhotoProfile(url: String) {
	  Glide.with(requireContext())
		.load(url)
		.error(R.drawable.ic_profile)
		.into(binding.ivImageProfile)
    }

    private fun setImagePhoto(url: String?) {
	  if (url == null) return
	  else {
		loadBlurPhoto()
		loadOriginalPhoto(url)
	  }
    }

    private fun loadBlurPhoto() {
	  val blurHash = viewModel.info.blurHash
	  val bitmapFromBlurHash = BlurHashDecoder.decode(blurHash) ?: return
	  showBlurImageView()

	  Glide.with(requireContext())
		.load(bitmapFromBlurHash)
		.addListener(GlideListener(null) {
		    parentFrg?.let {
			  startPostponedEnterTransition()
			  endProcessOpening()
		    }
		})
		.into(binding.ivImagePhotoBlur)
    }

    private fun loadOriginalPhoto(url: String) {
	  Glide.with(requireContext())
		.load(url)
		.addListener(
		    GlideListener(
			  onLoadReady = {
				hideBlurImageView()
			  },
			  onLoadFailed = null
		    )
		).into(binding.ivImagePhotoOriginal)
    }

    private fun hideBlurImageView() {
	  lifecycleScope.launchMain(
		action = {
		    for (newAlpha in 10 downTo 0) {
			  binding.ivImagePhotoBlur.alpha = newAlpha / 10f
			  delay(16)
		    }
		},
		onError = { it.printStackTrace() }
	  )
    }

    private fun showBlurImageView() {
	  binding.ivImagePhotoBlur.alpha = 1f
    }

    private fun requestPhotoDetailsById() {
	  viewModel.requestPhotoDetailsById()
    }

    private fun retrieveArguments() {
	  retrievePhotoId()
    }

    private fun retrievePhotoId() {
	  val photoId = requireArguments().getString(KEY_PHOTO_ID) ?: return
	  viewModel.savePhotoId(photoId)
    }

    private fun settingsEnterThisFrg() {
	  settingAnimateTransition()
    }

    private fun settingAnimateTransition() {
	  sharedElementEnterTransition = MaterialContainerTransform().apply {
		drawingViewId = R.id.frameLayNavigateContainerScreens
		duration = NavigateByScreensFragment.TRANSITION_DURATION
		scrimColor = Color.TRANSPARENT
	  }
    }

    private fun injectDependency() {
	  (requireActivity().applicationContext as AppApplication).component.inject(this)
    }

    companion object {
	  private const val KEY_PHOTO_ID = "KEY_PHOTO_ID"

	  fun newInstance(
		photoId: String,
	  ): PhotoDetailsFragment {
		return PhotoDetailsFragment().withArguments {
		    putString(KEY_PHOTO_ID, photoId)
		}
	  }
    }

}