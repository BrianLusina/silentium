package com.silentium.ui.main

import com.google.android.gms.location.places.Place

/**
 * @author lusinabrian on 02/12/17.
 * @Notes Interface for the presenter layer
 */
interface MainPresenter<V : MainView> {

    fun onAttach(mainView: V)

    fun onResume()

    fun onDetach()

    /**
     * Callback to add Place to DB
     * @param place Place Item
     * */
    fun onAddPlaceToDatabase(place : Place)

    /**
     * Enables geo fences and stores the value in a shared prefs file
     * @param enabled Boolean value to indicate whether Geo fences are enabled
     * */
    fun onEnableGeoFencesChecked(enabled : Boolean)

    fun onLocationPermissionClicked()

    fun onRingerPermissionClicked()

    fun onAddNewLocationClicked()

    fun isGeoFencesEnabled() : Boolean

    /**
     * Gets places from the database
     * */
    fun onGetPlaces()

    fun onGetPlaceById(placeId : String)
}