package com.example.comics

import android.app.Application
import com.example.comics.di.AppModule
import com.example.comics.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.ksp.generated.module

open class ComicsApp: Application() {
    override fun onCreate() {
        super.onCreate()
        startDI()
    }

    open fun startDI() {
        startKoin {
            androidLogger()

            androidContext(this@ComicsApp)
            androidFileProperties()

            modules(AppModule().module + networkModule)
        }
    }
}