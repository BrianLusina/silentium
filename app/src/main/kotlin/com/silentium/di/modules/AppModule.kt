package com.silentium.di.modules

import android.app.Application
import android.content.Context
import com.silentium.data.DataManager
import com.silentium.data.DataManagerImpl
import com.silentium.di.qualifiers.AppContextQualifier
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * @author lusinabrian on 02/12/17.
 * @Notes Provides dependencies that are available for injection
 */
@Module
class AppModule(private val mApplication : Application) {

    @Provides
    @AppContextQualifier
    fun provideAppContext() : Context{
        return mApplication
    }

    @Provides
    fun provideApplication(): Application {
        return mApplication
    }

    @Provides
    @Singleton
    fun provideDataManager(dataManager: DataManagerImpl): DataManager {
        return dataManager
    }
}