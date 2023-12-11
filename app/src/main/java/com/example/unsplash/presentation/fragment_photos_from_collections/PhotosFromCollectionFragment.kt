package com.example.unsplash.presentation.fragment_photos_from_collections

import android.util.DisplayMetrics.DENSITY_DEFAULT
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.view.setPadding
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.unsplash.R
import com.example.unsplash._common.extensions.launchMain
import com.example.unsplash._common.extensions.multiCollectToFlow
import com.example.unsplash._common.extensions.withArguments
import com.example.unsplash.presentation.abstract_fragment_photos.PhotosAbstractFragment
import kotlinx.coroutines.flow.onEach

class PhotosFromCollectionFragment : PhotosAbstractFragment() {

    override val titleToolbar by lazy { getString(R.string.photos) }

    override val viewModel by viewModels<PhotosFromCollectionViewModel> { viewModelsFactory }

    override val refreshListener by lazy {
	  SwipeRefreshLayout.OnRefreshListener {
		binding.swipeLayout.isRefreshing = false
	  }
    }

    private var textViewForInfo: TextView? = null

    override fun thisCustomActions() {
	  retrieveArguments()
	  requestPhotos()
    }

    override fun thisCustomSettings() {
	  super.thisCustomSettings()
	  settingsNavBtnInToolbar()
	  createTextViewForInfoAndSetData()
	  observeData()
    }

    private fun createTextViewForInfoAndSetData() {
	  textViewForInfo ?: let {
		textViewForInfo = TextView(requireContext()).apply { id = TEXT_VIEW_INFO_ID }
		settingsLayoutParamsAndSpecifyPosition(textViewForInfo!!)
		settingsStartPadding(textViewForInfo!!)
		settingsBackground(textViewForInfo!!)
		addTextViewInfo(textViewForInfo!!)
	  }
    }

    private fun addTextViewInfo(textViewForInfo: TextView) {
	  (requireView() as? ViewGroup)?.addView(textViewForInfo)
    }

    private fun observeData() {
	  lifecycleScope.launchMain(action = {
		lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
		    multiCollectToFlow(
			  viewModel.collectionTitleStateFlow.onEach(::setCollectionTitle),
			  viewModel.collectionCommonInfoSateFlow.onEach(::setInfo)
		    )
		}
	  }, onError = { it.printStackTrace() })
    }

    private fun setInfo(info: String?) {
	  info ?: return
	  textViewForInfo?.text = info
    }

    private fun setCollectionTitle(title: String?) {
	  title ?: return
	  binding.toolbar.subtitle = getString(R.string.from_collection, title)
    }

    private fun settingsNavBtnInToolbar() {
	  binding.toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
	  binding.toolbar.setNavigationOnClickListener {
		requireActivity().onBackPressedDispatcher.onBackPressed()
	  }
    }

    private fun requestPhotos() {
	  viewModel.requestPhotosFromCollection()
    }


    private fun settingsLayoutParamsAndSpecifyPosition(tv: TextView) {
	  tv.layoutParams = RelativeLayout.LayoutParams(
		RelativeLayout.LayoutParams.MATCH_PARENT,
		RelativeLayout.LayoutParams.WRAP_CONTENT
	  ).apply {
		addRule(RelativeLayout.BELOW, binding.toolbar.id)
	  }

	  binding.swipeLayout.layoutParams = RelativeLayout.LayoutParams(
		RelativeLayout.LayoutParams.MATCH_PARENT,
		RelativeLayout.LayoutParams.MATCH_PARENT
	  ).apply {
		addRule(RelativeLayout.BELOW, tv.id)
	  }
    }

    private fun settingsBackground(tvInfo: TextView) {
	  tvInfo.setBackgroundColor(android.R.attr.windowBackground)
    }

    private fun settingsStartPadding(tvInfo: TextView) {
	  val densityDp = 8
	  val tvPaddingStartPx =
		densityDp * (resources.displayMetrics.densityDpi / DENSITY_DEFAULT)
	  tvInfo.setPadding(tvPaddingStartPx)
    }

    private fun retrieveArguments() {
	  viewModel.saveCollectionCommonInfo(
		requireArguments().getString(KEY_COLLECTION_COMMON_INFO)
	  )

	  viewModel.saveCollectionTitle(
		requireArguments().getString(KEY_COLLECTION_TITLE)
	  )


	  viewModel.saveCollectionId(
		requireArguments().getString(KEY_COLLECTION_ID)
	  )
    }

    companion object {
	  private const val KEY_COLLECTION_COMMON_INFO = "KEY_COLLECTION_COMMON_INFO"
	  private const val KEY_COLLECTION_TITLE = "KEY_COLLECTION_NAME"
	  private const val KEY_COLLECTION_ID = "KEY_COLLECTION_ID"
	  private const val TEXT_VIEW_INFO_ID = 165

	  fun newInstance(
		collectionCommonInfo: String,
		collectionTitle: String,
		collectionId: String,
	  ) = PhotosFromCollectionFragment().withArguments {
		putString(KEY_COLLECTION_COMMON_INFO, collectionCommonInfo)
		putString(KEY_COLLECTION_TITLE, collectionTitle)
		putString(KEY_COLLECTION_ID, collectionId)
	  }
    }
}