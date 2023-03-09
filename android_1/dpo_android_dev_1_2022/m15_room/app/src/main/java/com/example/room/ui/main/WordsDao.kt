package com.example.room.ui.main

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

@Dao
interface WordsDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addWord(words: Words)
    @Query("SELECT * FROM words LIMIT 5")
    fun getWords(): Flow<List<Words>>
    @Delete
    suspend fun deleteWords(word: List<Words>)
    @Update
    suspend fun updateNumber(words: Words)
}