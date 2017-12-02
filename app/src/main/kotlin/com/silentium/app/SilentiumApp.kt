package com.silentium.app

import android.app.Application
import android.content.Context

/**
 * @author lusinabrian on 02/12/17.
 * @Notes Silentium App, entry point to application
 */
class SilentiumApp : Application(){

    override fun onCreate() {
        super.onCreate()
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)

    }
}