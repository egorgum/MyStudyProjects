package com.example.practice_18.data

import androidx.room.*
import com.example.practice_18.entity.UriEntity
import kotlinx.coroutines.flow.Flow
@Dao
interface UriDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addUri(uri: UriEntity)
    @Query("SELECT * FROM info LIMIT 5")
    fun getUri(): Flow<List<UriEntity>>
    @Delete
    suspend fun deleteUri(uri: List<UriEntity>)
    @Update
    suspend fun updateUri(uri: UriEntity)
}