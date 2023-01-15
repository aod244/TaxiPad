package com.example.taxipad

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class FuelAddActivity : AppCompatActivity() {

    lateinit var details: EditText
    lateinit var km: EditText
    lateinit var priceliter: EditText
    lateinit var liter: EditText

    private lateinit var sqLiteHelper: SQLiteHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fuel_add)
        supportActionBar?.hide()
        initView()
        sqLiteHelper = SQLiteHelper(this)

        val mainMenuButton = findViewById<Button>(R.id.tomenubutton2)
        val fuelAddButton = findViewById<Button>(R.id.addFuelButton)

        mainMenuButton.setOnClickListener {
            finish()
        }
        fuelAddButton.setOnClickListener {
            addFuel()
        }
    }

    private fun initView() {
        details = findViewById(R.id.fuelDetails)
        km = findViewById(R.id.fuelKm)
        priceliter = findViewById(R.id.fuelPricePerLiter)
        liter = findViewById(R.id.fuelLiter)
    }

    private fun addFuel() {
        val details = details.text.toString()
        val km = km.text.toString()
        val priceliter = priceliter.text.toString()
        val liter = liter.text.toString()
        if(details.isEmpty() || km.isEmpty() || priceliter.isEmpty() || liter.isEmpty()) {
            Toast.makeText(this, "Wpisz wszystkie potrzebne informacje!", Toast.LENGTH_SHORT).show()
        }else{
            val fuel = FuelModel(detailsfuel = details, kmfuel = km, priceliter = priceliter, liters = liter)
            val status = sqLiteHelper.addFUEL(fuel)
            if(status > -1){
                Toast.makeText(this, "Tankowanie dodane!", Toast.LENGTH_SHORT).show()
                finish()
            }else {
                Toast.makeText(this, "Blad!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}