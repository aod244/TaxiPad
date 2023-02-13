package com.example.taxipad

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.math.RoundingMode

// Zastanowic sie na wprowadzaniem danych
class FuelActivity : AppCompatActivity() {

    private lateinit var sqLiteHelper: SQLiteHelper
    private lateinit var recyclerView: RecyclerView
    private var adapter: FuelAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fuel)
        supportActionBar?.hide()

        val mainMenuButton = findViewById<Button>(R.id.tomenubutton)
        val addFuelButton = findViewById<Button>(R.id.addFuel)
        val showButton = findViewById<Button>(R.id.showFuel)

        recyclerView = findViewById(R.id.recyclerView)
        initRecyclerView()
        sqLiteHelper = SQLiteHelper(this)
        getFuel()
        getAllFuel()
        getAllFuelLitters()
        avgAllFuelPrice()


        mainMenuButton.setOnClickListener{
            finish()
        }
        addFuelButton.setOnClickListener {
            val intent = Intent(this,FuelAddActivity::class.java)
            startActivity(intent)
        }
        showButton.setOnClickListener {
            getFuel()
        }

    }

    private fun initRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = FuelAdapter()
        recyclerView.adapter = adapter
    }

    private fun getFuel() {
        val fuelList = sqLiteHelper.getFUEL()
        adapter?.addItems(fuelList)
    }

    private fun getAllFuel() {
        val sumFuel = findViewById<TextView>(R.id.sumFuelCost)
        val sumFuelPrice = sqLiteHelper.sumALLFUEL()
        val roundedSum = sumFuelPrice.toBigDecimal().setScale(2, RoundingMode.UP)
        val timeSpan = findViewById<TextView>(R.id.spanOfTimeViewFuel)
        timeSpan.text = " całości "
        sumFuel.text = buildString {
            append(roundedSum.toString())
            append(" zł ")
        }
    }

    private fun getAllFuelLitters() {
        val sumLitters = findViewById<TextView>(R.id.sumFuelLiters)
        val sumFuelLitters = sqLiteHelper.sumALLFUELLITERS()
        val roundedSum = sumFuelLitters.toBigDecimal().setScale(2, RoundingMode.UP)
        sumLitters.text = buildString {
            append(roundedSum.toString())
            append(" l")
        }
    }

    private fun avgAllFuelPrice() {
        val avgPrice = findViewById<TextView>(R.id.averagePriceFuelLiter)
        val avgLitersPrice = sqLiteHelper.avgALLFUELPRICE()
        val roundedSum = avgLitersPrice.toBigDecimal().setScale(2, RoundingMode.UP)
        avgPrice.text = buildString {
            append(roundedSum.toString())
            append(" zł")
        }
    }
}