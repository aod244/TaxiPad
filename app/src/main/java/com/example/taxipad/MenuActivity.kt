package com.example.taxipad

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.*
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MenuActivity : AppCompatActivity() {

    private lateinit var sqLiteHelper: SQLiteHelper
    private lateinit var recyclerView: RecyclerView
    private var adapter: PlanJobAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        supportActionBar?.hide()

        recyclerView = findViewById(R.id.taskRecyclerView)
        initRecyclerView()
        sqLiteHelper = SQLiteHelper(this)

        getPlanJobs()


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
            val intent = Intent(this, MenuActivityPlanJob::class.java)
            startActivity(intent)
        }
        val settings = findViewById<Button>(R.id.logoutbutton)
        settings.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initRecyclerView(){
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = PlanJobAdapter()
        recyclerView.adapter = adapter
    }

    private fun getPlanJobs() {
        val planjoblist =sqLiteHelper.getplanJOB()
        adapter?.addItems(planjoblist)
    }
}