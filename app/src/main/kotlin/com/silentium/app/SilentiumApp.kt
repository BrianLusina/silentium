package com.silentium.app

import android.app.Application
import android.content.Context
import android.support.multidex.MultiDex
import com.silentium.di.components.AppComponent
import com.silentium.di.components.DaggerAppComponent
import com.silentium.di.modules.AppModule
import com.silentium.di.modules.DatabaseModule

/**
 * @author lusinabrian on 02/12/17.
 * @Notes Silentium App, entry point to application
 */
class SilentiumApp : Application(){

    val appComponent : AppComponent by lazy {
        DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .databaseModule(DatabaseModule())
                .build()
    }

    override fun onCreate() {
        super.onCreate()
        appComponent.injectApp(this)
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}