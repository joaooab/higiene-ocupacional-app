package br.com.joaoov

import android.app.Application
import br.com.joaoov.di.daoModule
import br.com.joaoov.di.repositoryModule
import br.com.joaoov.di.servciceModule
import br.com.joaoov.di.viewModelModule
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
                    servciceModule
                )
            )
        }
        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true)
    }
}
