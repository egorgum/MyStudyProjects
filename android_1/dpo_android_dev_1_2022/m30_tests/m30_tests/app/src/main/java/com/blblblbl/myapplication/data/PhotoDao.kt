package com.blblblbl.myapplication.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PhotoDao {

    @Query("SELECT * FROM photos_db")
    fun getAll(): Flow<List<Photo>>

    @Query("SELECT * FROM photos_db")
    fun getAllNotFLow(): List<Photo>

    @Insert
    suspend fun insert(photo: Photo)
    @Delete
    suspend fun delete(photo: Photo)

    @Query("DELETE FROM photos_db")
    suspend fun clear()
}