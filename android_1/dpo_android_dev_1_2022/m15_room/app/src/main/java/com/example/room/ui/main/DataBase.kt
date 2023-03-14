package com.example.room.ui.main

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Words::class], version = 1)
abstract class DataBase: RoomDatabase() {
    abstract fun wordsDao(): WordsDao
}