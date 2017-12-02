package com.silentium.di.modules

import android.app.Application
import android.content.Context
import com.silentium.data.DataManager
import com.silentium.data.DataManagerImpl
import com.silentium.data.prefs.PreferencesHelper
import com.silentium.data.prefs.PreferencesHelperImpl
import com.silentium.di.qualifiers.AppContextQualifier
import com.silentium.di.qualifiers.PreferenceInfo
import com.silentium.utils.PREFS_FILE_NAME
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

    @Provides
    @PreferenceInfo
    fun providePreferenceName(): String {
        return PREFS_FILE_NAME
    }

    @Provides
    @Singleton
    fun providePreferenceHelper(preferencesHelper: PreferencesHelperImpl): PreferencesHelper {
        return preferencesHelper
    }
}