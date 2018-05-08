package org.demo.weatherapp.api

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.support.annotation.MainThread
import org.demo.weatherapp.BuildConfig
import org.demo.weatherapp.api.response.NetworkResponse
import org.demo.weatherapp.api.util.AppExecutor
import org.demo.weatherapp.api.util.CacheController
import org.demo.weatherapp.database.WeatherDao
import org.demo.weatherapp.model.WeatherModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherRepository @Inject constructor(private val appExecutor: AppExecutor,
                                            private val networkInterface: NetworkInterface,
                                            private val weatherDao: WeatherDao,
                                            private val cacheController: CacheController) {

    private val result = MediatorLiveData<WeatherModel>()

    @MainThread
    fun getWeatherData(): LiveData<WeatherModel> {
        val testSource = loadCache()
        result.addSource(testSource) { data ->
            result.removeSource(testSource)

            if (data == null || cacheController.isCacheTooOld()) {
                fetchFromNetwork()
            } else {
                establishConnection()
            }
        }

        return result
    }

    @MainThread
    private fun fetchFromNetwork() {
        val networkResponse = networkInterface.getWeatherData(BuildConfig.apiKey)

        executeCall(networkResponse)
    }

    private fun executeCall(apiResponse: LiveData<NetworkResponse<WeatherModel>>) {
        result.addSource(apiResponse) { response ->
            result.removeSource(apiResponse)

            appExecutor.workerThread.execute {
                if (response!!.isSuccessful() && response.body != null) {
                    weatherDao.insertData(response.body)
                    cacheController.updateCacheTime()
                }
                establishConnection()

            }
        }
    }

    @MainThread
    private fun establishConnection() {
        appExecutor.mainThread.execute({
            val resultSource = loadCache()
            result.addSource(resultSource) { data -> result.value = data }
        })
    }

    @MainThread
    private fun loadCache(): LiveData<WeatherModel> {
        return weatherDao.getData()
    }
}