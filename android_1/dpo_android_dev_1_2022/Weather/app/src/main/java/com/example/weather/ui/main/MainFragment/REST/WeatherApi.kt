package com.example.weather.ui.main.MainFragment.REST

import com.example.weather.ui.main.MainFragment.REST.models.Weather
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

const val API_KEY = "0a5658aa3f69e671e272e810a3eff323"
interface WeatherApi {
    @GET("/data/2.5/weather")
    suspend fun getWeather(@Query("q")q: String, @Query("units")units: String = "metric",
                           @Query("appid")appid: String = API_KEY): Weather
}
object RetrofitService {
    private const val URL = "https://api.openweathermap.org"
    private val retrofit = Retrofit.Builder()
        .baseUrl(URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
    val searchWeatherAPI: WeatherApi = retrofit.create(WeatherApi::class.java)
}