package com.example.taxipad

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper;

class SQLiteHelper (context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "TaxiPad.db"

        // Name of tables:
        const val TABLE_NAME = "JobsDoneTable"

        // Common columns:
        const val ID = "id"
        const val DATE = "data"

        // Table 1 columns
        const val JOBSTART = "poczatek"
        const val JOBEND = "koniec"
        const val JOBKM = "ilosc"
        const val JOBPRICE = "cena"


    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = ("CREATE TABLE " + TABLE_NAME + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + JOBSTART + " TEXT," + JOBEND + " TEXT," + JOBKM + " TEXT," + JOBPRICE + " TEXT" + ")")
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun addJOB(std: JobModel): Long {
        val db = this.writableDatabase

        val contentValues = ContentValues()

        contentValues.put(JOBSTART, std.start)
        contentValues.put(JOBEND, std.end)
        contentValues.put(JOBKM, std.km)
        contentValues.put(JOBPRICE, std.price)

        val success = db.insert(TABLE_NAME, null, contentValues)
        db.close()
        return success

    }

    fun getJOB(): ArrayList<JobModel> {
        val jobList: ArrayList<JobModel> = ArrayList()
        val selectQuery = "SELECT * FROM $TABLE_NAME"
        val db = this.readableDatabase

        val cursor: Cursor?

        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: Exception) {
            e.printStackTrace()
            db.execSQL(selectQuery)
            return ArrayList()
        }

        var start: String
        var end: String
        var price: String
        var km:String

        if (cursor.moveToFirst()){
            do{
                start = cursor.getString(1)
                end = cursor.getString(2)
                price = cursor.getString(4)
                km = cursor.getString(3)

                val std = JobModel(start = start, end = end, price = price, km = km)
                jobList.add(std)
            }while (cursor.moveToNext())
        }
        return  jobList

    }
}