package com.pandacorp.randomui.domain.usecase

import com.pandacorp.randomui.domain.model.NumberItem

class GetRandomListUseCase(private val getRandomNumberUseCase: GetRandomNumberUseCase) {

    operator fun invoke(range: IntRange, times: Int): MutableList<NumberItem> {
        val list = mutableListOf<NumberItem>().apply {
            repeat(times) {
                add(NumberItem(it, getRandomNumberUseCase(range)))
            }
        }
        return list
    }
}