package com.pandacorp.randomui.presentation.di.modules

import com.pandacorp.randomui.domain.usecase.GetRandomListUseCase
import com.pandacorp.randomui.domain.usecase.GetRandomNumberUseCase
import com.pandacorp.randomui.presentation.viewModels.RandomManyViewModel
import com.pandacorp.randomui.presentation.viewModels.RandomOneViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val randomModule = module {
    singleOf(::GetRandomNumberUseCase)
    singleOf(::GetRandomListUseCase)
    viewModelOf(::RandomManyViewModel)
    viewModelOf(::RandomOneViewModel)
}