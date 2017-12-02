package com.silentium.data.db

import com.google.android.gms.location.places.Place
import com.silentium.data.db.places.PlacesEntity
import io.reactivex.Flowable

/**
 * @author lusinabrian on 02/12/17.
 * @Notes Db Helper interface
 */
interface DbHelper{

    fun addPlaceToDatabase(place: Place)

    /**
     * Gets places from db
     * */
    fun getPlaces() : Flowable<List<PlacesEntity>>

    /**
     * Gets places by id
     * @param placeId PLace Id for given Location
     * */
    fun getPlaceById(placeId : String) : Flowable<PlacesEntity>
}