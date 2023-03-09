package com.example.practice_18.ui.second

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practice_18.data.UriDao
import com.example.practice_18.entity.UriEntity
import kotlinx.coroutines.launch

class SecondViewModel(private  val uriDao: UriDao) : ViewModel() {
    fun addButton(text: String,date: String) {
        viewModelScope.launch {
            uriDao.addUri(UriEntity(uri = text, date = date))
        }
    }
}