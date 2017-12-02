package com.silentium.ui.main

import com.google.android.gms.location.places.Places
import com.silentium.data.db.places.PlacesEntity

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

    fun updateAdapter(placesList : List<PlacesEntity>)

    /**
     * Displays error
     * */
    fun displayError(message: String)
}