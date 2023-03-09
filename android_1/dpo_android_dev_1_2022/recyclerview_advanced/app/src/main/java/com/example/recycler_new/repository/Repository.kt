package com.example.recycler_new.repository

import android.content.ContentValues.TAG
import android.util.Log
import com.example.recycler_new.api.RetrofitService
import com.example.recycler_new.models.CharactersList

class Repository {
    suspend fun getData(page:Int):CharactersList{
        Log.d(TAG, "Результат: ${RetrofitService.searchRickAndMortyApi.getCharacters(page = page)}")
        return RetrofitService.searchRickAndMortyApi.getCharacters(page = page)
    }
}