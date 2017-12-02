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
class DataManagerImpl @Inject constructor(val dbHelper: DbHelper,
                                          val preferencesHelper: PreferencesHelper): DataManager{


}