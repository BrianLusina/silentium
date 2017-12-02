package com.silentium.data.prefs

import android.content.Context
import android.content.SharedPreferences

import com.silentium.di.qualifiers.PreferenceInfo
import com.silentium.di.qualifiers.AppContextQualifier

import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author lusinabrian on 28/03/17
 * * Implementation of [PreferencesHelper] whose responsibility is to ensure that the application prefs
 * * are handled accordingly. This is a singleton thus there will be only one reference to it.
 */

@Singleton
class PreferencesHelperImpl @Inject
constructor(@AppContextQualifier context: Context, @PreferenceInfo prefFilename: String) : PreferencesHelper {

    private val keyGeoFencesEnabled = "keyGeoFencesEnabled"

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(prefFilename, Context.MODE_PRIVATE)

    /**
     * Saves data to shared preferences
     * this is an extension function that
     * @param key key to use to save data
     * @param value the value to use to save data
     * extension functions that allows the saving of data to a shared preference file
     * */
    private fun <T> SharedPreferences.saveData(key: String, value: T) {
        when (value) {
            is String -> this.edit().putString(key, value.toString()).apply()
            is Boolean -> this.edit().putBoolean(key, value).apply()
            is Int -> this.edit().putInt(key, value.toInt()).apply()
            is Float -> this.edit().putFloat(key, value.toFloat()).apply()
            is Long -> this.edit().putLong(key, value.toLong()).apply()
        }
    }

    override fun getGeoFencesEnabled(): Boolean {
        return sharedPreferences.getBoolean(keyGeoFencesEnabled, false)
    }

    override fun setGeoFencesEnabled(enabled: Boolean) {
        sharedPreferences.saveData(keyGeoFencesEnabled, enabled)
    }
}
