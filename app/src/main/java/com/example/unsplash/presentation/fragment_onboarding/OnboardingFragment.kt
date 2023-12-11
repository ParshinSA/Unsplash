package com.example.unsplash.presentation.fragment_onboarding

import android.content.Context
import android.view.MenuItem
import android.view.View
import android.view.animation.AnimationUtils
import androidx.core.view.children
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.example.unsplash.R
import com.example.unsplash._common.extensions.addEndAnimationListener
import com.example.unsplash.databinding.FragmentOnboardingBinding
import com.example.unsplash.presentation.AppApplication
import com.example.unsplash.presentation.base.BaseFragment
import com.example.unsplash.presentation.base.ViewModelsFactory
import com.example.unsplash.presentation.fragment_onboarding.adapter_vp2.ViewPagerAdapter
import javax.inject.Inject

class OnboardingFragment :
    BaseFragment<FragmentOnboardingBinding>(FragmentOnboardingBinding::inflate) {

    @Inject
    lateinit var viewModelsFactory: ViewModelsFactory
    private val viewModel by viewModels<OnboardingViewModel> { viewModelsFactory }

    private val adapterVp2 by lazy { ViewPagerAdapter(viewModel.getOnboardingList()) }

    //******************** lifecycle ********************//
    override fun onAttach(context: Context) {
	  injectDependency()
	  super.onAttach(context)
    }
    //******************** lifecycle ********************//

    override fun thisCustomActions() {
	  // Not yet implemented
    }

    override fun thisCustomSettings() {
	  settingsIntro()
    }

    private fun settingsIntro() {
	  settingsVp2()
	  settingsSkipIntro()
	  settingsShowIntro()
	  settingsBtnNextItemIntro()
    }

    private fun settingsVp2() {
	  vp2Listener()
    }

    private fun vp2Listener() {
	  binding.viewPager2Introduce.registerOnPageChangeCallback(object :
		ViewPager2.OnPageChangeCallback() {
		override fun onPageSelected(position: Int) {
		    if (position == binding.viewPager2Introduce.adapter?.itemCount?.minus(1)) {
			  binding.btnNext.text = getString(R.string.go)
		    } else {
			  binding.btnNext.text = getString(R.string.next)
		    }
		}
	  })
    }

    private fun settingsBtnNextItemIntro() {
	  binding.btnNext.setOnClickListener {
		val vp2 = binding.viewPager2Introduce
		val maxItemPositions = vp2.adapter?.itemCount?.minus(1) ?: return@setOnClickListener
		val currentItemPosition = vp2.currentItem
		if (currentItemPosition < maxItemPositions) vp2.currentItem = currentItemPosition + 1
		else closeOnboarding()
	  }
    }

    private fun closeOnboarding() {
	  hideContent()
	  startAnimationExitOnboarding()
    }

    private fun startAnimationExitOnboarding() {
	  binding.toolbar.startAnimation(
		AnimationUtils.loadAnimation(requireContext(), R.anim.exit_onboarding_animation_set)
		    .apply { addEndAnimationListener { viewModel.closeOnboarding() } }
	  )
    }

    private fun settingsSkipIntro() {
	  val btnSkip = binding.toolbar.menu.children.firstOrNull { menuItem: MenuItem ->
		menuItem.itemId == R.id.skip
	  } ?: return

	  btnSkip.setOnMenuItemClickListener {
		closeOnboarding()
		true
	  }
    }

    private fun hideContent() {
	  listOf(binding.btnNext, binding.viewPager2Introduce, binding.dotsIndicator)
		.forEach { view: View -> view.isVisible = false }
	  binding.toolbar.title = ""
    }

    private fun settingsShowIntro() {
	  setAdapterVp2()
	  setDotsIndicatorVp2()
    }

    private fun setDotsIndicatorVp2() {
	  binding.dotsIndicator.setViewPager2(binding.viewPager2Introduce)
    }

    private fun setAdapterVp2() {
	  binding.viewPager2Introduce.adapter = adapterVp2
    }

    private fun injectDependency() {
	  (requireContext().applicationContext as AppApplication).component.inject(this)
    }

    companion object {
	  fun newInstance() = OnboardingFragment()
    }
}
