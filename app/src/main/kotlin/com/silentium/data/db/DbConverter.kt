package com.silentium.data.db

import android.arch.persistence.room.TypeConverter
import com.google.android.gms.maps.model.LatLng
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

/**
 * @author lusinabrian on 02/12/17.
 * @Notes DB Converter
 */
object DbConverter {

    @JvmStatic
    @TypeConverter
    fun convertLocaleFromString(value: String): Locale {
        val locale = object : TypeToken<Locale>() {}.type
        return Gson().fromJson(value, locale)
    }

    @JvmStatic
    @TypeConverter
    fun convertLocaleToString(locale: Locale): String {
        val gson = Gson()
        return gson.toJson(locale)
    }

    @JvmStatic
    @TypeConverter
    fun convertLatLngFromString(value: String): LatLng {
        val latLng = object : TypeToken<LatLng>() {}.type
        return Gson().fromJson(value, latLng)
    }

    @JvmStatic
    @TypeConverter
    fun convertLatLngToString(latLng: LatLng): String {
        val gson = Gson()
        return gson.toJson(latLng)
    }

}