package com.silentium.data.db

import com.google.android.gms.location.places.Place
import com.silentium.data.db.places.PlacesDao
import com.silentium.data.db.places.PlacesEntity
import io.reactivex.Flowable
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.info
import org.jetbrains.anko.onComplete
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author lusinabrian on 02/12/17.
 * @Notes Db Helper implementation
 */
@Singleton
class DbHelperImpl @Inject constructor(private val placesDao: PlacesDao) : DbHelper, AnkoLogger{

    override fun addPlaceToDatabase(place: Place) {
        val placeEntity = PlacesEntity(
                place.id,
                place.name.toString(),
                place.address.toString(),
                place.latLng
        )

        // add to database on a separate thread
        doAsync {
            placesDao.insertPlaceEntity(placeEntity)
        }
    }

    override fun getPlaces(): Flowable<List<PlacesEntity>> {
        return placesDao.getAllPlaces()
    }

    override fun getPlaceById(placeId: String): Flowable<PlacesEntity> {
        return placesDao.getPlaceById(placeId)
    }
}