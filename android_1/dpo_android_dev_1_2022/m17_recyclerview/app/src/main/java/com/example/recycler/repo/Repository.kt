package com.example.recycler.repo

import com.example.recycler.api.RetrofitService
import com.example.recycler.models.PhotosList

class Repository {
    suspend fun getData(page: Int): PhotosList {
        return RetrofitService.searchNasaAPI.getPhotos(page = page, sol = 1000)
    }
}