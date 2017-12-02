package com.silentium.data

import com.silentium.data.db.DbHelper
import com.silentium.data.prefs.PreferencesHelper

/**
 * @author lusinabrian on 02/12/17.
 * @Notes Interface that delegates data/model related tasks to the relevant data layers
 */
interface DataManager : DbHelper, PreferencesHelper{

}