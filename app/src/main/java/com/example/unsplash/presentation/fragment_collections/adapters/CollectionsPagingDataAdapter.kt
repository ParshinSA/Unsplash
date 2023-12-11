package com.example.unsplash.presentation.fragment_collections.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.unsplash.R
import com.example.unsplash.databinding.ItemRvCollectionBinding
import com.example.unsplash.presentation.fragment_collections.adapters.CollectionsPagingDataAdapter.CollectionsViewHolder
import com.example.unsplash.presentation.fragment_collections.models.PhotosCollection

class CollectionsPagingDataAdapter(
    private val clickByCollection: (collectionId: PhotosCollection) -> Unit,
) :
    PagingDataAdapter<PhotosCollection, CollectionsViewHolder>(CollectionsDiffUtilItemCallback()) {

    override fun onBindViewHolder(holder: CollectionsViewHolder, position: Int) {
	  holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectionsViewHolder {
	  val inflater = LayoutInflater.from(parent.context)
	  val view = inflater.inflate(R.layout.item_rv_collection, parent, false)
	  return CollectionsViewHolder(view, clickByCollection)
    }

    class CollectionsViewHolder(
	  view: View,
	  private val clickByCollection: (collectionId: PhotosCollection) -> Unit
    ) : RecyclerView.ViewHolder(view) {

	  private val binding = ItemRvCollectionBinding.bind(view)
	  private var collection: PhotosCollection? = null

	  init {
		settingsPhotosCollectionContainer()
		settingsOpeningPhotosFromCollectionFrg()
	  }

	  private fun settingsOpeningPhotosFromCollectionFrg() {
		binding.llPhotosCollectionContainer.setOnClickListener {
		    collection?.apply(clickByCollection)
		}
	  }

	  private fun settingsPhotosCollectionContainer() {
		val heightPixelsScreen = itemView.context.resources.displayMetrics.heightPixels
		binding.llPhotosCollectionContainer.layoutParams = ViewGroup.LayoutParams(
		    ViewGroup.LayoutParams.MATCH_PARENT,
		    (0.35 * heightPixelsScreen).toInt()
		)
	  }

	  fun bind(item: PhotosCollection?) {
		item?.let {
		    collection = item
		    setCoverPhoto()
		    setPreviewPhotos()
		    setTitleCollection()
		}
	  }

	  private fun setTitleCollection() {
		binding.tvTitleCollection.text = collection?.title ?: return
	  }

	  private fun setPreviewPhotos() {
		val previewPhotos = collection?.previewPhotos ?: return
		val allPhotos = previewPhotos.size

		val containerLl = binding.llPreviewPhotosContainer
		containerLl.weightSum = allPhotos - 1f

		for (index in 1 until allPhotos) {
		    val imageView = ImageView(itemView.context).apply {
			  layoutParams = LinearLayout.LayoutParams(
				0,
				LinearLayout.LayoutParams.MATCH_PARENT,
				1f
			  )
			  scaleType = ImageView.ScaleType.CENTER_CROP
		    }

		    containerLl.addView(imageView)

		    Glide.with(itemView)
			  .load(previewPhotos[index])
			  .into(imageView)
		}
	  }

	  private fun setCoverPhoto() {
		val url = collection?.previewPhotos?.get(0) ?: return
		Glide.with(itemView)
		    .load(url)
		    .into(binding.ivCoverPhoto)
	  }

    }
}