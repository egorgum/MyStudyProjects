package com.example.alarm.ui.main


import android.app.AlarmManager
import android.app.Application
import android.app.PendingIntent
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.work.*
import com.example.alarm.MainActivity
import com.example.alarm.R
import com.example.alarm.databinding.FragmentMainBinding
import java.util.Calendar


val DATA_FORMAT = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
const val NOTIFICATION_ID = 2000
class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }
    private lateinit var binding: FragmentMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeState()

        fun startWorker(): String {
            val alarmWorkerBuilder = OneTimeWorkRequestBuilder<WorkerAlarm>()
            val data = workDataOf(
                WORK_INPUT_TIME_KEY to binding.etTime.text.toString(),
                WORK_INPUT_LNG_KEY to binding.etLng.text.toString(),
                WORK_INPUT_LAT_KEY to binding.etLat.text.toString(),
                WORK_INPUT_TIMEZONE_KEY to binding.etTimeZone.text.toString()
            )
            alarmWorkerBuilder.setInputData(data)
            val workManager = WorkManager.getInstance(Application())
            val continuation = workManager.beginUniqueWork(
                "UNIQUE_WORK_NAME",
                ExistingWorkPolicy.REPLACE, alarmWorkerBuilder.build(),
            )
            val res = continuation.enqueue().result.get().toString()
            viewModel.getStringData(res)
            return res
        }

        binding.btSave.setOnClickListener{
            try {
                startWorker()
                Log.d(TAG, startWorker())
            }
            catch (e: Exception){
                Toast.makeText(activity, "Error: $e", Toast.LENGTH_LONG).show()
            }
        }


    }

    private fun createNotification(t: String?){
        val intent = Intent(requireContext(), MainActivity::class.java)
        val pendingIntent =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                PendingIntent.getActivity(requireContext(), 0, intent, PendingIntent.FLAG_IMMUTABLE)
            else
                PendingIntent.getActivity(requireContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        val notification = NotificationCompat.Builder(requireContext(),App.NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_alarm)
            .setContentTitle("Письмо от приложения Alarm")
            .setContentText(t)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()
        NotificationManagerCompat.from(requireContext()).notify(NOTIFICATION_ID,notification)
    }

    private fun observeState(){
        viewModel.dataString.asLiveData().observe(viewLifecycleOwner){
            if (it != null){
                createNotification(it)
                viewModel.getStringData(null)
            }
        }
    }
}
