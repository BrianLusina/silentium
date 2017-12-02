package com.silentium.ui.main

import com.google.android.gms.location.places.Places
import com.google.android.gms.location.places.internal.PlaceEntity
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

    fun updateAdapter(placesEntity: PlacesEntity)

    /**
     * Registers geo fence for place entity given its id
     * */
    fun registerGeoFenceForPlace(placesEntityId: String)

    /**
     * Displays error
     * */
    fun displayError(message: String)
}