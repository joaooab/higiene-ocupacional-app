package br.com.joaoov

import android.app.Application
import br.com.joaoov.di.*
import com.google.firebase.crashlytics.FirebaseCrashlytics
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class AppAplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@AppAplication)
            modules(
                listOf(
                    viewModelModule,
                    daoModule,
                    repositoryModule,
                    servciceModule,
                    useCaseModule
                )
            )
        }
        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true)
    }
}
