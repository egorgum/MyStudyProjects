package com.example.firebasepractice.ui.main

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.firebasepractice.MainActivity
import com.example.firebasepractice.R
import com.example.firebasepractice.databinding.FragmentMainBinding
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
        private const val NOTIFICATION_ID = 2000
    }

    private lateinit var viewModel: MainViewModel

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btError.setOnClickListener{
            try {
                throw RuntimeException("Test Crash")
            }
            catch(e: RuntimeException){
                FirebaseCrashlytics.getInstance().recordException(e)
                FirebaseCrashlytics.getInstance().log("Исключение поймано")
            }
        }
        binding.btMessage.setOnClickListener{
            createNotification()
            FirebaseMessaging.getInstance().token.addOnCompleteListener {
                Log.d(TAG, "${it.result}")
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    fun createNotification(){
        val intent = Intent(requireContext(), MainActivity::class.java)
        val pendingIntent =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                PendingIntent.getActivity(requireContext(), 0, intent, PendingIntent.FLAG_IMMUTABLE)
            else
                PendingIntent.getActivity(requireContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        val notification = NotificationCompat.Builder(requireContext(),App.NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notifications)
            .setContentTitle("Письмо от приложения")
            .setContentText("Заберите сундук")
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()
        NotificationManagerCompat.from(requireContext()).notify(NOTIFICATION_ID,notification)
    }
}