package com.silentium.data.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.silentium.data.db.places.PlacesDao
import com.silentium.data.db.places.PlacesEntity

/**
 * @author lusinabrian on 02/12/17.
 * @Notes Application database
 */
@Database(entities = [PlacesEntity::class], version = 1, exportSchema = false)
@TypeConverters(DbConverter::class)
abstract class SilentiumDatabase : RoomDatabase(){

    abstract fun getPlacesDao() : PlacesDao
}