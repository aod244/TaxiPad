package com.example.taxipad

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class KilometersActivity : AppCompatActivity() {

    private lateinit var sqLiteHelper: SQLiteHelper
    private lateinit var recyclerView: RecyclerView
    private var adapter: KilometersAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kilometers)
        supportActionBar?.hide()

        recyclerView = findViewById(R.id.recyclerView)
        initRecyclerView()
        sqLiteHelper = SQLiteHelper(this)

        getKm()

        val mainMenuButton = findViewById<Button>(R.id.tomenubutton)
        val addKmButton = findViewById<Button>(R.id.addKm)
        mainMenuButton.setOnClickListener{
            val intent = Intent(this, MenuActivity::class.java)
            finish()
        }
        addKmButton.setOnClickListener{
            val intent = Intent(this, KilometersAddActivity::class.java)
            startActivity(intent)
        }

    }

    private fun initRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = KilometersAdapter()
        recyclerView.adapter = adapter
    }

    private fun getKm() {
        val kmList = sqLiteHelper.getKM()
        adapter?.addItems(kmList)
    }
}