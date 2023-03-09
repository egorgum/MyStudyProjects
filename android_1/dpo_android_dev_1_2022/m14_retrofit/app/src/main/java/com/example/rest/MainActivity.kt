package com.example.rest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.rest.ui.main.MainFragment
import com.example.rest.ui.main.MainFragment.Companion.newInstance

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, newInstance())
                .commitNow()
        }
    }
}