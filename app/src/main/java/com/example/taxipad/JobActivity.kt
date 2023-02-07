package com.example.taxipad

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Job
import java.math.RoundingMode

class JobActivity : AppCompatActivity() {

    private lateinit var sqLiteHelper: SQLiteHelper
    private lateinit var recyclerView: RecyclerView
    private var adapter: JobAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_job)
        supportActionBar?.hide()
        val mainMenuButton = findViewById<Button>(R.id.tomenubutton)
        val jobAddButton = findViewById<Button>(R.id.addJobDone)
        val showJobButton = findViewById<Button>(R.id.showJobsDone)
        val sumJob = findViewById<TextView>(R.id.sumJobDoneView)


        recyclerView = findViewById(R.id.recyclerView)
        initRecyclerView()
        sqLiteHelper = SQLiteHelper(this)
        getJobs()
        getAllSumJobs()
        getAllSumJobsKm()
        sumJob.text = adapter?.itemCount.toString()


        mainMenuButton.setOnClickListener{
            Intent(this, MenuActivity::class.java)
            finish()
        }
        jobAddButton.setOnClickListener{
            val intent = Intent(this, JobAddActivity::class.java)
            startActivity(intent)
        }
        showJobButton.setOnClickListener{
            initRecyclerView()
            getJobs()
            getAllSumJobsKm()
            sumJob.text = adapter?.itemCount.toString()
        }
    }

    private fun initRecyclerView(){
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = JobAdapter()
        recyclerView.adapter = adapter

    }

    private fun getJobs() {
        val jobList = sqLiteHelper.getJOB()
        adapter?.addItems(jobList)
    }
    private fun getAllSumJobs() {
        val sumMoney = findViewById<TextView>(R.id.sumMoneyJobDoneView)
        val timeStamp = findViewById<TextView>(R.id.spanOfTimeView)
        val sum = sqLiteHelper.sumALLJOB()
        timeStamp.text = "Całości "
        sumMoney.text = buildString {
            append(sum.toString())
            append(" zł")
        }

    }
    private fun getAllSumJobsKm() {
        val sumKM = findViewById<TextView>(R.id.sumKmDrivenJobDoneView)
        val sum = sqLiteHelper.sumALLJOBKM()
        val roundedSum = sum.toBigDecimal().setScale(1, RoundingMode.UP)
        sumKM.text = buildString {
            append((roundedSum.toString()))
            append((" km"))
        }
    }
}