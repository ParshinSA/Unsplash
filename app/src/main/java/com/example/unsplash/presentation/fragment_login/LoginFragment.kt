package com.example.unsplash.presentation.fragment_login

import android.content.Context
import android.content.Intent
import android.view.animation.AnimationUtils
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.unsplash.R
import com.example.unsplash._common.extensions.launchMain
import com.example.unsplash._common.extensions.multiCollectToFlow
import com.example.unsplash.databinding.FragmentLoginBinding
import com.example.unsplash.presentation.AppApplication
import com.example.unsplash.presentation.base.BaseFragment
import com.example.unsplash.presentation.base.ViewModelsFactory
import com.example.unsplash.presentation.fragment_login.models.LoginFrgUiState
import com.example.unsplash.presentation.fragment_login.models.LoginFrgUiState.*
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class LoginFragment :
    BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    @Inject
    lateinit var viewModelsFactory: ViewModelsFactory
    private val viewModel by viewModels<LoginViewModel> { viewModelsFactory }

    private val getLoginResponse = registerForActivityResult(
	  ActivityResultContracts.StartActivityForResult()
    ) { result ->
	  val dataIntent = result?.data ?: return@registerForActivityResult
	  viewModel.handleAuthResponseIntent(dataIntent)
    }

    //******************** lifecycle ********************//
    override fun onAttach(context: Context) {
	  injectDependency()
	  super.onAttach(context)
    }
    //******************** lifecycle ********************//

    override fun thisCustomActions() {
	  enterAnimationShow()
    }

    override fun thisCustomSettings() {
	  settingsBtnLogin()
	  observeData()
    }

    private fun settingsBtnLogin() {
	  clickListener()
	  setTitle()
    }

    private fun startedFrgUiState() {
	  binding.progressBar.isVisible = false
	  binding.btnLogin.isVisible = true
    }

    private fun initializedLoginUiState() {
	  binding.progressBar.isVisible = true
	  binding.btnLogin.isVisible = false
    }

    private fun unsuccessfulLoginUiState() {
	  startedFrgUiState()
    }

    private fun successfulLoginUiState() {
	  binding.btnLogin.isEnabled = false
	  startedFrgUiState()
    }

    private fun handlesUiState(state: LoginFrgUiState) {
	  when (state) {
		ON_UNSUCCESSFUL_LOGIN -> unsuccessfulLoginUiState()
		ON_INITIALIZED_LOGIN -> initializedLoginUiState()
		ON_SUCCESSFUL_LOGIN -> successfulLoginUiState()
		ON_STARTED_FRAGMENT -> startedFrgUiState()
	  }
    }

    private fun observeData() {
	  lifecycleScope.launchMain(action = {
		lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
		    multiCollectToFlow(
			  viewModel.loginPageIntentSharedFlow.onEach { getLoadingResponse(it) },
			  viewModel.uiStateStateFlow.onEach { handlesUiState(it) },
		    )
		}
	  }, onError = { it.printStackTrace() })
    }

    private fun getLoadingResponse(intent: Intent?) {
	  intent ?: return
	  getLoginResponse.launch(intent)
    }

    private fun clickListener() {
	  binding.btnLogin.setOnClickListener {
		viewModel.openLoginPage()
	  }
    }

    private fun enterAnimationShow() {
	  binding.containerLogo.startAnimation(
		AnimationUtils.loadAnimation(requireContext(), R.anim.enter_login_animation_set)
	  )
    }

    private fun setTitle() {
	  val appName = getString(R.string.app_name)
	  binding.btnLogin.text = getString(R.string.login_via, appName)
    }

    private fun injectDependency() {
	  (requireActivity().applicationContext as AppApplication).component.inject(this)
    }

    companion object {
	  fun newInstance() = LoginFragment()
    }
}