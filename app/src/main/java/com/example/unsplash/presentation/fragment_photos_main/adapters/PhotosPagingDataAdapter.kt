package com.example.unsplash.presentation.fragment_photos_main.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.isVisible
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.unsplash.R
import com.example.unsplash.databinding.ItemRvPhotoBinding
import com.example.unsplash.presentation._common.models.LikeInfo
import com.example.unsplash.presentation._common.some_components.GlideListener
import com.example.unsplash.presentation.fragment_photos_main.adapters.PhotosPagingDataAdapter.PhotosHolder
import com.example.unsplash.presentation.fragment_photos_main.models.Photo
import kotlinx.coroutines.*

class PhotosPagingDataAdapter(
    private val changeLike: (oldLikeInfo: LikeInfo) -> Unit,
    private val openDetails: suspend (sharedView: View, photoId: String) -> Boolean,
) : PagingDataAdapter<Photo, PhotosHolder>(PhotosDiffUtilItemCallback()) {

    private val coroutineScope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    var jobOpenDetails: Job? = null

    override fun onBindViewHolder(holder: PhotosHolder, position: Int) {
	  with(holder) {
		bind(getItem(position))
	  }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotosHolder {
	  val inflater = LayoutInflater.from(parent.context)
	  val view = inflater.inflate(R.layout.item_rv_photo, parent, false)
	  return PhotosHolder(view)
    }

    override fun onViewDetachedFromWindow(holder: PhotosHolder) {
	  holder.stopPlaceholderAnimation()
	  super.onViewDetachedFromWindow(holder)
    }

    inner class PhotosHolder(view: View) : RecyclerView.ViewHolder(view) {

	  private val context = itemView.context
	  private val binding = ItemRvPhotoBinding.bind(view)

	  private val placeHolderAnim = AnimationUtils.loadAnimation(context, R.anim.placeholder)
	  private lateinit var currentItem: Photo

	  init {
		settingChangeLikesState()
		settingOpenDetails()
	  }

	  fun bind(item: Photo?) {
		item?.let {
		    saveCurrentItem(item)
		    isInfoContainerVisible(false)
		    isPlaceholderAnimationActive(true)
		    setImagePhoto()
		}
	  }

	  private fun setInfo() {
		setPhotoPhotographersProfile()
		setPhotographersName()
		setCounterLikes()
		setImageLike()
		isInfoContainerVisible(true)
	  }

	  private fun settingOpenDetails() {
		binding.ivImagePhoto.setOnClickListener {
		    jobOpenDetails ?: let {
			  jobOpenDetails = coroutineScope.launch {
				binding.loaderDetailedInfo.isVisible = true

				val sharedView = binding.cardVPhotoContainer
				val photoId = currentItem.photoID
				sharedView.transitionName = photoId

				openDetails(sharedView, photoId)

				binding.loaderDetailedInfo.isVisible = false
				jobOpenDetails = null
			  }
		    }
		}
	  }

	  private fun settingChangeLikesState() {
		binding.imBtnImageLike.setOnClickListener {

		    val oldLikeInfo = LikeInfo(
			  photoId = currentItem.photoID,
			  likes = currentItem.likes,
			  likedByUser = currentItem.likedByUser
		    )

		    changeLike(oldLikeInfo)
		}
	  }

	  private fun setImagePhoto() {
		val view = binding.ivImagePhoto
		val resource = currentItem.photoResource

		Glide.with(itemView)
		    .load(resource)
		    .listener(
			  GlideListener(
				onLoadFailed = {
				    isPlaceholderAnimationActive(false)
				},
				onLoadReady = {
				    isPlaceholderAnimationActive(false)
				    setInfo()
				}
			  )
		    )
		    .into(view)
	  }

	  private fun isPlaceholderAnimationActive(stateAnimation: Boolean) {
		binding.ivPlaceholder.isVisible = stateAnimation
		if (stateAnimation) {
		    binding.ivPlaceholder.startAnimation(placeHolderAnim)
		} else binding.ivPlaceholder.clearAnimation()
	  }

	  fun stopPlaceholderAnimation() {
		isPlaceholderAnimationActive(false)
	  }

	  private fun saveCurrentItem(item: Photo) {
		currentItem = item
	  }

	  private fun setImageLike() {
		if (currentItem.likedByUser) setImage(R.drawable.ic_like_true)
		else setImage(R.drawable.ic_like_false)
	  }

	  private fun setPhotoPhotographersProfile() {
		val view = binding.ivImageProfile
		val resource = currentItem.photoResourcePhotographerProfile

		Glide.with(itemView)
		    .load(resource)
		    .into(view)
	  }

	  private fun isInfoContainerVisible(state: Boolean) {
		binding.fmlInfoContainer.isVisible = state
	  }

	  private fun setCounterLikes() {
		binding.tvLikesCounter.text = currentItem.likes
	  }

	  private fun setImage(@DrawableRes id: Int) {
		val resource = AppCompatResources.getDrawable(context, id)
		binding.imBtnImageLike.setImageDrawable(resource)
	  }

	  private fun setPhotographersName() {
		binding.tvPhotographerName.text = currentItem.photographerName
	  }

    }

}