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
        val fuelbutton = findViewById<Button>(R.id.refuelbutton)
        fuelbutton.setOnClickListener{
            val intent = Intent(this, FuelActivity::class.java)
            startActivity(intent)
        }
        val kilometersbutton = findViewById<Button>(R.id.kilometersbutton)
        kilometersbutton.setOnClickListener{
            val intent = Intent(this, KilometersActivity::class.java)
            startActivity(intent)
        }
        val carbutton = findViewById<Button>(R.id.carmaintenancebutton)
        carbutton.setOnClickListener{
            val intent = Intent(this, CarActivity::class.java)
            startActivity(intent)
        }
        val planbtn = findViewById<Button>(R.id.plantask)
        planbtn.setOnClickListener{
            Toast.makeText(this,"Działa",Toast.LENGTH_SHORT).show()
        }


    }
}