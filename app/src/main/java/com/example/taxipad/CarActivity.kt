package com.example.taxipad

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text
import java.math.RoundingMode

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
        val sumCar = findViewById<Button>(R.id.showCarFix)
        val sumCarRepairs = findViewById<TextView>(R.id.sumCarRepairsDone)

        recyclerView = findViewById(R.id.recyclerView3)
        initRecyclerView()
        sqLiteHelper = SQLiteHelper(this)
        val xdate = "today"
        getCar(xdate)
        getCarPrice(xdate)
        sumCarRepairs.text = adapter?.itemCount.toString()

        registerForContextMenu(sumCar)

        mainMenuButton.setOnClickListener{
            val intent = Intent(this, MenuActivity::class.java)
            finish()
        }
        carfixAddButton.setOnClickListener{
            val intent = Intent(this, CarAddActivity::class.java)
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
                val timeStamp = findViewById<TextView>(R.id.spanOfTimeViewCar)
                timeStamp.text = buildString {
                    append("bieżącego dnia ")
                }
                initRecyclerView()
                sqLiteHelper = SQLiteHelper(this)
                val xdate = "today"
                getCar(xdate)
                getCarPrice(xdate)
                val sumCar = findViewById<TextView>(R.id.sumCarRepairsDone)
                sumCar.text = adapter?.itemCount.toString()
                return true
            }

            R.id.option_2 -> {
                val timeStamp = findViewById<TextView>(R.id.spanOfTimeViewCar)
                timeStamp.text = buildString {
                    append("ostatniego tygodnia ")
                }
                initRecyclerView()
                sqLiteHelper = SQLiteHelper(this)
                val xdate = "week"
                getCar(xdate)
                getCarPrice(xdate)
                val sumCar = findViewById<TextView>(R.id.sumCarRepairsDone)
                sumCar.text = adapter?.itemCount.toString()

                return true
            }

            R.id.option_3 -> {
                val timeStamp = findViewById<TextView>(R.id.spanOfTimeViewCar)
                timeStamp.text = buildString {
                    append("biezacego miesiace ")
                }
                initRecyclerView()
                sqLiteHelper = SQLiteHelper(this)
                val xdate = "month"
                getCar(xdate)
                getCarPrice(xdate)
                val sumCar = findViewById<TextView>(R.id.sumCarRepairsDone)
                sumCar.text = adapter?.itemCount.toString()

                return true
            }

            R.id.option_4 -> {
                val timeStamp = findViewById<TextView>(R.id.spanOfTimeViewCar)
                timeStamp.text = buildString {
                    append("poprzedniego miesiąca ")
                }
                initRecyclerView()
                sqLiteHelper = SQLiteHelper(this)
                val xdate = "lastmonth"
                getCar(xdate)
                getCarPrice(xdate)
                val sumCar = findViewById<TextView>(R.id.sumCarRepairsDone)
                sumCar.text = adapter?.itemCount.toString()

                return true
            }

            R.id.option_5 -> {
                val timeStamp = findViewById<TextView>(R.id.spanOfTimeViewCar)
                timeStamp.text = buildString {
                    append("Całości ")
                }
                initRecyclerView()
                sqLiteHelper = SQLiteHelper(this)
                val xdate = "all"
                getCar(xdate)
                getCarPrice(xdate)
                val sumCar = findViewById<TextView>(R.id.sumCarRepairsDone)
                sumCar.text = adapter?.itemCount.toString()

                return true
            }
            else -> return super.onContextItemSelected(item)
        }
    }

    private fun initRecyclerView(){
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = CarAdapter()
        recyclerView.adapter = adapter
    }

    private fun getCar(xdate: String) {
        val carList = sqLiteHelper.getCARFIX(xdate)
        adapter?.addItems(carList)
    }

    private fun getCarPrice(xdate: String) {
        val sumCar = findViewById<TextView>(R.id.sumCarJobDoneView)
        val sumCarPrice = sqLiteHelper.sumALLCAR(xdate)
        val roundedSum = sumCarPrice.toBigDecimal().setScale(2, RoundingMode.UP)
        val timeSpan = findViewById<TextView>(R.id.spanOfTimeViewCar)
        timeSpan.text = buildString {
        append(" całości ")
    }
        sumCar.text = buildString {
            append(roundedSum.toString())
            append(" zł")
        }
    }
}