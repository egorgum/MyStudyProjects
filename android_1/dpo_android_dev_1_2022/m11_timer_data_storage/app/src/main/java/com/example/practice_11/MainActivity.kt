package com.example.practice_11

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.practice_11.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val repo = Repository(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvResult.text = repo.getText()

        binding.btSave.setOnClickListener{
            repo.saveText(binding.putData.text.toString())
            binding.tvResult.text = repo.getText()
        }

        binding.btClear.setOnClickListener{
            repo.clearText()
            binding.tvResult.text = repo.getText()
        }


    }
}