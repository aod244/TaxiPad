package com.example.taxipad

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class JobActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_job)
        supportActionBar?.hide()

    }
}