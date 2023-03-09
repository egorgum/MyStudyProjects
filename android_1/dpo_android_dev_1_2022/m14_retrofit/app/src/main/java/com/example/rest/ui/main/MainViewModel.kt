package com.example.rest.ui.main

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rest.Data.User
import kotlinx.coroutines.launch

class MainViewModel(private val repo: Repo) : ViewModel() {
    private val mutableUser = MutableLiveData<User>()
    val userData: LiveData<User>
    get() = mutableUser
    fun dataIsOk(){
        viewModelScope.launch {
            try {
                val user = repo.getData()
                mutableUser.value = user
                Log.d(ContentValues.TAG, "Всё ${mutableUser.value}")

            }
            catch (t: Throwable) {
                Log.d(ContentValues.TAG, "Пришла ошибка $t")
            }
        }
    }
}