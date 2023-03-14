package com.example.weather.ui.main.MainFragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weather.App
import com.example.weather.ui.main.MainFragment.REST.RepositoryRest
import com.example.weather.MainViewModel
import javax.inject.Inject

class Factory@Inject constructor(): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val weatherDao = App.db.weatherDao()
        if (modelClass.isAssignableFrom(MainViewModel::class.java)){
            return MainViewModel(RepositoryRest(), weatherDao) as T
        }
        throw IllegalStateException("Unknown class name")
    }
}