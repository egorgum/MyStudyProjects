package com.example.weather


import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather.ui.main.MainFragment.REST.models.Weather
import com.example.weather.ui.main.MainFragment.REST.RepositoryRest
import com.example.weather.ui.main.Room.DataBaseWeather
import com.example.weather.ui.main.Room.WeatherDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor (private val repo: RepositoryRest, private val weatherDao: WeatherDao) : ViewModel() {

    var tempREST: MutableStateFlow<Weather?> = MutableStateFlow(null)
    var tempROOM: MutableStateFlow<DataBaseWeather?> = MutableStateFlow(null)
    var bundle: MutableStateFlow<Bundle?> = MutableStateFlow(null)
    var flag: MutableStateFlow<Boolean?> = MutableStateFlow(null)
    val allWeather = this.weatherDao.getAllInfo().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = emptyList()
    )

    suspend fun loadWeather(q: String){
            try {
                getBundleRest(q)
                Log.d(TAG, "REST: ${tempREST.value}")
            }
            catch (t: Throwable) {
                Log.d(TAG, "ошибка REST: $t")
                getBundleRoom(q)
            }
    }

    private fun addWeather(weather: Weather) {
        viewModelScope.launch {
            weatherDao.addInfo(DataBaseWeather(
                city = weather.name, temp =  weather.main.temp,
                description = weather.weather.first().description, feels = weather.main.feels_like,
                windSpeed = weather.wind.speed))
        }
        allWeather.value.forEach {
            if (it.city != weather.name){
                viewModelScope.launch {
                    weatherDao.addInfo(DataBaseWeather(
                        city = weather.name, temp =  weather.main.temp,
                        description = weather.weather.first().description, feels = weather.main.feels_like,
                        windSpeed = weather.wind.speed))
                }
            }
            if (it.city == weather.name && it.temp != weather.main.temp){
                viewModelScope.launch {
                    weatherDao.updateWeather(it.copy(temp =  weather.main.temp,
                        description = weather.weather.first().description, feels = weather.main.feels_like,
                        windSpeed = weather.wind.speed))
                }
            }
        }
    }

    suspend fun getOneWeather(name: String){
        tempROOM.value = weatherDao.getOneInfo(name)
        Log.d(TAG, "ROOM:${tempREST.value}")
    }
    suspend fun getBundleRest(text: String){
        tempREST.value = repo.getData(q = text)
        addWeather(tempREST.value!!)
        val b = Bundle().apply {
            with(tempREST.value!!){
                putString("cityName", this.name)
                putFloat("temp", this.main.temp.toFloat())
                putFloat("feels", this.main.feels_like.toFloat())
                putString("description", this.weather.first().description)
                putFloat("windSpeed", this.wind.speed.toFloat())
            }
        }
        bundle.value = b
        flag.value = true
    }

    fun onDelete(){
        viewModelScope.launch{
            weatherDao.deleteWeather(allWeather.value)
        }
    }

    private suspend fun getBundleRoom(text: String){
        try {
            Log.d(TAG, "start ROOM")
            getOneWeather(text.uppercase().trim())
            val b = Bundle().apply {
                with(tempROOM.value!!){
                    putString("cityName", this.city)
                    putFloat("temp", this.temp.toFloat())
                    putFloat("feels", this.feels.toFloat())
                    putString("description", this.description)
                    putFloat("windSpeed", this.windSpeed.toFloat())
                }
            }
            bundle.value = b
            flag.value = true
            Log.d(TAG, "end ROOM")

        }
        catch (e: Exception){
            bundle.value = null
            flag.value = false
            Log.d(TAG, "error ROOM: $e")
        }
    }
}