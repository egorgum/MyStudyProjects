package com.example.practice_16.data

import javax.inject.Inject


class UsefulActivitiesRepository @Inject constructor(){
    suspend fun getUsefulActivity(): UsefulActivityDto {
        return RetrofitService.searchUsefulActivityAPI.getUsefulActivity()
    }
}