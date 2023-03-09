package com.example.practice_18.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.practice_18.entity.UriEntity

@Database(entities = [UriEntity::class], version = 2)
abstract class DataBase: RoomDatabase() {
    abstract fun dao():UriDao
}