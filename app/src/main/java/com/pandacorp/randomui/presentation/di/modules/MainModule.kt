package com.pandacorp.randomui.presentation.di.modules

import com.pandacorp.randomui.domain.usecase.GetRandomListUseCase
import com.pandacorp.randomui.domain.usecase.GetRandomNumberUseCase
import com.pandacorp.randomui.presentation.ui.viewModels.RandomManyViewModel
import com.pandacorp.randomui.presentation.ui.viewModels.RandomOneViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val randomModule = module {
    single {
        GetRandomNumberUseCase()
    }
    single {
        GetRandomListUseCase(get())
    }
    viewModel {
        RandomManyViewModel(get())
    }
    viewModel {
        RandomOneViewModel(get())
    }
}