package com.example.taxipad

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*

class CarAddActivity : AppCompatActivity() {

    lateinit var cardetails: EditText
    lateinit var fixprice: EditText
    lateinit var carkm: EditText
    lateinit var fixdate: EditText

    private lateinit var sqLiteHelper: SQLiteHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_car_add)
        supportActionBar?.hide()
        initView()
        getDate()
        sqLiteHelper = SQLiteHelper(this)

        val mainMenuButton = findViewById<Button>(R.id.tomenubutton3)
        val addCarFixButton = findViewById<Button>(R.id.addCarButton)

        mainMenuButton.setOnClickListener{
            Intent(this, CarActivity::class.java)
            finish()
        }
        addCarFixButton.setOnClickListener{
            addCarFix()
        }
    }
    private fun addCarFix(){
        val details = cardetails.text.toString()
        val price = fixprice.text.toString()
        val carkm = carkm.text.toString()
        val fixdate = fixdate.text.toString()
        if(details.isEmpty() || price.isEmpty() || carkm.isEmpty() || fixdate.isEmpty()){
            Toast.makeText(this, "Wpisz wszystkie potrzebne informacje!", Toast.LENGTH_SHORT).show()
        }else{
            val car = CarModel(fixdetails = details, fixprice = price, carkm = carkm, fixdate = fixdate)
            val status = sqLiteHelper.addCARFIX(car)
            if(status > -1){
                Toast.makeText(this, "Naprawa dodana!", Toast.LENGTH_SHORT).show()
                getDate()
                finish()
            }else {
                Toast.makeText(this, "Blad!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getDate() {
        val dateNow: String = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        val date = findViewById<View>(R.id.carFixDate) as TextView
        date.text = dateNow
    }

    private fun initView() {
        cardetails = findViewById(R.id.carFixDetails)
        fixprice = findViewById(R.id.carFixPrice)
        carkm = findViewById(R.id.carFixKm)
        fixdate = findViewById(R.id.carFixDate)
    }
}
