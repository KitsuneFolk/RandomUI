package com.pandacorp.randomui.presentation.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.pandacorp.randomui.R
import com.pandacorp.randomui.databinding.FragmentRandomManyBinding
import com.pandacorp.randomui.presentation.ui.activity.MainActivity
import com.pandacorp.randomui.presentation.utils.helpers.viewBinding
import com.pandacorp.randomui.presentation.viewModels.RandomManyViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class RandomManyFragment : Fragment(R.layout.fragment_random_many) {
    private val binding by viewBinding(FragmentRandomManyBinding::bind)

    private val fragulaNavController by lazy {
        (requireActivity() as MainActivity).getFragulaNavController()
    }
    private val vm: RandomManyViewModel by sharedViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
    }

    private fun initViews() {
        binding.randomButton.setOnClickListener {
            try {
                val _min = binding.minEditText.text.toString().toInt()
                val _max = binding.maxEditText.text.toString().toInt()

                // Reassign variables if max is less than min
                val min = _min.coerceAtMost(_max)
                val max = _max.coerceAtLeast(_min)

                val times = binding.timesEditText.text.toString().toInt()
                vm.getRandomList(min..max, times)
                fragulaNavController.navigate(R.id.nav_listview_screen)
            } catch (e: NumberFormatException) {
                // Empty editTexts
            }
        }
    }
}