package com.silentium.di.modules

import android.arch.persistence.room.Room
import android.content.Context
import com.silentium.data.db.DbHelper
import com.silentium.data.db.DbHelperImpl
import com.silentium.data.db.places.PlacesDao
import com.silentium.data.db.SilentiumDatabase
import com.silentium.di.qualifiers.AppContextQualifier
import com.silentium.di.qualifiers.DatabaseInfo
import com.silentium.utils.DATABASE_NAME
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * @author lusinabrian on 02/12/17.
 * @Notes Provides dependencies related to the database
 */
@Module
class DatabaseModule {

    @Provides
    fun provideDbHelper(dbHelper: DbHelperImpl) : DbHelper {
        return dbHelper
    }

    @Provides
    @DatabaseInfo
    fun provideDatabaseName(): String {
        return DATABASE_NAME
    }

    @Provides
    @Singleton
    fun provideDatabase(@AppContextQualifier context: Context): SilentiumDatabase {
        return Room.databaseBuilder(context, SilentiumDatabase::class.java, DATABASE_NAME).build()
    }

    @Provides
    fun providePlacesDao(database: SilentiumDatabase): PlacesDao {
        return database.getPlacesDao()
    }


}