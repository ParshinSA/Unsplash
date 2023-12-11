package com.example.unsplash.presentation.abstract_fragment_photos

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.example.unsplash.R
import com.example.unsplash._common.helpers.connection_state.ConnectionStateProvider
import com.example.unsplash.presentation.AppApplication
import com.example.unsplash.presentation._common.interfaces.PhotoDetailsHandler
import com.example.unsplash.presentation._common.models.LikeInfo
import com.example.unsplash.presentation.abstract_fragment_items_display.ItemsDisplayAbstractFragment
import com.example.unsplash.presentation.base.ViewModelsFactory
import com.example.unsplash.presentation.fragment_photos_main.adapters.PhotosPagingDataAdapter
import com.example.unsplash.presentation.fragment_photos_main.models.Photo
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

abstract class PhotosAbstractFragment
    : ItemsDisplayAbstractFragment<Photo, PhotosPagingDataAdapter.PhotosHolder>() {

    override val titleToolbar by lazy { getString(R.string.photos) }

    @Inject
    lateinit var viewModelsFactory: ViewModelsFactory
    abstract override val viewModel: PhotosAbstractViewModel

    @Inject
    lateinit var connectionStateProvider: ConnectionStateProvider

    override val layoutManagerItemsRv by lazy { GridLayoutManager(requireContext(), 2) }

    override val pagingAdapterItemsRv by lazy {
	  PhotosPagingDataAdapter(::changeLikeState, ::openPhotoDetailsFrg)
    }

    //******************** lifecycle ********************//
    override fun onAttach(context: Context) {
	  injectDependency()
	  super.onAttach(context)
    }
    //******************** lifecycle ********************//

    private fun changeLikeState(oldLikeInfo: LikeInfo) {
	  viewModel.changeLike(oldLikeInfo)
    }

    private suspend fun openPhotoDetailsFrg(sharedView: View, photoId: String): Boolean {
	  return suspendCoroutine { continuation ->
		if (connectionStateProvider.isDeviceOnline() == null) {
		    continuation.resume(false)
		    return@suspendCoroutine
		}

		(parentFragment as? PhotoDetailsHandler)?.let { parent ->

		    parentFragmentManager
			  .setFragmentResultListener(photoId, viewLifecycleOwner) { requestKey, _ ->
				if (requestKey == photoId) {
				    continuation.resume(true)
				    parentFragmentManager.clearFragmentResultListener(photoId)
				}
			  }

		    parent.openPhotoDetailsFrg(photoId = photoId, sharedView = sharedView)
		} ?: continuation.resume(false)
	  }
    }

    private fun injectDependency() {
	  (requireActivity().applicationContext as AppApplication).component.inject(this)
    }

}