package com.example.taxipad

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class CarActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_car)
        supportActionBar?.hide()

        val mainMenuButton = findViewById<Button>(R.id.tomenubutton)
        mainMenuButton.setOnClickListener{
            val intent = Intent(this, MenuActivity::class.java)
            finish()
        }

    }
}