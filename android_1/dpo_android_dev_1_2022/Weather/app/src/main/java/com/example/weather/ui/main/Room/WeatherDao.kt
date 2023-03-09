package com.example.weather.ui.main.Room

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addInfo(info: DataBaseWeather)
    @Query("SELECT * FROM weather WHERE city LIKE '%' || :city || '%'")
    suspend fun getOneInfo(city: String): DataBaseWeather
    @Query("SELECT * FROM weather")
    fun getAllInfo(): Flow<List<DataBaseWeather>>
    @Update
    suspend fun updateWeather(info: DataBaseWeather)
    @Delete
    suspend fun deleteWeather(word: List<DataBaseWeather>)
}