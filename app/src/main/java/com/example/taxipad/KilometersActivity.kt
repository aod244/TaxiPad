package com.example.taxipad

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.math.RoundingMode

class KilometersActivity : AppCompatActivity() {

    private lateinit var sqLiteHelper: SQLiteHelper
    private lateinit var recyclerView: RecyclerView
    private var adapter: KilometersAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kilometers)
        supportActionBar?.hide()
        val sumWorkDays = findViewById<TextView>(R.id.sumDaysWorkView)

        recyclerView = findViewById(R.id.recyclerView)
        initRecyclerView()
        sqLiteHelper = SQLiteHelper(this)
        getKm()
        getAllSumJobsKm()
        getAllKm()
        sumWorkDays.text = adapter?.itemCount.toString()

        val mainMenuButton = findViewById<Button>(R.id.tomenubutton)
        val addKmButton = findViewById<Button>(R.id.addKm)
        mainMenuButton.setOnClickListener{
            val intent = Intent(this, MenuActivity::class.java)
            finish()
        }
        addKmButton.setOnClickListener{
            val intent = Intent(this, KilometersAddActivity::class.java)
            startActivity(intent)
        }

    }

    private fun initRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = KilometersAdapter()
        recyclerView.adapter = adapter
    }

    private fun getKm() {
        val kmList = sqLiteHelper.getKM()
        adapter?.addItems(kmList)
    }
    private fun getAllSumJobsKm() {
        val sumKM = findViewById<TextView>(R.id.sumKmDrivenJobDoneView2)
        val sum = sqLiteHelper.sumALLJOBKM()
        val timeStamp = findViewById<TextView>(R.id.spanOfTimeViewKM)
        timeStamp.text = buildString {
        append("całości ")
    }
        val roundedSum = sum.toBigDecimal().setScale(1, RoundingMode.UP)
        sumKM.text = buildString {
            append((roundedSum.toString()))
            append((" km "))
        }
    }
    private fun getAllKm() {
        val allKm = findViewById<TextView>(R.id.sumAllKmView)
        val sumKm = sqLiteHelper.sumALLKM()
        val roundedSum = sumKm.toBigDecimal().setScale(0, RoundingMode.UP)
        allKm.text = buildString {
            append(roundedSum.toString())
            append(" km")
        }
    }
}