package com.example.cactom.ui.main

import android.icu.text.SimpleDateFormat
import androidx.lifecycle.ViewModel
import java.util.*


val DATA_FORMAT = SimpleDateFormat("HH-mm-ss", Locale.getDefault())
class MainViewModel : ViewModel() {
    var currentTime: Long = 0
    var isStarted = false


    fun convToString():String{
        val date = currentTime
        DATA_FORMAT.timeZone = android.icu.util.TimeZone.getTimeZone("UTC+3")
        return DATA_FORMAT.format(date)

    }
}