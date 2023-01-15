package com.example.taxipad

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MenuActivityPlanJob : AppCompatActivity() {

    lateinit var start: EditText
    lateinit var date: EditText
    lateinit var price: EditText

    private lateinit var sqLiteHelper: SQLiteHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_plan_job)
        supportActionBar?.hide()
        initView()

        val toMainMenuButton = findViewById<Button>(R.id.tomenubutton5)
        val addPlanJobButton = findViewById<Button>(R.id.addPlanJobButton)
        sqLiteHelper = SQLiteHelper(this)

        toMainMenuButton.setOnClickListener {
            finish()
        }
        addPlanJobButton.setOnClickListener {
            planJob()
        }
    }

    private fun planJob() {
        val start = start.text.toString()
        val date = date.text.toString()
        val price = price.text.toString()
        if(start.isEmpty() || date.isEmpty()){
            Toast.makeText(this, "Wpisz wszystkie potrzebne informacje!", Toast.LENGTH_SHORT).show()
        }else{
            val planJob = PlanModel(jobstart = start, jobdate = date, jobprice = price)
            val status = sqLiteHelper.planJOB(planJob)
            if(status > -1){
                Toast.makeText(this, "Kurs zaplanowany!", Toast.LENGTH_SHORT).show()
                finish()
            }else {
                Toast.makeText(this, "Blad!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun initView() {
        start = findViewById(R.id.planJobStartInput)
        date = findViewById(R.id.planJobStartInput2)
        price = findViewById(R.id.planJobStartInput3)
    }
}