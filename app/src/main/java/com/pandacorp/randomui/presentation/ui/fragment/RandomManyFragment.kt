package com.pandacorp.randomui.presentation.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.pandacorp.randomui.R
import com.pandacorp.randomui.databinding.FragmentRandomManyBinding
import com.pandacorp.randomui.presentation.ui.activity.MainActivity
import com.pandacorp.randomui.presentation.ui.viewModels.RandomManyViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class RandomManyFragment : Fragment() {
    private var _binding: FragmentRandomManyBinding? = null
    private val binding: FragmentRandomManyBinding get() = _binding!!

    private val fragulaNavController by lazy {
        (requireActivity() as MainActivity).getFragulaNavController()
    }
    private val vm: RandomManyViewModel by sharedViewModel()

    private fun initViews() {
        binding.apply {
            randomButton.setOnClickListener {
                try {
                    val _min = minEditText.text.toString().toInt()
                    val _max = maxEditText.text.toString().toInt()

                    // Reassign variables if max is less than min
                    val min = _min.coerceAtMost(_max)
                    val max = _max.coerceAtLeast(_min)

                    val times = timesEditText.text.toString().toInt()
                    vm.getRandomList(min..max, times)
                    fragulaNavController.navigate(R.id.nav_listview_screen)

                } catch (e: NumberFormatException) {
                    // Empty editTexts
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRandomManyBinding.inflate(layoutInflater)

        initViews()

        return binding.root
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

}