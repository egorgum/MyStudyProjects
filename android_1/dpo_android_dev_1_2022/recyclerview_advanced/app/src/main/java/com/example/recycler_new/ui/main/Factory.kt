package com.example.recycler_new.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.recycler_new.repository.Repository

class Factory: ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)){
            return MainViewModel(Repository()) as T
        }
        throw IllegalStateException("Unknown class name")
    }
}
