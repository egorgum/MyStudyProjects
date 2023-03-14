package com.example.weather.ui.main.Room

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "weather", primaryKeys = ["city","temp"])
data class DataBaseWeather(
    @ColumnInfo(name = "city")
    val city: String,
    @ColumnInfo(name = "temp")
    val temp: Double,
    @ColumnInfo(name = "feels")
    val feels: Double,
    @ColumnInfo(name = "description")
    val description: String,
    @ColumnInfo(name = "windSpeed")
    val windSpeed: Double,
)