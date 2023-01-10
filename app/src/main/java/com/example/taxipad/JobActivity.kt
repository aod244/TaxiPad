package com.example.taxipad

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Job

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


        recyclerView = findViewById(R.id.recyclerView)
        initRecyclerView()
        sqLiteHelper = SQLiteHelper(this)

        getJobs()

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
}