package com.pandacorp.randomui.presentation.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.pandacorp.randomui.R
import com.pandacorp.randomui.databinding.FragmentRandomCoinBinding
import com.pandacorp.randomui.presentation.utils.helpers.viewBinding

class RandomCoinFragment : Fragment(R.layout.fragment_random_coin) {
    private val binding by viewBinding(FragmentRandomCoinBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
    }

    private fun initViews() {
        binding.randomButton.apply {
            val headsText = resources.getString(R.string.heads)
            val tailsText = resources.getString(R.string.tails)
            setOnClickListener {
                val result = if ((0..1).random() == 0) headsText else tailsText
                Snackbar.make(binding.randomButton, result, 500).apply {
                    animationMode = BaseTransientBottomBar.ANIMATION_MODE_SLIDE
                    anchorView = binding.randomButton
                    show()
                }
            }
        }
    }
}