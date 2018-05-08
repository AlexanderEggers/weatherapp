package org.demo.weatherapp.di

import android.app.Activity
import android.os.Bundle
import org.archknife.extension.AppInjector
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherAppInjector @Inject constructor(private val contextProvider: ActivityContextProvider) : AppInjector() {

    override fun onActivityCreated(activity: Activity, bundle: Bundle?) {
        super.onActivityCreated(activity, bundle)
        setContext(activity)
    }

    override fun onActivityResumed(activity: Activity) {
        super.onActivityResumed(activity)
        setContext(activity)
    }

    private fun setContext(activity: Activity) {
        contextProvider.context = activity
    }
}
