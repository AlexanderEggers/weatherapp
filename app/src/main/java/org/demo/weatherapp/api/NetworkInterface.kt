package org.demo.weatherapp.api

import android.arch.lifecycle.LiveData
import org.demo.weatherapp.api.response.NetworkResponse
import org.demo.weatherapp.model.WeatherModel
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkInterface {

    @GET("weather")
    fun getWeatherData(
            @Query("APPID") apiKey: String,
            @Query("q") cityCountry: String = "Melbourne,au",
            @Query("units") metric: String = "metric"): LiveData<NetworkResponse<WeatherModel>>
}