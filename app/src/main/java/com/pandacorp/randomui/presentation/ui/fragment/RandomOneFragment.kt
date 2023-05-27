package com.pandacorp.randomui.presentation.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.pandacorp.randomui.databinding.FragmentRandomOneBinding
import com.pandacorp.randomui.presentation.ui.viewModels.RandomOneViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class RandomOneFragment : Fragment() {
    private var _binding: FragmentRandomOneBinding? = null
    private val binding: FragmentRandomOneBinding get() = _binding!!

    private val vm: RandomOneViewModel by viewModel()

    private fun initViews() {
        binding.apply {
            randomButton.setOnClickListener {
                try {
                    val _min = minEditText.text.toString().toInt()
                    val _max = maxEditText.text.toString().toInt()

                    // Reassign variables if max is less than min
                    val min = _min.coerceAtMost(_max)
                    val max = _max.coerceAtLeast(_min)

                    vm.getRandomNumber(min..max)
                    lifecycleScope.launch {
                        vm.number.collect {
                            binding.randomTv.text = it.toString()
                        }
                    }
                } catch (e: NumberFormatException) {
                    // Empty editTexts
                }
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentRandomOneBinding.inflate(layoutInflater)

        initViews()

        return binding.root
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}