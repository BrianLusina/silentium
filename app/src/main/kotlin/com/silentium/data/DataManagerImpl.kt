package com.silentium.data

import com.silentium.data.db.DbHelper
import com.silentium.data.prefs.PreferencesHelper
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author lusinabrian on 02/12/17.
 * @Notes Data manager implementation that implements interfaces for the Model layer
 */
@Singleton
class DataManagerImpl @Inject constructor(private val dbHelper: DbHelper,
                                          private val preferencesHelper: PreferencesHelper): DataManager{

    override fun getGeoFencesEnabled(): Boolean {
        return preferencesHelper.getGeoFencesEnabled()
    }

    override fun setGeoFencesEnabled(enabled: Boolean) {
        preferencesHelper.setGeoFencesEnabled(enabled)
    }
}