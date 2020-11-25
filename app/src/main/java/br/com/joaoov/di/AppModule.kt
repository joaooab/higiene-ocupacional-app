package br.com.joaoov.di

import br.com.joaoov.SyncViewModel
import br.com.joaoov.data.local.AppDatabase
import br.com.joaoov.data.remote.AppRemote
import br.com.joaoov.data.remote.ResourceService
import br.com.joaoov.data.remote.SyncService
import br.com.joaoov.repository.*
import br.com.joaoov.ui.ambient.AmbientViewModel
import br.com.joaoov.ui.company.CompanyViewModel
import br.com.joaoov.ui.departament.DepartamentViewModel
import br.com.joaoov.ui.function.FunctionViewModel
import br.com.joaoov.ui.risk.RiskViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { AmbientViewModel(get()) }
    viewModel { CompanyViewModel(get()) }
    viewModel { DepartamentViewModel(get()) }
    viewModel { FunctionViewModel(get()) }
    viewModel { SyncViewModel(get(), get()) }
    viewModel { RiskViewModel(get()) }
}

val daoModule = module {
    single { AppDatabase.getInstance(androidContext()).ambientDAO() }
    single { AppDatabase.getInstance(androidContext()).departamentDAO() }
    single { AppDatabase.getInstance(androidContext()).companyDAO() }
    single { AppDatabase.getInstance(androidContext()).functionDAO() }
    single { AppDatabase.getInstance(androidContext()).syncronizeDAO() }
    single { AppDatabase.getInstance(androidContext()).resourceRiskDAO() }
    single { AppDatabase.getInstance(androidContext()).resourceAmbientDAO() }
    single { AppDatabase.getInstance(androidContext()).resourceAgentDAO() }
    single { AppDatabase.getInstance(androidContext()).riskDao() }
}

val repositoryModule = module {
    single<DepartamentRepository> { DepartamentRepositoryImpl(get()) }
    single<AmbientRepository> { AmbientRepositoryImpl(get()) }
    single<FunctionRepository> { FunctionRepositoryImpl(get()) }
    single<ResourceRepository> { ResourceRepositoryImpl(get(), get(), get(), get()) }
    single<SyncronizeRepository> { SyncronizeRepositoryImpl(get(), get()) }
    single<RiskRepository> { RiskRepositoryImpl(get()) }
}

val servciceModule = module {
    single { AppRemote.create<ResourceService>() }
    single { AppRemote.create<SyncService>() }
}

