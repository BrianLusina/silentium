package com.silentium.data.db.places

import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith
import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.google.android.gms.maps.model.LatLng
import com.silentium.data.db.SilentiumDatabase
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.junit.Test
import java.util.Locale


/**
 * @author lusinabrian on 02/12/17.
 * @Notes Test for [PlacesDao]
 */
@RunWith(AndroidJUnit4::class)
class PlacesDaoTest {
    lateinit var placesDao : PlacesDao
    lateinit var testDatabase : SilentiumDatabase

    @Before
    fun setUp(){
        val context = InstrumentationRegistry.getTargetContext()
        testDatabase = Room.inMemoryDatabaseBuilder(context, SilentiumDatabase::class.java).build()
        placesDao = testDatabase.getPlacesDao()
    }

    @After
    fun tearDown(){
        testDatabase.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertPlaceEntityAndRead(){
        val placeEntity = PlacesEntity(3, "Nakuru", "Milimani",
                Locale("English", "KE"), LatLng(0.0,0.0))

        placesDao.insertPlaceEntity(placeEntity)

        val placeById = placesDao.getPlaceById(0L).blockingFirst()
        assertThat(placeById, equalTo(placeEntity))
    }

}
