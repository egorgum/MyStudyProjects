package com.example.weather.ui.main.Room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [DataBaseWeather::class], version = 1)
abstract class DataBase: RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
}