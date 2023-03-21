package com.example.taxipad

import android.content.Intent
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
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.math.RoundingMode

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
        importFromRecyclerJob()
        registerForContextMenu(showJobButton)
        mainMenuButton.setOnClickListener{
            finish()
        }
        jobAddButton.setOnClickListener{
            val intent = Intent(this, JobAddActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)

        menuInflater.inflate(R.menu.floating_context_menu, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {

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

    private fun importFromRecyclerJob() {
        sqLiteHelper = SQLiteHelper(this)
        val intent2 = intent
        val start = intent2.getStringExtra("Start").toString()
        val end = intent2.getStringExtra("End").toString()
        val price = intent2.getStringExtra("Price").toString()
        val km = intent2.getStringExtra("Km").toString()
        val datejob = intent2.getStringExtra("Date").toString()
        val id = intent2.getStringExtra("ID").toString()
        val mDialogViewJob = LayoutInflater.from(this).inflate(R.layout.edit_job_popup, null)
        val mBuilderJob = AlertDialog.Builder(this).setView(mDialogViewJob)
        val mAlertDialogJob = mBuilderJob.create()

        val editStart = mDialogViewJob.findViewById<EditText>(R.id.editStart)
        editStart.setText(start)
        val editEnd = mDialogViewJob.findViewById<EditText>(R.id.editEnd)
        editEnd.setText(end)
        val editPrice = mDialogViewJob.findViewById<EditText>(R.id.editPrice)
        editPrice.setText(price)
        val editKm = mDialogViewJob.findViewById<EditText>(R.id.EditKm)
        editKm.setText(km)
        val editDate = mDialogViewJob.findViewById<EditText>(R.id.editDate)
        editDate.setText(datejob)

        if(id != "null"){
            mAlertDialogJob.show()
            val deleteButton = mDialogViewJob.findViewById<Button>(R.id.buttonDelete)

            deleteButton.setOnClickListener {
                sqLiteHelper = SQLiteHelper(this)
                sqLiteHelper.deleteJob(Integer.valueOf(id))
                mAlertDialogJob.dismiss()
            }

            val cancelButton = mDialogViewJob.findViewById<Button>(R.id.buttonAbort)
            val editButton = mDialogViewJob.findViewById<Button>(R.id.buttonEdit)
            cancelButton.setOnClickListener {
                mAlertDialogJob.dismiss()
                finish()
            }

            editButton.setOnClickListener {
                sqLiteHelper = SQLiteHelper(this)
                val intid = Integer.valueOf(id)
                val newStart = editStart.text.toString()
                val newEnd = editEnd.text.toString()
                val newPrice = editPrice.text.toString()
                val newKm = editKm.text.toString()
                val newDate = editDate.text.toString()
                val job = JobModel(
                        ID = intid,
                        start = newStart,
                        end = newEnd,
                        price = newPrice,
                        km = newKm,
                        datejob = newDate
                )
                if(newStart.isEmpty() || newEnd.isEmpty() || newPrice.isEmpty() || newKm.isEmpty() || newDate.isEmpty()){
                    Toast.makeText(this, "Wpisz wszystkie potrzebne informacje!", Toast.LENGTH_SHORT).show()
                }else{
                    val status = sqLiteHelper.updateJOB(job)
                    if(status > -1){
                        Toast.makeText(this, "Kurs zaktualizowany!", Toast.LENGTH_SHORT).show()
                        mAlertDialogJob.dismiss()
                        finish()
                    }else {
                        Toast.makeText(this, "Blad!", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }else {
            mAlertDialogJob.show()
            mAlertDialogJob.dismiss()
        }
    }
}