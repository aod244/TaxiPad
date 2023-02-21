package com.example.taxipad

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.*


class JobAddActivity : AppCompatActivity() {
    //inputs
    lateinit var jobStart: EditText
    lateinit var jobEnd: EditText
    lateinit var jobKm: EditText
    lateinit var jobPrice: EditText
    lateinit var dateInput: EditText
    lateinit var sqLiteHelper: SQLiteHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_job_add)
        supportActionBar?.hide()

        sqLiteHelper = SQLiteHelper(this)
        initView()
        importFromJobPlan()

        //buttons
        val mainMenuButton = findViewById<Button>(R.id.tomenubutton3)
        val addJobButton = findViewById<Button>(R.id.addJobButton)

        mainMenuButton.setOnClickListener{
            Intent(this, MenuActivity::class.java)
            finish()
        }
        addJobButton.setOnClickListener{
            addJob()
        }
    }
    private fun addJob() {
        val start = jobStart.text.toString()
        val end = jobEnd.text.toString()
        val km  = jobKm.text.toString()
        val price = jobPrice.text.toString()
        if(start.isEmpty() || end.isEmpty() || km.isEmpty() || price.isEmpty()){
            Toast.makeText(this, "Wpisz wszystkie potrzebne informacje!", Toast.LENGTH_SHORT).show()
        }else{
            val job = JobModel(start = start, end = end, km = km, price = price)
            val status = sqLiteHelper.addJOB(job)
            if(status > -1){
                Toast.makeText(this, "Kurs dodany!", Toast.LENGTH_SHORT).show()
                clearJobView()
                finish()
            }else {
                Toast.makeText(this, "Blad!", Toast.LENGTH_SHORT).show()
            }
        }

    }
    private fun initView() {
        jobStart = findViewById(R.id.JobStartInput)
        jobEnd = findViewById(R.id.JobEndInput)
        jobKm = findViewById(R.id.JobKmInput)
        jobPrice = findViewById(R.id.JobPriceInput)
        dateInput = findViewById(R.id.DateInput)
    }

    private fun clearJobView() {
        jobStart.setText("")
        jobEnd.setText("")
        jobKm.setText("")
        jobPrice.setText("")
    }

    private fun importFromJobPlan() {
        sqLiteHelper = SQLiteHelper(this)
        val intent = intent
        val start1 = intent.getStringExtra("Start").toString()
        val date1 = intent.getStringExtra("Date").toString()
        val price1 = intent.getStringExtra("Price").toString()
        val id = intent.getIntExtra("ID", 0)
        val dateArray: List<String> = price1.split(" ")
        val success1 = intent.getStringExtra("Success").toString()
        if (success1 == "1"){
            jobStart.setText(start1)
            jobPrice.setText(dateArray[0])
            dateInput.setText(date1)
            sqLiteHelper.deletePLANJOB(id)
        }
        else{
            clearJobView()
        }
    }

}