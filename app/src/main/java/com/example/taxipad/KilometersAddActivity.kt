package com.example.taxipad

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class KilometersAddActivity : AppCompatActivity() {

    lateinit var startkm: EditText
    lateinit var endkm: EditText
    lateinit var kmdate: EditText
    lateinit var drivenkm: TextView

    private lateinit var sqLiteHelper: SQLiteHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kilometers_add)
        supportActionBar?.hide()
        initView()
        sqLiteHelper = SQLiteHelper(this)

        val mainMenuButton = findViewById<Button>(R.id.tomenubutton4)
        val addKmButton = findViewById<Button>(R.id.addKmButton)
        val sumButton = findViewById<Button>(R.id.sumKmButton)

        sumButton.setOnClickListener{
            sumKM()
        }
        mainMenuButton.setOnClickListener{
            Intent(this, KilometersActivity::class.java)
            finish()
        }
        addKmButton.setOnClickListener{
            addKm()
        }
    }

    private fun addKm(){
        val start = startkm.text.toString()
        val end = endkm.text.toString()
        val sum = drivenkm.text.toString()
        val date = kmdate.text.toString()
        if(start.isEmpty() || end.isEmpty() || sum.isEmpty() || date.isEmpty()){
            Toast.makeText(this, "Wpisz wszystkie potrzebne informacje!", Toast.LENGTH_SHORT).show()
        }else{
            val km = KmModel(startkm = start, endkm = end, drivenkm = sum, datekm = date)
            val status = sqLiteHelper.addKM(km)
            if(status > -1){
                Toast.makeText(this, "Przebieg dodany!", Toast.LENGTH_SHORT).show()
                finish()
            }else {
                Toast.makeText(this, "Blad!", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun initView() {
        startkm = findViewById(R.id.kmStartInput)
        endkm = findViewById(R.id.kmEndInput)
        drivenkm = findViewById(R.id.kmSumInput)
        kmdate = findViewById(R.id.kmDateInput)
    }

    private fun sumKM(){
        val start = startkm.text
        val end = endkm.text
        if(start.isNotEmpty() || end.isNotEmpty()){
            val start1 = Integer.valueOf(startkm.text.toString())
            val end1 = Integer.valueOf(endkm.text.toString())
            val sum = (end1 - start1)
            drivenkm.text = sum.toString()
        }
    }
}