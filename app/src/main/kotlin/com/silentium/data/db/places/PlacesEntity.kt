package com.silentium.data.db.places

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import com.google.android.gms.maps.model.LatLng
import java.util.*

/**
 * @author lusinabrian on 02/12/17.
 * @Notes Places entity, Represents a single Place entity
 */
@Entity(tableName = "places")
data class PlacesEntity(
        @ColumnInfo(name = "id")
        @PrimaryKey(autoGenerate = false)
        var id: String,

        @ColumnInfo(name = "name")
        var name: String,

        @ColumnInfo(name = "address")
        var address: String,

        @ColumnInfo(name = "latLng")
        var latLng : LatLng
)