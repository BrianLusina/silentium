package com.silentium.data.db

import com.google.android.gms.location.places.Place

/**
 * @author lusinabrian on 02/12/17.
 * @Notes Db Helper interface
 */
interface DbHelper{

    fun addPlaceToDatabase(place: Place)
}