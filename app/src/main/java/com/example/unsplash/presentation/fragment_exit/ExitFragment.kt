package com.example.unsplash.presentation.fragment_exit

import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.unsplash.R
import com.example.unsplash._common.extensions.launchMain
import com.example.unsplash._common.extensions.multiCollectToFlow
import com.example.unsplash._common.extensions.withArguments
import com.example.unsplash.databinding.FragmentExitBinding
import com.example.unsplash.presentation.AppApplication
import com.example.unsplash.presentation._common.some_components.theme_color_provider.ThemeColorProvider
import com.example.unsplash.presentation.base.BaseFragment
import com.example.unsplash.presentation.base.ViewModelsFactory
import kotlinx.coroutines.flow.onEach
import java.io.File
import javax.inject.Inject

class ExitFragment :
    BaseFragment<FragmentExitBinding>(FragmentExitBinding::inflate) {

    @Inject
    lateinit var themeColorProvider: ThemeColorProvider

    @Inject
    lateinit var vmFactory: ViewModelsFactory
    private val viewModel by viewModels<ExitViewModel> { vmFactory }

    private val getResponseLogout = registerForActivityResult(
	  ActivityResultContracts.StartActivityForResult()
    ) {
	  viewModel.saveLogoutState()
    }

    //******************** lifecycle ********************//
    override fun onAttach(context: Context) {
	  injectDependency()
	  super.onAttach(context)
    }
    //******************** lifecycle ********************//

    override fun thisCustomActions() {
	  retrieveArguments()
    }

    private fun retrieveArguments() {
	  val targetExit = requireArguments().getBoolean(KEY_IS_LOGOUT, false)
	  viewModel.saveTargetExit(targetExit)
    }

    override fun thisCustomSettings() {
	  settingsExitThisFragment()
	  settingsConfirmExit()
	  settingsDeclineExit()
	  observeData()
    }

    private fun observeData() {
	  lifecycleScope.launchMain(
		action = {
		    lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
			  multiCollectToFlow(
				viewModel.isLogoutStateFlow.onEach { handleIsLogout(it) },
				viewModel.logoutIntentSharedFlow.onEach { getResponseLogout(it) }
			  )
		    }
		}, onError = { it.printStackTrace() })
    }

    private fun getResponseLogout(intent: Intent?) {
	  intent ?: return
	  getResponseLogout.launch(intent)
    }

    private fun handleIsLogout(isLogout: Boolean?) {
	  isLogout ?: return
	  settingsBtnConfirmExit(isLogout)
	  settingsMessageExit(isLogout)
	  settingsTitleExit(isLogout)
    }

    private fun settingsTitleExit(isLogout: Boolean) {
	  val textId = if (isLogout) R.string.title_exit_logout else R.string.title_exit_close_app
	  binding.tvTitleExit.text = getString(textId)
    }

    private fun settingsMessageExit(isLogout: Boolean) {
	  val textId = if (isLogout) R.string.exit_alert_logout
	  else R.string.exit_alert_close_app
	  binding.tvMessage.text = getString(textId)
    }

    private fun settingsBtnConfirmExit(isLogout: Boolean) {
	  val textId = if (isLogout) R.string.btn_logout else R.string.btn_close
	  binding.btnConfirmExit.text = getString(textId)
    }

    private fun settingsExitThisFragment() {
	  initExitByClickOnBackground()
    }

    private fun initExitByClickOnBackground() {
	  binding.background.setOnClickListener {
		closeExitFragment()
	  }
    }

    private fun closeExitFragment() {
	  activity?.onBackPressedDispatcher?.onBackPressed()
    }

    private fun settingsDeclineExit() {
	  binding.btnDeclineExit.setOnClickListener { closeExitFragment() }
    }

    private fun settingsConfirmExit() {
	  binding.btnConfirmExit.setOnClickListener {
		if (viewModel.isLogoutStateFlow.value == false) closeApp()
		else {
		    viewModel.openLogoutPage()
		    clearCacheAndDatabaseFiles()
		}
	  }
    }

    private fun clearCacheAndDatabaseFiles() {
	  fun clearFolder(folder: File) {
		val listFile = folder.listFiles() ?: return
		for (file in listFile) {
		    if (file.isDirectory) clearFolder(file)
		    else file.delete()
		}
	  }
	  clearFolder(requireContext().cacheDir)
	  clearFolder(requireContext().filesDir)
	  viewModel.clearAllDatabaseFiles()
    }

    private fun closeApp() {
	  requireActivity().finish()
    }

    private fun injectDependency() {
	  (requireActivity().applicationContext as AppApplication).component.inject(this)
    }

    companion object {

	  const val TAG_EXIT_FRG = "ExitFragment"
	  const val KEY_IS_LOGOUT = "KEY_IS_LOGOUT"

	  fun newInstance(isLogout: Boolean) =
		ExitFragment().withArguments {
		    putBoolean(KEY_IS_LOGOUT, isLogout)
		}
    }

}