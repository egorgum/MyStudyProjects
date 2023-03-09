package com.example.room.ui.main

import android.app.Application
import androidx.room.Room

class App: Application() {
    lateinit var db: DataBase

    override fun onCreate() {
        super.onCreate()
        db = Room.databaseBuilder(
            applicationContext,
            DataBase::class.java,
            "db"
        ).build()
    }
}