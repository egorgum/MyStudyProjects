package com.example.practice_16.domain

import com.example.practice_16.data.UsefulActivitiesRepository
import com.example.practice_16.entity.UsefulActivity
import javax.inject.Inject

class GetUsefulActivityUseCase @Inject constructor(private val repository: UsefulActivitiesRepository) {
    suspend fun execute():  UsefulActivity{
        return repository.getUsefulActivity()
    }
}