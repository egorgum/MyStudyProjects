package com.example.homework_4

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.SeekBar
import android.widget.Toast
import androidx.navigation.fragment.NavHostFragment
import com.example.homework_4.databinding.ActivityMainBinding
import kotlinx.coroutines.*
import kotlinx.coroutines.NonCancellable.cancel
import java.lang.Thread.sleep
import kotlin.concurrent.timer

const val TIME = "time"
const val BOOL = "bool"


class MainActivity : AppCompatActivity() {
    var time = 0
    var bool = false
    private val scope = CoroutineScope(Job() + Dispatchers.Main)
    private var job: Job? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        suspend fun cor() = scope.launch {
            bool = true
            binding.btStart.text = getText(R.string.restart)
            binding.seekBar.isEnabled = false
            time -= 1
            while (time > 0){
                binding.textTime.text = time.toString()
                binding.progressBar.progress = time
                time -= 1
                delay(1000)
            }
            binding.progressBar.progress = binding.progressBar.max
            binding.textTime.text =  binding.progressBar.progress.toString()
            binding.seekBar.isEnabled = true
            binding.seekBar.progress = 0
            binding.btStart.text = getText(R.string.start)
            bool = false
        }


        if (savedInstanceState != null) {
            with(savedInstanceState) {
                time = getInt(TIME)
                binding.textTime.text = time.toString()
                if (!bool){
                    binding.progressBar.progress = time
                    job = scope.launch{
                        cor()
                    }
                }

            }
        }

        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                binding.textTime.text = (p1 * 10).toString()
            }
            override fun onStartTrackingTouch(p0: SeekBar?) {
                Toast.makeText(applicationContext, "Происходит изменение времени",  Toast.LENGTH_SHORT).show()
            }
            override fun onStopTrackingTouch(p0: SeekBar?) {
                time = binding.seekBar.progress * 10
                binding.progressBar.max = time
                Toast.makeText(applicationContext, "Время выбрано",  Toast.LENGTH_SHORT).show()
                binding.progressBar.progress = binding.progressBar.max
            }
        }
        )

        binding.btStart.setOnClickListener {
            if (time > 0){
                binding.textTime.text = time.toString()
                if (binding.btStart.text == getText(R.string.start)) {
                    job = scope.launch{
                        cor()
                    }
                }
                else {
                    job?.cancel()
                    var bool = false
                    time = 0
                    binding.progressBar.progress = binding.progressBar.max
                    binding.seekBar.progress = 0
                    binding.textTime.text = getText(R.string.time)
                    binding.btStart.text = getText(R.string.start)

                }
            }
            else{Toast.makeText(applicationContext, "Ошибка",  Toast.LENGTH_SHORT).show()}
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
    super.onSaveInstanceState(outState)
    outState.putInt(TIME,time)
    outState.putBoolean(BOOL, bool)
    }

    override fun onDestroy() {
        job?.cancel()
        super.onDestroy()
    }
}
