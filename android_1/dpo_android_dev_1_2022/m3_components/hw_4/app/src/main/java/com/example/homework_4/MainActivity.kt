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
const val MAX = "max"
const val NAME = "name"

val scope = CoroutineScope(Dispatchers.Main)

var time = 0
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState != null) {
            with(savedInstanceState) {
                time = getInt(TIME)
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
                Toast.makeText(applicationContext, "Время выбрано",  Toast.LENGTH_SHORT).show()
            }
        }
        )

        binding.btStart.setOnClickListener {
            if (time > 0){
                scope.isActive
                binding.textTime.text = time.toString()
                if (binding.btStart.text == getText(R.string.start)) {
                    binding.btStart.text = getText(R.string.restart)
                    scope.launch {
                        binding.progressBar.max = binding.seekBar.progress * 10
                        binding.progressBar.progress = binding.progressBar.max
                        binding.seekBar.isEnabled = false
                        while (time > 0){
                            binding.textTime.text = time.toString()
                            binding.progressBar.progress -= 1
                            time -= 1
                            delay(1000)
                        }
                        binding.progressBar.progress = binding.progressBar.max
                        binding.textTime.text =  binding.progressBar.progress.toString()
                        binding.seekBar.isEnabled = true
                        binding.seekBar.progress = 0
                        binding.btStart.text = getText(R.string.start)
                    }
                }
                else {
                    scope.cancel()
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
    //outState.putInt(MAX)
    //outState.putString(NAME)
    }
}
