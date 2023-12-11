package com.example.unsplash.presentation.fragment_navigate_by_screens

import android.content.Context
import android.view.MenuItem
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.unsplash.R
import com.example.unsplash.databinding.FragmentNavigateBinding
import com.example.unsplash.presentation.AppApplication
import com.example.unsplash.presentation._common.interfaces.BackPressedListener
import com.example.unsplash.presentation.base.BaseFragment
import com.example.unsplash.presentation.base.ViewModelsFactory
import com.google.android.material.navigation.NavigationBarView
import javax.inject.Inject

class NavigateByScreensFragment :
    BaseFragment<FragmentNavigateBinding>(FragmentNavigateBinding::inflate),
    BackPressedListener {

    @Inject
    lateinit var viewModelsFactory: ViewModelsFactory
    private val viewModel by viewModels<NavigateByScreensViewModel> { viewModelsFactory }

    private val navContainer = R.id.frameLayNavigateContainerScreens

    private val currentListFragments: List<Fragment>
	  get() = childFragmentManager.fragments

    //******************** lifecycle ********************//
    override fun onAttach(context: Context) {
	  super.onAttach(context)
	  injectDependency()
    }
    //******************** lifecycle ********************//

    override fun thisCustomActions() {
	  showDefaultFragment()
    }

    override fun thisCustomSettings() {
	  settingsBottomNavigationView()
    }

    private fun settingsBottomNavigationView() {
	  setMenuListener()
    }

    private fun showDefaultFragment() {
	  val menuItems = binding.bottomNavView.menu.children.toList()
	  val targetMenuItem =
		if (menuItems.lastIndex >= INDEX_TO_STARTING_MENU_ITEM)
		    menuItems.elementAt(INDEX_TO_STARTING_MENU_ITEM)
		else return

	  showFragmentByMenuItem(targetMenuItem)
	  targetMenuItem.isChecked = true
    }

    private fun setMenuListener() {
	  binding.bottomNavView.setOnItemSelectedListener(
		NavigationBarView.OnItemSelectedListener { item: MenuItem ->
		    showFragmentByMenuItem(item)
		    return@OnItemSelectedListener true
		})
    }

    private fun showFragmentByMenuItem(menuItem: MenuItem) {
	  val newFragmentTag = menuItem.itemId.toString()
	  var newFragment = childFragmentManager.findFragmentByTag(newFragmentTag)
	  val currentFragment = currentListFragments.firstOrNull { it.isVisible }

	  if (currentFragment != null && currentFragment === newFragment) return
	  val childFrgTransaction = childFragmentManager.beginTransaction()

	  if (newFragment == null) {
		newFragment = viewModel.getFragmentById(menuItem.itemId) ?: return
		childFrgTransaction.add(navContainer, newFragment, newFragmentTag)
	  }

	  currentFragment?.let { childFrgTransaction.hide(currentFragment) }
	  childFrgTransaction.show(newFragment).commit()
    }

    override fun onBackPressed(): Boolean {
	  val fragments = childFragmentManager.fragments
	  val currentFragment = fragments.firstOrNull { it.isVisible } ?: return false
	  return currentFragment is BackPressedListener && currentFragment.onBackPressed()
    }

    private fun injectDependency() {
	  (requireActivity().applicationContext as AppApplication).component.inject(this)
    }

    companion object {
	  //	   0 - "PhotosNavigableScreenFragment.kt"
	  //	   1 - "CollectionsNavigableScreenFragment.kt"
	  //	   2 - "ProfileNavigableScreenFragment.kt"
	  const val INDEX_TO_STARTING_MENU_ITEM = 0
	  const val TRANSITION_DURATION = 300L
	  fun newInstance() = NavigateByScreensFragment()
    }

}