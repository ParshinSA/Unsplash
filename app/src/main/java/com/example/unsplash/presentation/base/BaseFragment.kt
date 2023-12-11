package com.example.unsplash.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<BaseFrgViewBinding : ViewBinding>(
    private val bindingInflater: (layoutInflater: LayoutInflater) -> BaseFrgViewBinding
) : Fragment() {

    private var _binding: BaseFrgViewBinding? = null
    protected val binding get() = checkNotNull(_binding)

    //******************** lifecycle ********************//
    override fun onCreateView(
	  inflater: LayoutInflater,
	  container: ViewGroup?,
	  savedInstanceState: Bundle?
    ): View? {
	  _binding = bindingInflater.invoke(inflater)
	  return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
	  super.onViewCreated(view, savedInstanceState)

	  if (savedInstanceState == null) {
		thisCustomSettings() // warning!! first method
		thisCustomActions()
	  } else thisCustomSettings()

    }

    override fun onDestroy() {
	  _binding = null
	  super.onDestroy()
    }
    //******************** lifecycle ********************//

    abstract fun thisCustomActions()

    abstract fun thisCustomSettings()

}