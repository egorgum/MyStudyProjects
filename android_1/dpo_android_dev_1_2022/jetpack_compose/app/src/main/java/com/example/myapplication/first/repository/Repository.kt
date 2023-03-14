package com.example.myapplication.first.repository

import android.content.ContentValues.TAG
import android.util.Log
import com.example.myapplication.first.api.RetrofitService
import com.example.myapplication.first.models.CharactersList


class Repository {
    suspend fun getData(page:Int): CharactersList {
        Log.d(TAG, "Результат: ${RetrofitService.searchRickAndMortyApi.getCharacters(page = page)}")
        return RetrofitService.searchRickAndMortyApi.getCharacters(page = page)
    }
}