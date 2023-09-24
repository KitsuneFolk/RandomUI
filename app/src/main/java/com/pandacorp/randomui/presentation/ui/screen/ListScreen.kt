package com.pandacorp.randomui.presentation.ui.screen

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.pandacorp.randomui.R
import com.pandacorp.randomui.databinding.ScreenListBinding
import com.pandacorp.randomui.presentation.ui.adapter.NumbersAdapter
import com.pandacorp.randomui.presentation.utils.helpers.supportActionBar
import com.pandacorp.randomui.presentation.utils.helpers.viewBinding
import com.pandacorp.randomui.presentation.viewModels.RandomManyViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class ListScreen : Fragment(R.layout.screen_list) {
    private val binding by viewBinding(ScreenListBinding::bind)
    private val vm: RandomManyViewModel by activityViewModel()

    private val recyclerAdapter = NumbersAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
    }

    override fun onResume() {
        super.onResume()
        supportActionBar?.title = resources.getString(R.string.manyNumbers)
    }

    private fun initViews() {
        lifecycleScope.launch {
            vm.numbersList.collect {
                recyclerAdapter.submitList(it)
            }
        }

        binding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(
                DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
            )
            adapter = recyclerAdapter
        }
    }
}