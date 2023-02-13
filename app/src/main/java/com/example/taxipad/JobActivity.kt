package com.example.taxipad

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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

        registerForContextMenu(showJobButton)

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

    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)

        menuInflater.inflate(R.menu.floating_context_menu, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {

            R.id.option_1 -> {
                Toast.makeText(applicationContext, "Opcja 1", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MenuActivity::class.java)
                startActivity(intent)
                return true
            }

            R.id.option_2 -> {
                Toast.makeText(applicationContext, "Opcja 2", Toast.LENGTH_SHORT).show()
                return true
            }

            R.id.option_3 -> {
                Toast.makeText(applicationContext, "Opcja 3", Toast.LENGTH_SHORT).show()
                return true
            }

            R.id.option_4 -> {
                Toast.makeText(applicationContext, "Opcja 4", Toast.LENGTH_SHORT).show()
                return true
            }

            else -> return super.onContextItemSelected(item)
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
            append((" km "))
        }
    }
}