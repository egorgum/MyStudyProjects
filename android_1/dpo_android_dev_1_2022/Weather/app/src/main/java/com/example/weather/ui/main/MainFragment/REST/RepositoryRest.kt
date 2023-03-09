package com.example.weather.ui.main.MainFragment.REST

import com.example.weather.ui.main.MainFragment.REST.models.Weather
import javax.inject.Inject

class RepositoryRest @Inject constructor() {
    suspend fun getData (q:String): Weather {
        return RetrofitService.searchWeatherAPI.getWeather(q = q)
    }
}