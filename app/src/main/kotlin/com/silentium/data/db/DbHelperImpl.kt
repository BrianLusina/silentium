package com.silentium.data.db

import com.google.android.gms.location.places.Place
import com.silentium.data.db.places.PlacesDao
import com.silentium.data.db.places.PlacesEntity
import io.reactivex.Flowable
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author lusinabrian on 02/12/17.
 * @Notes Db Helper implementation
 */
@Singleton
class DbHelperImpl @Inject constructor(private val placesDao: PlacesDao) : DbHelper{

    override fun addPlaceToDatabase(place: Place) {
        val placeEntity = PlacesEntity(
                place.id.toLong(),
                place.name.toString(),
                place.address.toString(),
                place.locale,
                place.latLng
        )
        placesDao.insertPlaceEntity(placeEntity)
    }

    override fun getPlaces(): Flowable<List<PlacesEntity>> {
        return placesDao.getAllPlaces()
    }
}