package com.example.rest.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class Factory: ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)){
            return MainViewModel(Repo()) as T
        }
        throw IllegalStateException("Unknown class name")
    }
}