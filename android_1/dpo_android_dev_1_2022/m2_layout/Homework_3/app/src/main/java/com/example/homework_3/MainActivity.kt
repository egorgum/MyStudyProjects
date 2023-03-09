package com.example.homework_3


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.homework_3.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.customView.myView.up.text = "верхняя строчка, настроенная из кода"
        binding.customView.myView.down.text = "нижняя строчка, настроенная из кода"

    }
}