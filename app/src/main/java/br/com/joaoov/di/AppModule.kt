package br.com.joaoov.di

import br.com.joaoov.data.local.AppDatabase
import br.com.joaoov.data.remote.AppRemote
import br.com.joaoov.repository.*
import br.com.joaoov.ui.ambient.AmbientViewModel
import br.com.joaoov.ui.company.CompanyViewModel
import br.com.joaoov.ui.departament.DepartamentViewModel
import br.com.joaoov.ui.function.FunctionViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { AmbientViewModel(get()) }
    viewModel { CompanyViewModel(get()) }
    viewModel { DepartamentViewModel(get()) }
    viewModel { FunctionViewModel(get()) }
}

val daoModule = module {
    single { AppDatabase.getInstance(androidContext()).ambientDAO() }
    single { AppDatabase.getInstance(androidContext()).departamentDAO() }
    single { AppDatabase.getInstance(androidContext()).companyDAO() }
    single { AppDatabase.getInstance(androidContext()).functionDAO() }
}

val repositoryModule = module {
    single<DepartamentRepository> { DepartamentRepositoryImpl(get()) }
    single<AmbientRepository> { AmbientRepositoryImpl(get()) }
    single<FunctionRepository> { FunctionRepositoryImpl(get()) }
}

val servciceModule = module {
//    single { AppRemote.create<>() }
}

