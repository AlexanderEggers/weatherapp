package org.demo.weatherapp.di

import android.content.Context
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.archknife.annotation.ProvideModule
import org.demo.weatherapp.R
import org.demo.weatherapp.api.NetworkInterface
import org.demo.weatherapp.api.retrofit.LiveDataCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@ProvideModule
@Module
class NetworkModel {

    @Singleton
    @Provides
    fun provideNetworkInterface(context: Context, liveDataCallAdapterFactory: LiveDataCallAdapterFactory,
                                okHttpClient: OkHttpClient): NetworkInterface {
        return Retrofit.Builder()
                .baseUrl(context.getString(R.string.server_base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(liveDataCallAdapterFactory)
                .client(okHttpClient)
                .build()
                .create<NetworkInterface>(NetworkInterface::class.java)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()
    }
}