package com.sielehub.treasuremart

import android.app.Application
import com.sielehub.treasuremart.di.AppModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(
                level = Level.DEBUG
            )
            androidContext(this@App)
            modules(
                AppModules.appModule,
                AppModules.networkModule,
                AppModules.authModule,
                AppModules.productsModule,
                AppModules.wishModule,
                AppModules.cartModule,
                AppModules.ordersModule,
                AppModules.notificationModule
            )
        }
    }
}