package org.demo.weatherapp.api.util

import android.app.AlarmManager
import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CacheController @Inject constructor(context: Context) {

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(
            "org.demo.weatherapp", Context.MODE_PRIVATE)

    fun isCacheTooOld(): Boolean {
        val cachedTime = sharedPreferences.getLong("CACHED_DATA_TIME_STAMP", 0)
        val currentTime = System.currentTimeMillis()

        return currentTime - cachedTime >= AlarmManager.INTERVAL_HOUR
    }

    fun updateCacheTime() {
        sharedPreferences.edit()
                .putLong("CACHED_DATA_TIME_STAMP", System.currentTimeMillis())
                .apply()
    }
}