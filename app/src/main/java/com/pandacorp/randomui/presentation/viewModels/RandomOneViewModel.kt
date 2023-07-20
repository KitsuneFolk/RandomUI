package com.pandacorp.randomui.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pandacorp.randomui.domain.usecase.GetRandomNumberUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RandomOneViewModel(private val getRandomNumberUseCase: GetRandomNumberUseCase) : ViewModel() {
    private val _number = MutableStateFlow(0)
    val number: StateFlow<Int> = _number

    private fun updateValue(newValue: Int) {
        viewModelScope.launch {
            _number.emit(newValue)
        }
    }

    fun getRandomNumber(range: IntRange): Int {
        val number = getRandomNumberUseCase(range)
        updateValue(number)
        return number
    }
}