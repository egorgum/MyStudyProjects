package com.example.weather

import android.app.Application
import androidx.room.Room
import com.example.weather.ui.main.Room.DataBase
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App: Application() {

    override fun onCreate() {
        super.onCreate()
        db = Room.databaseBuilder(
            applicationContext,
            DataBase::class.java,
            "db"
        ).build()
    }
    companion object{
        lateinit var db: DataBase
    }
}