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

class KilometersActivity : AppCompatActivity() {

    private lateinit var sqLiteHelper: SQLiteHelper
    private lateinit var recyclerView: RecyclerView
    private var adapter: KilometersAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kilometers)
        supportActionBar?.hide()
        val sumWorkDays = findViewById<TextView>(R.id.sumDaysWorkView)

        recyclerView = findViewById(R.id.recyclerView)
        initRecyclerView()
        sqLiteHelper = SQLiteHelper(this)
        val xdate = "today"
        getKm(xdate)
        getAllSumJobsKm(xdate)
        getAllKm(xdate)
        sumWorkDays.text = adapter?.itemCount.toString()
        importFromRecyclerKm()
        val mainMenuButton = findViewById<Button>(R.id.tomenubutton)
        val addKmButton = findViewById<Button>(R.id.addKm)
        val showKmButton = findViewById<Button>(R.id.showKm)
        registerForContextMenu(showKmButton)
        mainMenuButton.setOnClickListener{
            val intent = Intent(this, MenuActivity::class.java)
            finish()
        }
        addKmButton.setOnClickListener{
            val intent = Intent(this, KilometersAddActivity::class.java)
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
                val sumWorkDays = findViewById<TextView>(R.id.sumDaysWorkView)
                val timeStamp = findViewById<TextView>(R.id.spanOfTimeViewKM)
                timeStamp.text = buildString {
                    append("bieżącego dnia ")
                }
                initRecyclerView()
                sqLiteHelper = SQLiteHelper(this)
                val xdate = "today"
                getKm(xdate)
                getAllSumJobsKm(xdate)
                getAllKm(xdate)
                sumWorkDays.text = adapter?.itemCount.toString()
                return true
            }
            R.id.option_2 -> {
                val sumWorkDays = findViewById<TextView>(R.id.sumDaysWorkView)
                val timeStamp = findViewById<TextView>(R.id.spanOfTimeViewKM)
                timeStamp.text = buildString {
                    append("ostatniego tygodnia ")
                }
                initRecyclerView()
                sqLiteHelper = SQLiteHelper(this)
                val xdate = "week"
                getKm(xdate)
                getAllSumJobsKm(xdate)
                getAllKm(xdate)
                sumWorkDays.text = adapter?.itemCount.toString()
                return true
            }
            R.id.option_3 -> {
                val sumWorkDays = findViewById<TextView>(R.id.sumDaysWorkView)
                val timeStamp = findViewById<TextView>(R.id.spanOfTimeViewKM)
                timeStamp.text = buildString {
                    append("bieżącego miesiąca ")
                }
                initRecyclerView()
                sqLiteHelper = SQLiteHelper(this)
                val xdate = "month"
                getKm(xdate)
                getAllSumJobsKm(xdate)
                getAllKm(xdate)
                sumWorkDays.text = adapter?.itemCount.toString()
                return true
            }
            R.id.option_4 -> {
                val sumWorkDays = findViewById<TextView>(R.id.sumDaysWorkView)
                val timeStamp = findViewById<TextView>(R.id.spanOfTimeViewKM)
                timeStamp.text = buildString {
                    append("poprzedniego miesiąca ")
                }
                initRecyclerView()
                sqLiteHelper = SQLiteHelper(this)
                val xdate = "lastmonth"
                getKm(xdate)
                getAllSumJobsKm(xdate)
                getAllKm(xdate)
                sumWorkDays.text = adapter?.itemCount.toString()
                return true
            }

            R.id.option_5 -> {
                val sumWorkDays = findViewById<TextView>(R.id.sumDaysWorkView)
                val timeStamp = findViewById<TextView>(R.id.spanOfTimeViewKM)
                timeStamp.text = buildString {
                    append("całości ")
                }
                initRecyclerView()
                sqLiteHelper = SQLiteHelper(this)
                val xdate = "all"
                getKm(xdate)
                getAllSumJobsKm(xdate)
                getAllKm(xdate)
                sumWorkDays.text = adapter?.itemCount.toString()
                return true
            }

            else -> return super.onContextItemSelected(item)
        }
    }

    private fun initRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = KilometersAdapter()
        recyclerView.adapter = adapter
    }

    private fun getKm(xdate: String) {
        val kmList = sqLiteHelper.getKM(xdate)
        adapter?.addItems(kmList)
    }
    private fun getAllSumJobsKm(xdate: String) {
        val sumKM = findViewById<TextView>(R.id.sumKmDrivenJobDoneView2)
        val sum = sqLiteHelper.sumALLJOBKM(xdate)
        val timeStamp = findViewById<TextView>(R.id.spanOfTimeViewKM)
        timeStamp.text = buildString {
        append("całości ")
    }
        val roundedSum = sum.toBigDecimal().setScale(1, RoundingMode.UP)
        sumKM.text = buildString {
            append((roundedSum.toString()))
            append((" km "))
        }
    }
    private fun getAllKm(xdate: String) {
        val allKm = findViewById<TextView>(R.id.sumAllKmView)
        val sumKm = sqLiteHelper.sumALLKM(xdate)
        val roundedSum = sumKm.toBigDecimal().setScale(0, RoundingMode.UP)
        allKm.text = buildString {
            append(roundedSum.toString())
            append(" km")
        }
    }
    private fun importFromRecyclerKm() {
        sqLiteHelper = SQLiteHelper(this)
        val intentKm = intent
        val kmstart = intentKm.getStringExtra("StartKm").toString()
        val kmend = intentKm.getStringExtra("EndKm").toString()
        val sumkm = intentKm.getStringExtra("SumKm").toString()
        val datekm = intentKm.getStringExtra("DateKm").toString()
        val id = intentKm.getStringExtra("Id").toString()
        val mDialogViewKm = LayoutInflater.from(this).inflate(R.layout.edit_km_popup, null)
        val mBuilderKm = AlertDialog.Builder(this).setView(mDialogViewKm)
        val mAlertDialogKm = mBuilderKm.create()

        val editStartKm = mDialogViewKm.findViewById<EditText>(R.id.editKmStart)
        editStartKm.setText(kmstart)
        val editEndKm = mDialogViewKm.findViewById<EditText>(R.id.editKmEnd)
        editEndKm.setText(kmend)
        val editKmSum = mDialogViewKm.findViewById<EditText>(R.id.editKmSum)
        editKmSum.setText(sumkm)
        val editKmDate = mDialogViewKm.findViewById<EditText>(R.id.editKmDate)
        editKmDate.setText(datekm)

        if(id != "null"){
            mAlertDialogKm.show()
            val deleteButton = mDialogViewKm.findViewById<Button>(R.id.buttonDeleteKm)
            val cancelButton = mDialogViewKm.findViewById<Button>(R.id.buttonAbortKm)
            val editButton = mDialogViewKm.findViewById<Button>(R.id.buttonEditKm)
            deleteButton.setOnClickListener {
                sqLiteHelper = SQLiteHelper(this)
                sqLiteHelper.deleteKM(Integer.valueOf(id))
                mAlertDialogKm.dismiss()
            }
            cancelButton.setOnClickListener {
                mAlertDialogKm.dismiss()
                finish()
            }
            editButton.setOnClickListener {
                sqLiteHelper = SQLiteHelper(this)
                val intid = Integer.valueOf(id)
                val newStartKm = editStartKm.text.toString()
                val newEndKm = editEndKm.text.toString()
                val newSumKm = (Integer.valueOf(newEndKm)- Integer.valueOf(newStartKm)).toString()
                val newDateKm = editKmDate.text.toString()
                val km = KmModel(id = intid, startkm = newStartKm, endkm = newEndKm, drivenkm = newSumKm, datekm = newDateKm)
                val status = sqLiteHelper.updateKM(km)
                if(status > -1){
                    Toast.makeText(this, "Kurs zaktualizowany!", Toast.LENGTH_SHORT).show()
                    mAlertDialogKm.dismiss()
                    finish()
                }else {
                    Toast.makeText(this, "Blad!", Toast.LENGTH_SHORT).show()
                }
            }


        }else {
            mAlertDialogKm.show()
            mAlertDialogKm.dismiss()
        }

    }
}