package com.silentium.data.db.places

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * @author lusinabrian on 02/12/17.
 * @Notes Places entity, Represents a single Place entity
 */
@Entity(tableName = "places")
data class PlacesEntity(
        @ColumnInfo(name = "id")
        @PrimaryKey(autoGenerate = true)
        val id: Long)