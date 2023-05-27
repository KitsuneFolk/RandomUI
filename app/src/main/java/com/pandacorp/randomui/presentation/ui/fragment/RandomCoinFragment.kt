package com.pandacorp.randomui.presentation.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.pandacorp.randomui.R
import com.pandacorp.randomui.databinding.FragmentRandomCoinBinding

class RandomCoinFragment : Fragment() {
    private var _binding: FragmentRandomCoinBinding? = null
    private val binding: FragmentRandomCoinBinding get() = _binding!!

    private fun initViews() {
        binding.randomButton.apply {
            val headsText = resources.getString(R.string.heads)
            val tailsText = resources.getString(R.string.tails)
            setOnClickListener {
                val result =
                    if ((0..1).random() == 0) headsText
                    else tailsText
                Snackbar.make(binding.randomButton, result, 500).apply {
                    animationMode = BaseTransientBottomBar.ANIMATION_MODE_SLIDE
                    anchorView = binding.randomButton
                    show()
                }
            }

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRandomCoinBinding.inflate(layoutInflater)

        initViews()

        return binding.root
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}