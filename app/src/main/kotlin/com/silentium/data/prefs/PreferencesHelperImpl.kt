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

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(prefFilename, Context.MODE_PRIVATE)

    companion object {
        // key used to determine if this is the first start of the application
        private val PREF_KEY_FIRST_START = "PREF_KEY_FIRST_START"
    }

}
