package com.example.alarm.ui.main

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.os.IBinder
import android.widget.Toast
import com.example.alarm.R

class MyAlarmService : Service() {
    override fun onCreate() {
        Toast.makeText(this, "MyAlarmService.onCreate()",
            Toast.LENGTH_LONG)
            .show()
    }
    override fun onBind(intent: Intent?): IBinder? {
        Toast.makeText(this, "MyAlarmService.onBind()",
            Toast.LENGTH_LONG)
            .show()
        return null
    }
    override fun onDestroy() {
        super.onDestroy()
        Toast.makeText(this, "MyAlarmService.onDestroy()",
            Toast.LENGTH_LONG)
            .show()
    }
    override fun onStartCommand(intent: Intent?, flags: Int, startId:
    Int): Int {
        Toast.makeText(
            this,
            "MyAlarmService.onStartCommand()",
            Toast.LENGTH_LONG
        )
            .show()
        val ringURI =
            RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE)
        try {
            val mp = MediaPlayer.create(this, R.raw.bema)
            mp.start()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return super.onStartCommand(intent, flags, startId)
    }
    override fun onUnbind(intent: Intent?): Boolean {
        Toast.makeText(this, "MyAlarmService.onUnbind()",
            Toast.LENGTH_LONG)
            .show()
        return super.onUnbind(intent)
    }
}