package com.pandacorp.randomui.presentation.ui.screen

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.pandacorp.randomui.R
import com.pandacorp.randomui.presentation.utils.helpers.supportActionBar

class MainScreen : Fragment(R.layout.screen_main) {
    private val navHostFragment by lazy {
        childFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
    }

    private val navController by lazy {
        navHostFragment.navController
    }

    fun navigateFragment(id: Int, options: Bundle? = bundleOf()) {
        navController.navigate(id, options)
        setTitle()
    }

    override fun onResume() {
        super.onResume()
        setTitle()
    }

    private fun setTitle() {
        val stringId = when (navController.currentDestination?.id) {
            R.id.nav_one -> R.string.oneNumber
            R.id.nav_many -> R.string.manyNumbers
            R.id.nav_coin -> R.string.coin
            else -> R.string.app_name
        }
        supportActionBar?.setTitle(stringId)
    }
}