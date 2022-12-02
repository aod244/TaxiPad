package com.example.taxipad

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.*
import android.widget.Toast

class MenuActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        supportActionBar?.hide()


        val jobsdonebutton = findViewById<Button>(R.id.jobsdonebutton)
        jobsdonebutton.setOnClickListener{
            val intent = Intent(this, JobActivity::class.java)
            startActivity(intent)
        }
        val planbtn = findViewById<Button>(R.id.plantask)
        planbtn.setOnClickListener{
            Toast.makeText(this,"Działa",Toast.LENGTH_SHORT).show()
        }


    }
}