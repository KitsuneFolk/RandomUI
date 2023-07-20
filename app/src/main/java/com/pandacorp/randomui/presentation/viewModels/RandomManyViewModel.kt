package com.pandacorp.randomui.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pandacorp.randomui.domain.model.NumberItem
import com.pandacorp.randomui.domain.usecase.GetRandomListUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RandomManyViewModel(private val getRandomListUseCase: GetRandomListUseCase) : ViewModel() {
    private val _numbersList = MutableStateFlow<MutableList<NumberItem>>(mutableListOf())
    val numbersList: StateFlow<MutableList<NumberItem>> = _numbersList

    private fun updateList(newList: MutableList<NumberItem>) {
        viewModelScope.launch {
            _numbersList.emit(newList)
        }
    }

    fun getRandomList(range: IntRange, times: Int): MutableList<NumberItem> {
        val list = getRandomListUseCase(range, times)
        updateList(list)
        return list
    }
}