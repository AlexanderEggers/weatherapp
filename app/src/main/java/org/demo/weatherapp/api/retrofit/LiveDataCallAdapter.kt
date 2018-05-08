package org.demo.weatherapp.api.retrofit

import android.arch.lifecycle.LiveData
import org.demo.weatherapp.api.response.NetworkResponse
import org.demo.weatherapp.api.response.NetworkResponseStatus
import org.demo.weatherapp.api.util.AppExecutor
import org.demo.weatherapp.model.WeatherModel
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type
import java.util.concurrent.atomic.AtomicBoolean

class LiveDataCallAdapter internal constructor(
        private val responseType: Type,
        private val appExecutor: AppExecutor) : CallAdapter<WeatherModel, LiveData<NetworkResponse<WeatherModel>>> {

    override fun responseType(): Type {
        return responseType
    }

    override fun adapt(call: Call<WeatherModel>): LiveData<NetworkResponse<WeatherModel>> {
        return object : LiveData<NetworkResponse<WeatherModel>>() {
            internal var started = AtomicBoolean(false)

            override fun onActive() {
                super.onActive()
                if (started.compareAndSet(false, true)) {
                    appExecutor.workerThread.execute {
                        val url = call.request().url().toString()

                        call.enqueue(object : Callback<WeatherModel> {
                            override fun onResponse(call: Call<WeatherModel>, response: Response<WeatherModel>) {
                                postValue(NetworkResponse(
                                        response.body(),
                                        url,
                                        response.code(),
                                        if (response.isSuccessful) NetworkResponseStatus.SUCCESS else NetworkResponseStatus.FAILED))
                            }

                            override fun onFailure(call: Call<WeatherModel>, throwable: Throwable) {
                                postValue(NetworkResponse(null, url, 0, NetworkResponseStatus.NETWORK_ERROR))
                            }
                        })
                    }
                }
            }
        }
    }
}
