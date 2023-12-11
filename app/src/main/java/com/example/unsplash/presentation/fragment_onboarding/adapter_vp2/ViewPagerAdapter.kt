package com.example.unsplash.presentation.fragment_onboarding.adapter_vp2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.unsplash.R
import com.example.unsplash.databinding.ItemVp2OnboardingBinding
import com.example.unsplash.presentation.fragment_onboarding.models.ItemOnboarding

class ViewPagerAdapter(
    private val list: List<ItemOnboarding>
) : RecyclerView.Adapter<ViewPagerAdapter.PagerVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerVH {
	  val inflater = LayoutInflater.from(parent.context)
	  val view = inflater.inflate(R.layout.item_vp2_onboarding, parent, false)
	  return PagerVH(view)
    }

    override fun onBindViewHolder(holder: PagerVH, position: Int) {
	  holder.bind(list[position])
    }

    override fun getItemCount() = list.size

    class PagerVH(view: View) : RecyclerView.ViewHolder(view) {

	  val binding = ItemVp2OnboardingBinding.bind(view)

	  fun bind(item: ItemOnboarding) {

		Glide.with(itemView)
		    .load(item.imageRes)
		    .into(binding.imViewImage)

		binding.tvMessage.text = itemView.context.getString(item.title)
	  }

    }

}