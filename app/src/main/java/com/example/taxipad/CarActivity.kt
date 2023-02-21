package com.example.taxipad

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
        val sumCarButton = findViewById<Button>(R.id.showCarFix)
        val sumCarRepairs = findViewById<TextView>(R.id.sumCarRepairsDone)
        val mDialogViewCar = LayoutInflater.from(this).inflate(R.layout.edit_car_popup, null)
        val mBuilderCar = AlertDialog.Builder(this).setView(mDialogViewCar)
        val mAlertDialogCar = mBuilderCar.create()

        recyclerView = findViewById(R.id.recyclerView3)
        initRecyclerView()
        sqLiteHelper = SQLiteHelper(this)
        val xdate = "today"
        getCar(xdate)
        getCarPrice(xdate)
        val timeStamp = findViewById<TextView>(R.id.spanOfTimeViewCar)
        timeStamp.text = buildString {
            append("bieżącego dnia ")
        }
        sumCarRepairs.text = adapter?.itemCount.toString()
        importFromRecyclerCar()
        registerForContextMenu(sumCarButton)
        mainMenuButton.setOnClickListener{
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

    private fun importFromRecyclerCar() {
        sqLiteHelper = SQLiteHelper(this)
        val intentCar = intent
        val FixDetails = intentCar.getStringExtra("Details")
        val FixPrice = intentCar.getStringExtra("Price")
        val FixDate = intentCar.getStringExtra("FixDate")
        val FixKm = intentCar.getStringExtra("FixKm")
        val id = intentCar.getStringExtra("ID").toString()
        val mDialogViewCar = LayoutInflater.from(this).inflate(R.layout.edit_car_popup, null)
        val mBuilderCar = AlertDialog.Builder(this).setView(mDialogViewCar)
        val mAlertDialogCar = mBuilderCar.create()
        val editDetails = mDialogViewCar.findViewById<EditText>(R.id.editDetails)
        editDetails.setText(FixDetails)
        val editPrice = mDialogViewCar.findViewById<EditText>(R.id.editPriceCar)
        editPrice.setText(FixPrice)
        val editDate = mDialogViewCar.findViewById<EditText>(R.id.editDateCar)
        editDate.setText(FixDate)
        val editFixKm = mDialogViewCar.findViewById<EditText>(R.id.editKmCar)
        editFixKm.setText(FixKm)

        if(id != "null"){
            mAlertDialogCar.show()
            val deleteButton = mDialogViewCar.findViewById<Button>(R.id.buttonDeleteCar)
            val cancelButton = mDialogViewCar.findViewById<Button>(R.id.buttonAbortCar)
            val editButton = mDialogViewCar.findViewById<Button>(R.id.buttonEditCar)

            deleteButton.setOnClickListener {
                sqLiteHelper = SQLiteHelper(this)
                sqLiteHelper.deleteCARFIX(Integer.valueOf(id))
                mAlertDialogCar.dismiss()
            }

            cancelButton.setOnClickListener {
                mAlertDialogCar.dismiss()
                finish()
            }

            editButton.setOnClickListener {
                sqLiteHelper = SQLiteHelper(this)
                val intid = Integer.valueOf(id)
                val newDetails = editDetails.text.toString()
                val newPrice = editPrice.text.toString()
                val newDate = editDate.text.toString()
                val newKm = editFixKm.text.toString()
                val car = CarModel(id = intid, fixdetails = newDetails, fixprice = newPrice, fixdate = newDate, carkm = newKm)
                val status = sqLiteHelper.updateCARFIX(car)
                if(status > -1){
                    Toast.makeText(this, "Kurs zaktualizowany!", Toast.LENGTH_SHORT).show()
                    mAlertDialogCar.dismiss()
                    finish()
                }else {
                    Toast.makeText(this, "Blad!", Toast.LENGTH_SHORT).show()
                }
            }

        }else {
            mAlertDialogCar.show()
            mAlertDialogCar.dismiss()
        }
    }
}