package com.example.practice_16.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.practice_16.data.UsefulActivitiesRepository
import com.example.practice_16.domain.GetUsefulActivityUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

class MainViewModelFactory @Inject constructor(private val mainViewModel: MainViewModel) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)){
            return mainViewModel as T
        }
        throw IllegalStateException("Unknown class name")
    }
}