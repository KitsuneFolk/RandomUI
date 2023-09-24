package com.pandacorp.randomui.domain.usecase

class GetRandomNumberUseCase {
    operator fun invoke(range: IntRange): Int = range.random()
}