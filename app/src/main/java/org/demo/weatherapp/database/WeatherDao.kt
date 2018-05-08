package org.demo.weatherapp.database

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import org.demo.weatherapp.model.WeatherModel

@Dao
interface WeatherDao {

    @Query(value = "SELECT * FROM weather_data")
    fun getData(): LiveData<WeatherModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertData(weatherModel: WeatherModel)
}