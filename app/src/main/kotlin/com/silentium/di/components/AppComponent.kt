package com.silentium.di.components

import android.app.Application
import android.content.Context
import com.silentium.app.SilentiumApp
import com.silentium.data.DataManager
import com.silentium.di.modules.AppModule
import com.silentium.di.modules.DatabaseModule
import com.silentium.di.qualifiers.AppContextQualifier
import dagger.Component
import javax.inject.Singleton

/**
 * @author lusinabrian on 02/12/17.
 * @Notes App component to connect App Module to requiring classes
 */
@Singleton
@Component(modules = [AppModule::class, DatabaseModule::class])
interface AppComponent {

    /**
     * Injects dependencies into Application
     * @param silentiumApp Instance of Application class
     * */
    fun injectApp(silentiumApp: SilentiumApp)

    @AppContextQualifier
    fun context(): Context

    fun application(): Application

    /**
     * Gets the datamanager interface for the app component
     * */
    fun getDataManager(): DataManager
}