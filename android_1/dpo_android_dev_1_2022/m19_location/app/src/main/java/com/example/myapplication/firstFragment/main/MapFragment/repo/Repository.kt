package com.example.myapplication.firstFragment.main.MapFragment.repo

import com.example.myapplication.firstFragment.main.MapFragment.api.RetrofitService
import com.example.myapplication.firstFragment.main.MapFragment.models.Sight


class Repository {
    suspend fun getData(): List<Sight> {
        return RetrofitService.searchTripAPI.getSights()
    }
}