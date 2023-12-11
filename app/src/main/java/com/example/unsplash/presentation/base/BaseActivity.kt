package com.example.unsplash.presentation.base

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.example.unsplash.R
import com.example.unsplash._common.helpers.loading.LoadCompletedBroadcastHelper
import com.example.unsplash.databinding.BaseActivityBinding
import com.example.unsplash.presentation.AppApplication
import com.example.unsplash.presentation._common.interfaces.BackPressedListener
import com.example.unsplash.presentation.fragment_exit.ExitFragment
import javax.inject.Inject

abstract class BaseActivity : AppCompatActivity() {

    @Inject
    lateinit var loadCompletedBroadcastHelper: LoadCompletedBroadcastHelper

    private var _binding: BaseActivityBinding? = null
    private val baseBinding get() = checkNotNull(_binding)

    //******************** lifecycle ********************//
    override fun onCreate(savedInstanceState: Bundle?) {
	  super.onCreate(savedInstanceState)
	  _binding = BaseActivityBinding.inflate(layoutInflater)
	  setContentView(baseBinding.root)
	  baseAction()
    }

    override fun onResume() {
	  super.onResume()
	  loadCompletedBroadcastHelper.register()
    }

    override fun onPause() {
	  loadCompletedBroadcastHelper.unregister()
	  super.onPause()
    }
    //******************** lifecycle ********************//

    private fun baseAction() {
	  injectDependency()
	  settingExitFromApp()
    }

    private fun settingExitFromApp() {
	  setBackPressCallback()
    }

    protected fun showExitFrg(isLogout: Boolean) {
	  supportFragmentManager.beginTransaction()
		.add(R.id.appScreensContainerIntoBaseActivity, ExitFragment.newInstance(isLogout))
		.addToBackStack(ExitFragment.TAG_EXIT_FRG)
		.commit()
    }

    private fun setBackPressCallback() {
	  onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
		override fun handleOnBackPressed() {
		    val fragments = supportFragmentManager.fragments

		    for (fragment in fragments) {
			  if (fragment.isVisible && fragment is BackPressedListener && fragment.onBackPressed()) {
				return
			  } else if (fragment is ExitFragment) {
				removeExit(fragment)
				return
			  } else continue
		    }

		    showExitFrg(isLogout = false)
		}
	  })
    }

    private fun removeExit(fragment: ExitFragment) {
	  supportFragmentManager.apply {
		popBackStack()
		beginTransaction().remove(fragment)
	  }
    }

    private fun injectDependency() {
	  (applicationContext as AppApplication).component.inject(this)
    }
}