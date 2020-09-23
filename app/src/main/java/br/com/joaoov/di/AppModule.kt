package br.com.joaoov.di

import br.com.joaoov.AppDatabase
import br.com.joaoov.EstadoAppViewModel
import br.com.joaoov.ui.ambient.AmbientCreateViewModel
import br.com.joaoov.ui.ambient.AmbientDetailViewModel
import br.com.joaoov.ui.ambient.AmbientViewModel
import br.com.joaoov.ui.company.CompanyCreateViewModel
import br.com.joaoov.ui.company.CompanyViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { AmbientViewModel(get()) }
    viewModel { AmbientCreateViewModel(get()) }
    viewModel { AmbientDetailViewModel(get()) }
    viewModel { CompanyViewModel(get()) }
    viewModel { CompanyCreateViewModel(get()) }
    viewModel { EstadoAppViewModel() }
}

val daoModule = module {
    single { AppDatabase.getInstance(androidContext()).ambientDAO() }
    single { AppDatabase.getInstance(androidContext()).companyDAO() }
}
