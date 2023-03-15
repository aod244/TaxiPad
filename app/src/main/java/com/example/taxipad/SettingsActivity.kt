package com.example.taxipad

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        supportActionBar?.hide()

        val menuBtn = findViewById<Button>(R.id.mainMenuButton)
        menuBtn.setOnClickListener {
            finish()
        }
    }
}