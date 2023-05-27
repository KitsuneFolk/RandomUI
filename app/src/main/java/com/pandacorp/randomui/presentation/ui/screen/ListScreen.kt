package com.pandacorp.randomui.presentation.ui.screen

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.pandacorp.randomui.R
import com.pandacorp.randomui.databinding.ScreenListBinding
import com.pandacorp.randomui.presentation.ui.adapter.NumbersAdapter
import com.pandacorp.randomui.presentation.ui.viewModels.RandomManyViewModel
import com.pandacorp.randomui.presentation.utils.supportActionBar
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class ListScreen : Fragment() {
    private var _binding: ScreenListBinding? = null
    private val binding get() = _binding!!

    private val vm: RandomManyViewModel by sharedViewModel()

    private fun initViews() {
        val adapter = NumbersAdapter().apply {
            lifecycleScope.launch {
                vm.numbersList.collect {
                    Log.d("ListScreen", "initViews: submitList = ${it.size}")
                    submitList(it)
                }
            }
        }
        binding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(
                DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
            )
            this.adapter = adapter
            Log.d("ListScreen", "initViews: adapter.values.size = ${adapter.currentList.size}")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = ScreenListBinding.inflate(inflater, container, false)
        initViews()
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        supportActionBar?.title = resources.getString(R.string.manyNumbers)
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}