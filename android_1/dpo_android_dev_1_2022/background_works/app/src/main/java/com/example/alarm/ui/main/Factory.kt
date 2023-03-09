package com.example.alarm.ui.main

import android.content.Context
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters

class Factory (private val lambda: suspend () -> Unit) : WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): WorkerAlarm {
        return WorkerAlarm(appContext, workerParameters)
    }
}