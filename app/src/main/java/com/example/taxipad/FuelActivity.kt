package com.example.taxipad

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.math.RoundingMode
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class FuelActivity : AppCompatActivity() {

    private lateinit var sqLiteHelper: SQLiteHelper
    private lateinit var recyclerView: RecyclerView
    private var adapter: FuelAdapter? = null
    private val formatter = DateTimeFormatter.ofPattern("yyyy-MM")
    private val current = LocalDateTime.now().format(formatter)

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
        val xdate = "today"
        getFuel(xdate)
        getAllFuel(xdate)
        getAllFuelLitters(xdate)
        avgAllFuelPrice(xdate)

        registerForContextMenu(showButton)

        mainMenuButton.setOnClickListener{
            finish()
        }
        addFuelButton.setOnClickListener {
            val intent = Intent(this,FuelAddActivity::class.java)
            startActivity(intent)
        }

    }
    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)

        menuInflater.inflate(R.menu.floating_context_menu, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {

            R.id.option_1 -> {
                Toast.makeText(applicationContext, "Opcja 1", Toast.LENGTH_SHORT).show()
                val timeStamp = findViewById<TextView>(R.id.spanOfTimeViewFuel)
                timeStamp.text = buildString {
                    append("bieżącego dnia ")
                }
                initRecyclerView()
                sqLiteHelper = SQLiteHelper(this)
                val xdate = "today"
                getFuel(xdate)
                getAllFuel(xdate)
                getAllFuelLitters(xdate)
                avgAllFuelPrice(xdate)
                return true
            }

            R.id.option_2 -> {
                Toast.makeText(applicationContext, "Opcja 2", Toast.LENGTH_SHORT).show()
                val timeStamp = findViewById<TextView>(R.id.spanOfTimeViewFuel)
                timeStamp.text = buildString {
                    append("bieżącego dnia ")
                }
                initRecyclerView()
                sqLiteHelper = SQLiteHelper(this)
                val xdate = "week"
                getFuel(xdate)
                getAllFuel(xdate)
                getAllFuelLitters(xdate)
                avgAllFuelPrice(xdate)
                return true
            }

            R.id.option_3 -> {
                Toast.makeText(applicationContext, "Opcja 3", Toast.LENGTH_SHORT).show()
                val timeStamp = findViewById<TextView>(R.id.spanOfTimeViewFuel)
                timeStamp.text = buildString {
                    append("bieżącego dnia ")
                }
                initRecyclerView()
                sqLiteHelper = SQLiteHelper(this)
                val xdate = "month"
                getFuel(xdate)
                getAllFuel(xdate)
                getAllFuelLitters(xdate)
                avgAllFuelPrice(xdate)
                return true
            }

            R.id.option_4 -> {
                Toast.makeText(applicationContext, "Opcja 4", Toast.LENGTH_SHORT).show()
                val timeStamp = findViewById<TextView>(R.id.spanOfTimeViewFuel)
                timeStamp.text = buildString {
                    append("bieżącego dnia ")
                }
                initRecyclerView()
                sqLiteHelper = SQLiteHelper(this)
                val xdate = "lastmonth"
                getFuel(xdate)
                getAllFuel(xdate)
                getAllFuelLitters(xdate)
                avgAllFuelPrice(xdate)
                return true
            }

            R.id.option_5 -> {
                Toast.makeText(applicationContext, "Opcja 5", Toast.LENGTH_SHORT).show()
                val timeStamp = findViewById<TextView>(R.id.spanOfTimeViewFuel)
                timeStamp.text = buildString {
                    append("całości ")
                }
                initRecyclerView()
                sqLiteHelper = SQLiteHelper(this)
                val xdate = "all"
                getFuel(xdate)
                getAllFuel(xdate)
                getAllFuelLitters(xdate)
                avgAllFuelPrice(xdate)
                return true
            }

            else -> return super.onContextItemSelected(item)
        }

    }

    private fun initRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = FuelAdapter()
        recyclerView.adapter = adapter
    }

    private fun getFuel(xdate: String) {
        val fuelList = sqLiteHelper.getFUEL(xdate)
        adapter?.addItems(fuelList)
    }

    private fun getAllFuel(xdate: String) {
        val sumFuel = findViewById<TextView>(R.id.sumFuelCost)
        val sumFuelPrice = sqLiteHelper.sumALLFUEL(xdate)
        val roundedSum = sumFuelPrice.toBigDecimal().setScale(2, RoundingMode.UP)
        val timeSpan = findViewById<TextView>(R.id.spanOfTimeViewFuel)
        timeSpan.text = " całości "
        sumFuel.text = buildString {
            append(roundedSum.toString())
            append(" zł ")
        }
    }

    private fun getAllFuelLitters(xdate: String) {
        val sumLitters = findViewById<TextView>(R.id.sumFuelLiters)
        val sumFuelLitters = sqLiteHelper.sumALLFUELLITERS(xdate)
        val roundedSum = sumFuelLitters.toBigDecimal().setScale(2, RoundingMode.UP)
        sumLitters.text = buildString {
            append(roundedSum.toString())
            append(" l")
        }
    }

    private fun avgAllFuelPrice(xdate: String) {
        val avgPrice = findViewById<TextView>(R.id.averagePriceFuelLiter)
        val avgLitersPrice = sqLiteHelper.avgALLFUELPRICE(xdate)
        val roundedSum = avgLitersPrice.toBigDecimal().setScale(2, RoundingMode.UP)
        avgPrice.text = buildString {
            append(roundedSum.toString())
            append(" zł")
        }
    }
}