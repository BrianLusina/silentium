package com.silentium.di.components

import com.silentium.di.modules.ActivityModule
import com.silentium.di.scope.ActivityScope
import com.silentium.ui.main.MainActivity
import dagger.Component

/**
 * @author lusinabrian on 02/12/17.
 * @Notes Injects dependencies from Module to requiring classes
 */
@ActivityScope
@Component(modules = [ActivityModule::class], dependencies = [AppComponent::class])
interface ActivityComponent {

    /**
     * Injects dependencies into the Main Activity
     * @param mainActivity Instance of the Main Activity
     * */
    fun injectMain(mainActivity: MainActivity)
}