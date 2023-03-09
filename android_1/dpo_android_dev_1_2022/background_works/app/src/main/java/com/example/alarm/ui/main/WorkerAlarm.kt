package com.example.alarm.ui.main

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.icu.util.TimeZone
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.view.ContentInfoCompat.Flags
import androidx.work.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.shredzone.commons.suncalc.SunTimes
import java.util.*

const val WORK_RESULT_KEY = "OutputKey"

const val WORK_INPUT_TIME_KEY = "InputTimeKey"
const val WORK_INPUT_LNG_KEY = "InputLNGKey"
const val WORK_INPUT_LAT_KEY = "InputLATKey"
const val WORK_INPUT_TIMEZONE_KEY = "InputTimeZoneKey"
const val INTENT_OUTPUT = "IntentOutput"

class WorkerAlarm(context: Context, workersParams: WorkerParameters): Worker(context,workersParams) {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun doWork(): Result {
        try{
            val scope = CoroutineScope(Dispatchers.IO)
            scope.launch {
                val job = scope.launch{
                    //Передача времени во вьюмодель
                    setAlarm(location().getLong(WORK_RESULT_KEY, 0))
                }
                job.join()
                job.cancel()
            }
            return Result.success()
        } catch (e: Exception){
            Log.d(TAG, "Ошибка воркера: $e")
            return Result.failure()
        }
    }
    //Вычисление времени для будильника
    @RequiresApi(Build.VERSION_CODES.O)
    fun location(): Data {
        val date = Calendar.getInstance()
        date.timeInMillis = System.currentTimeMillis()
//        date.add(Calendar.MILLISECOND, 10)

        Log.d(TAG, "Данное время: ${inputData.getString(WORK_INPUT_TIME_KEY)?.toLong()}")//3600000
        val timeSet = inputData.getString(WORK_INPUT_TIME_KEY)?.toLong()//binding.etTime.text.toString().toLong() ?: 0
        Log.d(TAG, "Широта: ${inputData.getString(WORK_INPUT_LAT_KEY)!!.toDouble()}")
        val lat = inputData.getString(WORK_INPUT_LAT_KEY)?.toDouble()//'binding.etLat.text.toString().toDouble()//55.7522
        Log.d(TAG, "Долгота: ${inputData.getString(WORK_INPUT_LNG_KEY)?.toDouble()}")
        val lng = inputData.getString(WORK_INPUT_LNG_KEY)?.toDouble()//binding.etLng.text.toString().toDouble()//36.6156
        Log.d(TAG, "Зона: ${inputData.getString(WORK_INPUT_TIMEZONE_KEY)}")
        DATA_FORMAT.timeZone = TimeZone.getTimeZone("${inputData.getString(WORK_INPUT_TIMEZONE_KEY)}")//binding.etTimeZone.text}")
        val times: SunTimes = SunTimes.compute().on(date).at(lat!!, lng!!).execute()
        val rDate: Long = Date.from(times.rise?.toInstant()).time.plus(timeSet!!)
        Log.d(TAG, "Время: $rDate")
        return workDataOf(WORK_RESULT_KEY to rDate)
    }
    private fun setAlarm(time: Long){
        val myIntent = Intent(
            applicationContext,
            MyAlarmService::class.java
        )
        val mPendingIntent = PendingIntent.getService(
            applicationContext, 0,
            myIntent,   PendingIntent.FLAG_IMMUTABLE)
        val alarmManager = applicationContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager?
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = time
        alarmManager!![AlarmManager.RTC, calendar.timeInMillis] = mPendingIntent

    }
}