package com.silentium.data.db.places

import android.arch.persistence.room.*
import io.reactivex.Flowable

/**
 * @author lusinabrian on 02/12/17.
 * @Notes Data access object to allow accessing and storage of objects to database over an interface
 */
@Dao
interface PlacesDao {

    // ----------------------- CREATE -------------------------
    /**
     * Inserts a Place Entity into database
     * @param placesEntity A single Place Entity
     * */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPlaceEntity(vararg placesEntity: PlacesEntity)

    // --------------------------- READ ---------------------------

    /**
     * Get Places from DB
     * */
    @Query("select * from places")
    fun getAllPlaces() : Flowable<List<PlacesEntity>>

    /**
     * Get place by id
     * @param placeId Place Id
     * */
    @Query("select * from places where id = :placeId")
    fun getPlaceById(placeId : String) : Flowable<PlacesEntity>

    // ---------------- UPDATE ------------------

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updatePlaces(vararg placesEntity: PlacesEntity)


    // --------------- DELETE --------------------

    @Delete
    fun deletePlace(placesEntity: PlacesEntity)
}