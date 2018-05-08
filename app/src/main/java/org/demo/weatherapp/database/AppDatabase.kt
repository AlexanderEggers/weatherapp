package org.demo.weatherapp.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters

import org.demo.weatherapp.model.WeatherModel

@Database(entities = [(WeatherModel::class)], version = 1)
@TypeConverters(Converter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
}
