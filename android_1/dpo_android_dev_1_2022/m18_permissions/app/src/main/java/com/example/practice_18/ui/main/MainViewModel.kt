package com.example.practice_18.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practice_18.data.UriDao
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(private  val uriDao: UriDao) : ViewModel() {
    val allUri = this.uriDao.getUri().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = emptyList()
    )
    fun onDelete(){
        viewModelScope.launch{
            uriDao.deleteUri(allUri.value)
        }
    }

}