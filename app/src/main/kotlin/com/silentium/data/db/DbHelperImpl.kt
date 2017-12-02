package com.silentium.data.db

import com.silentium.data.db.places.PlacesDao
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author lusinabrian on 02/12/17.
 * @Notes Db Helper implementation
 */
@Singleton
class DbHelperImpl @Inject constructor(val placesDao: PlacesDao) : DbHelper{
}