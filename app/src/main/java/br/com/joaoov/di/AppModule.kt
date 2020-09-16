package br.com.joaoov.di

import br.com.joaoov.AppDatabase
import br.com.joaoov.EstadoAppViewModel
import br.com.joaoov.ui.AmbientCreateViewModel
import br.com.joaoov.ui.AmbientViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { AmbientViewModel(get()) }
    viewModel { AmbientCreateViewModel(get()) }
    viewModel { EstadoAppViewModel() }
}

val daoModule = module {
    single { AppDatabase.getInstance(androidContext()).LevantamentoDAO() }
}
