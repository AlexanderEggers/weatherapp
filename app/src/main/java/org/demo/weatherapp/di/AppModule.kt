package org.demo.weatherapp.di

import android.app.Application
import android.arch.persistence.room.Room
import android.content.Context
import archknife.annotation.ProvideModule
import dagger.Module
import dagger.Provides
import org.demo.weatherapp.database.AppDatabase
import org.demo.weatherapp.database.WeatherDao
import javax.inject.Singleton

@ProvideModule
@Module
class AppModule {

    @Singleton
    @Provides
    fun provideAppContext(app: Application): Context {
        return app.applicationContext
    }

    @Singleton
    @Provides
    fun provideAppDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "weather-database")
                .fallbackToDestructiveMigration()
                .build()
    }

    @Singleton
    @Provides
    fun provideWeatherDao(appDatabase: AppDatabase): WeatherDao {
        return appDatabase.weatherDao()
    }
}