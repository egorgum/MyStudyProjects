package com.example.alarm.ui.main

import android.app.PendingIntent
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.ViewModel
import androidx.work.Data
import com.example.alarm.MainActivity
import com.example.alarm.R
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.xml.transform.OutputKeys

class MainViewModel : ViewModel() {
    //Переданное значение времени из воркменеджера
    private var mutableData = MutableStateFlow<String?>(null)
    val dataString = mutableData.asStateFlow()
    //Функция для получения времени
    fun getStringData(s: String?){
        mutableData.value = s
    }
}