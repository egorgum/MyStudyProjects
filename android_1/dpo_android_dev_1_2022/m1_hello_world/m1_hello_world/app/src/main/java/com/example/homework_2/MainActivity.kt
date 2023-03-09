package com.example.homework_2

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.constraintlayout.motion.widget.Key.VISIBILITY
import com.example.homework_2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        var i = 50
        super.onCreate(savedInstanceState)
        var binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fun count(i: Int){
            if (i == 50) {
                binding.people.setTextColor(getColor(R.color.green))
                binding.people.text = getText(R.string.all)
                binding.minus.isEnabled = false
                binding.plus.isEnabled = true
            }
            if (i in 1..49) {
                binding.people.setTextColor(getColor(R.color.purple_700))
                binding.people.text = "${getText(R.string.normal)}" + i.toString()
                binding.minus.isEnabled = true
                binding.plus.isEnabled = true
            }
            if (i == 0) {
                binding.people.setTextColor(getColor(R.color.red))
                binding.people.text = getText(R.string.zero)
                binding.buttonRestart.visibility = View.VISIBLE
                binding.plus.isEnabled = false
                binding.minus.isEnabled = true
            }
        }

        binding.minus.setOnClickListener{
            i += 1
            count(i)

        }

        count(i)

        binding.plus.setOnClickListener{
            i -= 1
            count(i)
            binding.minus.isEnabled = true
        }

        binding.buttonRestart.setOnClickListener {
            i = 50
            count(i)
        }
    }

}