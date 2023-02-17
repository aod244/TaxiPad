package com.example.taxipad

import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.math.RoundingMode
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

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
        val xdate = "today"
        getJobs(xdate)
        getAllSumJobs(xdate)
        getAllSumJobsKm(xdate)
        sumJob.text = adapter?.itemCount.toString()
        val timeStamp = findViewById<TextView>(R.id.spanOfTimeView)
        timeStamp.text = buildString {
            append("bieżącego dnia ")
        }
        registerForContextMenu(showJobButton)
        mainMenuButton.setOnClickListener{
            Intent(this, MenuActivity::class.java)
            finish()
        }
        jobAddButton.setOnClickListener{
            val intent = Intent(this, JobAddActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)

        menuInflater.inflate(R.menu.floating_context_menu, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {

            R.id.option_1 -> {
                val timeStamp = findViewById<TextView>(R.id.spanOfTimeView)
                timeStamp.text = buildString {
                    append("bieżącego dnia ")
                }
                val xdate = "today"
                initSpanTimeFunction(xdate)
                return true
            }

            R.id.option_2 -> {

                val timeStamp = findViewById<TextView>(R.id.spanOfTimeView)
                timeStamp.text = buildString {
                    append("ostatniego tygodnia ")
                }
                val xdate = "week"
                initSpanTimeFunction(xdate)
                return true
            }

            R.id.option_3 -> {
                val timeStamp = findViewById<TextView>(R.id.spanOfTimeView)
                timeStamp.text = buildString {
                    append("bieżącego miesiąca ")
                }
                val xdate = "month"
                initSpanTimeFunction(xdate)
                return true
            }

            R.id.option_4 -> {
                val timeStamp = findViewById<TextView>(R.id.spanOfTimeView)
                timeStamp.text = buildString {
                    append("poprzedniego miesiąca ")
                }
                val xdate = "lastmonth"
                initSpanTimeFunction(xdate)
                return true
            }

            R.id.option_5 -> {
                val timeStamp = findViewById<TextView>(R.id.spanOfTimeView)
                timeStamp.text = buildString {
                    append("Całości ")
                }
                val xdate = "all"
                initSpanTimeFunction(xdate)
                return true
            }

            else -> return super.onContextItemSelected(item)
        }

    }

    private fun initSpanTimeFunction(xdate: String) {
        initRecyclerView()
        sqLiteHelper = SQLiteHelper(this)
        getJobs(xdate)
        getAllSumJobs(xdate)
        getAllSumJobsKm(xdate)
        val sumJob = findViewById<TextView>(R.id.sumJobDoneView)
        sumJob.text = adapter?.itemCount.toString()
    }

    private fun initRecyclerView(){
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = JobAdapter()
        recyclerView.adapter = adapter

    }

    private fun getJobs(xdate: String) {
        val jobList = sqLiteHelper.getJOB(xdate)
        adapter?.addItems(jobList)
    }
    private fun getAllSumJobs(xdate: String) {
        val sumMoney = findViewById<TextView>(R.id.sumMoneyJobDoneView)
        val sum = sqLiteHelper.sumALLJOB(xdate)
        sumMoney.text = buildString {
            append(sum.toString())
            append(" zł")
        }

    }
    private fun getAllSumJobsKm(xdate: String) {
        val sumKM = findViewById<TextView>(R.id.sumKmDrivenJobDoneView)
        val sum = sqLiteHelper.sumALLJOBKM(xdate)
        val roundedSum = sum.toBigDecimal().setScale(1, RoundingMode.UP)
        sumKM.text = buildString {
            append((roundedSum.toString()))
            append((" km "))
        }
    }
}