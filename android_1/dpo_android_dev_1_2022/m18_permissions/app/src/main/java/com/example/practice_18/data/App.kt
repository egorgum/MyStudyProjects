package com.example.practice_18.data

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
        ).addMigrations(MIGRATION_1_2)
            .build()
    }
}