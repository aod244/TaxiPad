package com.example.taxipad

import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.os.Bundle
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.math.RoundingMode
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class JobActivity : AppCompatActivity() {

    private lateinit var sqLiteHelper: SQLiteHelper
    private lateinit var recyclerView: RecyclerView
    private var adapter: JobAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_job)
        supportActionBar?.hide()
        val mainMenuButton = findViewById<Button>(R.id.tomenubutton)
        val jobAddButton = findViewById<Button>(R.id.addJobDone)
        val showJobButton = findViewById<Button>(R.id.showJobsDone)
        val sumJob = findViewById<TextView>(R.id.sumJobDoneView)
        val mDialogView = LayoutInflater.from(this).inflate(R.layout.edit_job_popup, null)
        val deleteButton = mDialogView.findViewById<Button>(R.id.buttonDelete)


        recyclerView = findViewById(R.id.recyclerView)
        initRecyclerView()
        sqLiteHelper = SQLiteHelper(this)
        val xdate = "today"
        getJobs(xdate)
        getAllSumJobs(xdate)
        getAllSumJobsKm(xdate)
        sumJob.text = adapter?.itemCount.toString()
        val timeStamp = findViewById<TextView>(R.id.spanOfTimeView)
        timeStamp.text = buildString {
            append("bieżącego dnia ")
        }
        importFromRecycler()
        registerForContextMenu(showJobButton)
        mainMenuButton.setOnClickListener{
            Intent(this, MenuActivity::class.java)
            finish()
        }
        jobAddButton.setOnClickListener{
            val intent = Intent(this, JobAddActivity::class.java)
            startActivity(intent)
        }
        deleteButton.setOnClickListener {
            Toast.makeText(this, "Test!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)

        menuInflater.inflate(R.menu.floating_context_menu, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {

            R.id.option_1 -> {
                val timeStamp = findViewById<TextView>(R.id.spanOfTimeView)
                timeStamp.text = buildString {
                    append("bieżącego dnia ")
                }
                val xdate = "today"
                initSpanTimeFunction(xdate)
                return true
            }

            R.id.option_2 -> {

                val timeStamp = findViewById<TextView>(R.id.spanOfTimeView)
                timeStamp.text = buildString {
                    append("ostatniego tygodnia ")
                }
                val xdate = "week"
                initSpanTimeFunction(xdate)
                return true
            }

            R.id.option_3 -> {
                val timeStamp = findViewById<TextView>(R.id.spanOfTimeView)
                timeStamp.text = buildString {
                    append("bieżącego miesiąca ")
                }
                val xdate = "month"
                initSpanTimeFunction(xdate)
                return true
            }

            R.id.option_4 -> {
                val timeStamp = findViewById<TextView>(R.id.spanOfTimeView)
                timeStamp.text = buildString {
                    append("poprzedniego miesiąca ")
                }
                val xdate = "lastmonth"
                initSpanTimeFunction(xdate)
                return true
            }

            R.id.option_5 -> {
                val timeStamp = findViewById<TextView>(R.id.spanOfTimeView)
                timeStamp.text = buildString {
                    append("Całości ")
                }
                val xdate = "all"
                initSpanTimeFunction(xdate)
                return true
            }

            else -> return super.onContextItemSelected(item)
        }

    }

    private fun initSpanTimeFunction(xdate: String) {
        initRecyclerView()
        sqLiteHelper = SQLiteHelper(this)
        getJobs(xdate)
        getAllSumJobs(xdate)
        getAllSumJobsKm(xdate)
        val sumJob = findViewById<TextView>(R.id.sumJobDoneView)
        sumJob.text = adapter?.itemCount.toString()
    }

    private fun initRecyclerView(){
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = JobAdapter()
        recyclerView.adapter = adapter

    }

    private fun getJobs(xdate: String) {
        val jobList = sqLiteHelper.getJOB(xdate)
        adapter?.addItems(jobList)
    }
    private fun getAllSumJobs(xdate: String) {
        val sumMoney = findViewById<TextView>(R.id.sumMoneyJobDoneView)
        val sum = sqLiteHelper.sumALLJOB(xdate)
        sumMoney.text = buildString {
            append(sum.toString())
            append(" zł")
        }

    }
    private fun getAllSumJobsKm(xdate: String) {
        val sumKM = findViewById<TextView>(R.id.sumKmDrivenJobDoneView)
        val sum = sqLiteHelper.sumALLJOBKM(xdate)
        val roundedSum = sum.toBigDecimal().setScale(1, RoundingMode.UP)
        sumKM.text = buildString {
            append((roundedSum.toString()))
            append((" km "))
        }
    }

    private fun importFromRecycler() {
        val intent2 = intent
        val start = intent2.getStringExtra("Start").toString()
        val end = intent2.getStringExtra("End").toString()
        val price = intent2.getStringExtra("Price").toString()
        val km = intent2.getStringExtra("Km").toString()
        val datejob = intent2.getStringExtra("Date").toString()
        val id = intent2.getStringExtra("ID").toString()
        val mDialogView = LayoutInflater.from(this).inflate(R.layout.edit_job_popup, null)
        val mBuilder = AlertDialog.Builder(this).setView(mDialogView)
        val mAlertDialog = mBuilder.create()
        if(id != "null"){
            mAlertDialog.show()
            val editstart = mDialogView.findViewById<EditText>(R.id.editStart)
            editstart.hint = start
            val editend = mDialogView.findViewById<EditText>(R.id.editEnd)
            editend.hint = end
            val editkm = mDialogView.findViewById<EditText>(R.id.EditKm)
            editkm.hint = km
            val editprice = mDialogView.findViewById<EditText>(R.id.editPrice)
            editprice.hint = price
            val editdate = mDialogView.findViewById<EditText>(R.id.editDate)
            editdate.hint = datejob

            val deleteButton = mDialogView.findViewById<Button>(R.id.buttonDelete)
            deleteButton.setOnClickListener {
                sqLiteHelper.deleteJob(Integer.valueOf(id))
                mAlertDialog.dismiss()
            }
            val cancelButton = mDialogView.findViewById<Button>(R.id.buttonAbort)
            val editButton = mDialogView.findViewById<Button>(R.id.buttonEdit)
            cancelButton.setOnClickListener {
                mAlertDialog.dismiss()
            }
            editButton.setOnClickListener {
                if(start.isEmpty() || end.isEmpty() || km.isEmpty() || price.isEmpty() || datejob.isEmpty()){
                    Toast.makeText(this, "Wpisz wszystkie potrzebne informacje!", Toast.LENGTH_SHORT).show()
                }else {
                    val intid = Integer.valueOf(id)
                    val job = JobModel(
                        ID = intid,
                        start = start,
                        end = end,
                        price = price,
                        km = km,
                        datejob = datejob
                    )
                    val status = sqLiteHelper.updateJOB(job)
                    if(status > -1){
                        Toast.makeText(this, "Kurs zaktualizowany!", Toast.LENGTH_SHORT).show()
                        mAlertDialog.dismiss()
                        finish()
                    }else {
                        Toast.makeText(this, "Blad!", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }else {
            mAlertDialog.show()
            mAlertDialog.dismiss()
        }
    }
}