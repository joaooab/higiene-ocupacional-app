package br.com.joaoov.di

import br.com.joaoov.AppDatabase
import br.com.joaoov.repository.AmbientRepository
import br.com.joaoov.repository.AmbientRepositoryImpl
import br.com.joaoov.repository.DepartamentRepository
import br.com.joaoov.repository.DepartamentRepositoryImpl
import br.com.joaoov.ui.ambient.AmbientViewModel
import br.com.joaoov.ui.company.CompanyViewModel
import br.com.joaoov.ui.departament.DepartamentViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { AmbientViewModel(get()) }
    viewModel { CompanyViewModel(get()) }
    viewModel { DepartamentViewModel(get()) }
}

val daoModule = module {
    single { AppDatabase.getInstance(androidContext()).ambientDAO() }
    single { AppDatabase.getInstance(androidContext()).departamentDAO() }
    single { AppDatabase.getInstance(androidContext()).companyDAO() }
}

val repositoryModule = module {
    single<DepartamentRepository> { DepartamentRepositoryImpl(get()) }
    single<AmbientRepository> { AmbientRepositoryImpl(get()) }
}
