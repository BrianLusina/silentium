package com.silentium.ui.main

/**
 * @author lusinabrian on 02/12/17.
 * @Notes Main View interface
 */
interface MainView {

    /**
     * Sets the adapter to the recycler view
     * */
    fun setRecyclerAdapter()

    fun setListeners()

    /**
     * Sets values for the check boxes on the UI when the check boxes
     * */
    fun setCheckBoxPermissions()

    fun openRingerSettings()

    fun requestLocationPermissions()

    fun addNewPlace()

    fun refreshPlacesData()
}