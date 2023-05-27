package com.pandacorp.randomui.presentation.ui.screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.pandacorp.randomui.R
import com.pandacorp.randomui.databinding.ScreenMainBinding
import com.pandacorp.randomui.presentation.utils.app
import com.pandacorp.randomui.presentation.utils.supportActionBar

class MainScreen : Fragment() {
    private var _binding: ScreenMainBinding? = null
    private val binding get() = _binding!!

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

    private fun setTitle() {
        val stringId = when (app.selectedNavigationItemId) {
            R.id.nav_one -> R.string.oneNumber
            R.id.nav_many -> R.string.manyNumbers
            R.id.nav_coin -> R.string.coin
            else -> R.string.random
        }
        supportActionBar?.setTitle(stringId)
    }

    private fun initViews() {
        navigateFragment(app.selectedNavigationItemId)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = ScreenMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    override fun onResume() {
        super.onResume()
        setTitle()
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}