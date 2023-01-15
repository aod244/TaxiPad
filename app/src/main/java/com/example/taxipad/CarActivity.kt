package com.example.taxipad

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CarActivity : AppCompatActivity() {

    private lateinit var sqLiteHelper: SQLiteHelper
    private lateinit var recyclerView: RecyclerView
    private var adapter: CarAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_car)
        supportActionBar?.hide()

        val mainMenuButton = findViewById<Button>(R.id.tomenubutton)
        val carfixAddButton = findViewById<Button>(R.id.addCarFixButton)

        recyclerView = findViewById(R.id.recyclerView3)
        initRecyclerView()
        sqLiteHelper = SQLiteHelper(this)

        getCar()

        mainMenuButton.setOnClickListener{
            val intent = Intent(this, MenuActivity::class.java)
            finish()
        }
        carfixAddButton.setOnClickListener{
            val intent = Intent(this, CarAddActivity::class.java)
            startActivity(intent)
        }

    }

    private fun initRecyclerView(){
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = CarAdapter()
        recyclerView.adapter = adapter
    }

    private fun getCar() {
        val carList = sqLiteHelper.getCARFIX()
        adapter?.addItems(carList)
    }
}