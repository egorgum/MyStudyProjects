package com.example.recycler.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.recycler.repo.Repository

class Factory: ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)){
            return MainViewModel(Repository()) as T
        }
        throw IllegalStateException("Unknown class name")
    }
}