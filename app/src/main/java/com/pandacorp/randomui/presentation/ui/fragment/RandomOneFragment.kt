package com.pandacorp.randomui.presentation.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.pandacorp.randomui.R
import com.pandacorp.randomui.databinding.FragmentRandomOneBinding
import com.pandacorp.randomui.presentation.utils.helpers.viewBinding
import com.pandacorp.randomui.presentation.viewModels.RandomOneViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class RandomOneFragment : Fragment(R.layout.fragment_random_one) {
    private val binding by viewBinding(FragmentRandomOneBinding::bind)

    private val vm: RandomOneViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
    }

    private fun initViews() {
        lifecycleScope.launch {
            vm.number.collect {
                binding.randomTv.text = it.toString()
            }
        }
        binding.randomButton.setOnClickListener {
            try {
                val min = binding.minEditText.text.toString().toInt()
                val max = binding.maxEditText.text.toString().toInt()

                // Reassign variables if max is less than min
                val coercedMin = min.coerceAtMost(max)
                val coercedMax = max.coerceAtLeast(min)

                vm.getRandomNumber(coercedMin..coercedMax)
            } catch (e: NumberFormatException) {
                // Empty editTexts
            }
        }
    }
}