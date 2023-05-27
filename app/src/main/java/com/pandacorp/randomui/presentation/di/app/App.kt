package com.pandacorp.randomui.presentation.di.app

import android.app.Application
import com.pandacorp.randomui.R
import com.pandacorp.randomui.presentation.di.modules.randomModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App : Application() {
    var selectedNavigationItemId = R.id.nav_one

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@App)
            modules(listOf(randomModule))
        }
    }
}