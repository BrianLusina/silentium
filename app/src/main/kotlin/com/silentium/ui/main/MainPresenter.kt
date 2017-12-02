package com.silentium.ui.main

/**
 * @author lusinabrian on 02/12/17.
 * @Notes Interface for the presenter layer
 */
interface MainPresenter<V : MainView> {

    fun onAttach(mainView: V)

    fun onResume()

    fun onDetach()

    /**
     * Enables geo fences and stores the value in a shared prefs file
     * @param enabled Boolean value to indicate whether Geo fences are enabled
     * */
    fun onEnableGeoFencesChecked(enabled : Boolean)

    fun onLocationPermissionClicked()

    fun onRingerPermissionClicked()

    fun onAddNewLocationClicked()

    fun isGeoFencesEnabled() : Boolean
}