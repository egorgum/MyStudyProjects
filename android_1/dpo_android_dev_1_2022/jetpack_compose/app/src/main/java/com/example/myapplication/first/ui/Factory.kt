package com.example.myapplication.first.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.first.repository.Repository

class Factory: ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListViewModel::class.java)){
            return ListViewModel(Repository()) as T
        }
        throw IllegalStateException("Unknown class name")
    }
}
