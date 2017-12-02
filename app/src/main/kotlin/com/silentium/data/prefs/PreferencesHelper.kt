package com.silentium.data.prefs

/**
 * @author lusinabrian on 28/03/17
 * * This interface will allow interaction with the [android.content.SharedPreferences] API
 * * This will handle all data relating to shared preferences such as settings configuration and storing user
 * * data. This will be delegated that task by [DataManager]
 */

interface PreferencesHelper {

    /***
     * Gets the geo fences enabled setting
     * */
    fun getGeoFencesEnabled() : Boolean

    /**
     * Sets the geo fences enabled setting
     * @param enabled
     * */
    fun setGeoFencesEnabled(enabled: Boolean)
}
