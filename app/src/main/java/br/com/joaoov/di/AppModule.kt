package br.com.joaoov.di

import br.com.joaoov.ComponentViewModel
import br.com.joaoov.SyncViewModel
import br.com.joaoov.data.local.AppDatabase
import br.com.joaoov.data.local.defaultPref
import br.com.joaoov.data.remote.AppService
import br.com.joaoov.data.remote.auth.AuthService
import br.com.joaoov.data.remote.report.ReportService
import br.com.joaoov.data.remote.resource.ResourceService
import br.com.joaoov.data.remote.sync.SyncService
import br.com.joaoov.data.remote.user.UserService
import br.com.joaoov.repository.*
import br.com.joaoov.ui.ambient.AmbientViewModel
import br.com.joaoov.ui.auth.AuthViewModel
import br.com.joaoov.ui.company.CompanyViewModel
import br.com.joaoov.ui.component.move.MoveViewModel
import br.com.joaoov.ui.departament.DepartamentViewModel
import br.com.joaoov.ui.export.ExportViewModel
import br.com.joaoov.ui.function.FunctionViewModel
import br.com.joaoov.ui.risk.RiskViewModel
import br.com.joaoov.ui.user.UserViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { ComponentViewModel() }
    viewModel { AmbientViewModel(get(), get()) }
    viewModel { CompanyViewModel(get()) }
    viewModel { DepartamentViewModel(get()) }
    viewModel { FunctionViewModel(get()) }
    viewModel { SyncViewModel(get(), get()) }
    viewModel { RiskViewModel(get(), get()) }
    viewModel { ExportViewModel(get(), get()) }
    viewModel { MoveViewModel(get(), get(), get(), get()) }
    viewModel { UserViewModel(get()) }
    viewModel { AuthViewModel(get()) }
}

val daoModule = module {
    single { AppDatabase.getInstance(androidContext()) }
    factory { defaultPref(androidApplication()) }
    single { get<AppDatabase>().ambientDAO() }
    single { get<AppDatabase>().departamentDAO() }
    single { get<AppDatabase>().companyDAO() }
    single { get<AppDatabase>().functionDAO() }
    single { get<AppDatabase>().syncronizeDAO() }
    single { get<AppDatabase>().resourceRiskDAO() }
    single { get<AppDatabase>().resourceAmbientDAO() }
    single { get<AppDatabase>().resourceAgentDAO() }
    single { get<AppDatabase>().riskDAO() }
    single { get<AppDatabase>().reportDAO() }
}

val repositoryModule = module {
    single<CompanyRepository> { CompanyRepositoryImpl(get()) }
    single<DepartamentRepository> { DepartamentRepositoryImpl(get()) }
    single<AmbientRepository> { AmbientRepositoryImpl(get()) }
    single<FunctionRepository> { FunctionRepositoryImpl(get()) }
    single<ResourceRepository> { ResourceRepositoryImpl(get(), get(), get(), get()) }
    single<SyncronizeRepository> { SyncronizeRepositoryImpl(get(), get()) }
    single<RiskRepository> { RiskRepositoryImpl(get()) }
    single<ReportRepository> { ReportRepositoryImpl(get(), get()) }
    single<UserRepository> { UserRepositoryImpl(get()) }
    single<AuthRepository> { AuthRepositoryImpl(get()) }
}

val servciceModule = module {
    single { AppService.create<ResourceService>() }
    single { AppService.create<SyncService>() }
    single { AppService.create<ReportService>() }
    single { AppService.create<UserService>() }
    single { AppService.create<AuthService>() }
}

