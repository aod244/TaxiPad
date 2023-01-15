package com.example.taxipad

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

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
}