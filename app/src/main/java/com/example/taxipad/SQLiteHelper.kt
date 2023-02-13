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
        const val TABLE_NAME1 = "KmDrivenTable"
        const val TABLE_NAME2 = "CarMaintananceTable"
        const val TABLE_NAME3 = "FuelTable"
        const val TABLE_NAME4 = "JobPlanTable"

        // Common columns:
        const val ID = "id"
        const val DATE = "data"

        // Table 0 columns
        const val JOBSTART = "poczatek"
        const val JOBEND = "koniec"
        const val JOBKM = "ilosc"
        const val JOBPRICE = "cena"

        // Table 1 columns
        const val KMSTART = "przebiegPoczatkowy"
        const val KMEND = "przebiegKoncowy"
        const val KMDRIVEN = "przejechaneKm"

        // Table 2 columns
        const val FIXDETAILS = "opisKosztu"
        const val FIXPRICE = "cenaNaprawy"
        const val CARKM = "Przebieg"
        const val FIXDATE = "DataNaprawy"

        // Table 3 columns
        const val FUELDETAILS = "nazwaiAdresStacji"
        const val FUELKM = "przebiegPrzyTankowaniu"
        const val LITERPRICE = "cenaZaLitr"
        const val LITERS = "iloscLitrow"

        // Table 4 columns
        const val PLANJOBSTART = "adresPodjeciaKlienta"
        const val PLANJOBDATE = "dataICzasPodjecia"
        const val PLANJOBPRICE = "cenaUstalona"

    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = ("CREATE TABLE " + TABLE_NAME + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + JOBSTART + " TEXT," + JOBEND + " TEXT," + JOBKM + " TEXT," + JOBPRICE + " TEXT," + DATE + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL" + ")");
        val createTable1 = ("CREATE TABLE " + TABLE_NAME1 + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KMSTART + " TEXT," + KMEND + " TEXT," + KMDRIVEN + " TEXT," + DATE + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL" + ")");
        val createTable2 = ("CREATE TABLE " + TABLE_NAME2 + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + FIXDETAILS + " TEXT," + FIXPRICE + " TEXT," + CARKM + " TEXT," + FIXDATE + " TEXT" + ")");
        val createTable3 = ("CREATE TABLE " + TABLE_NAME3 + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + FUELDETAILS + " TEXT," + FUELKM + " TEXT," + LITERPRICE + " TEXT," + LITERS + " TEXT," + DATE + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL" + ")");
        val createTable4 = ("CREATE TABLE " + TABLE_NAME4 + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + PLANJOBSTART + " TEXT," + PLANJOBDATE + " TEXT," + PLANJOBPRICE + " TEXT" + ")");
        db.execSQL(createTable)
        db.execSQL(createTable1)
        db.execSQL(createTable2)
        db.execSQL(createTable3)
        db.execSQL(createTable4)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME2")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME1")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME3")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME4")
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

    fun sumALLJOB(): Double {
        var sumAllJobPrice: Double = 0.0
        val selectQuery = "SELECT * FROM $TABLE_NAME"
        val db = this.readableDatabase

        val cursor: Cursor?

        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: Exception) {
            e.printStackTrace()
            db.execSQL(selectQuery)
            return sumAllJobPrice
        }

        var price: String

        if(cursor.moveToFirst()){
            do{
                price = cursor.getString(4)
                val addprice = Integer.valueOf(price)
                sumAllJobPrice += addprice
            }while (cursor.moveToNext())
        }

        return sumAllJobPrice
    }

    fun sumALLJOBKM(): Double {
        var sumAllJobKM: Double = 0.0
        val selectQuery = "SELECT * FROM $TABLE_NAME"
        val db = this.readableDatabase

        val cursor: Cursor?

        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: Exception) {
            e.printStackTrace()
            db.execSQL(selectQuery)
            return sumAllJobKM
        }

        var km: String

        if(cursor.moveToFirst()){
            do{
                km = cursor.getString(3)
                val addkm = km.toDoubleOrNull()
                if (addkm != null) {
                    sumAllJobKM += addkm
                }
            }while (cursor.moveToNext())
        }

        return sumAllJobKM
    }

    fun addKM(std:KmModel): Long {
        val db = this.writableDatabase

        val contentValues = ContentValues()

        contentValues.put(KMSTART, std.startkm)
        contentValues.put(KMEND, std.endkm)
        contentValues.put(KMDRIVEN, std.drivenkm)

        val success = db.insert(TABLE_NAME1, null, contentValues)
        db.close()
        return success
    }

    fun getKM(): ArrayList<KmModel> {
        val kmList: ArrayList<KmModel> = ArrayList()
        val selectQuery = "SELECT * FROM $TABLE_NAME1"
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
        var sum: String
        var date: String

        if (cursor.moveToFirst()){
            do{
                start = cursor.getString(1)
                end = cursor.getString(2)
                sum = cursor.getString(3)
                date = cursor.getString(4)

                val std = KmModel(startkm = start, endkm = end, drivenkm = sum, datekm = date)
                kmList.add(std)
            }while (cursor.moveToNext())
        }
        return  kmList

    }
    fun sumALLKM(): Double {
        var sumAllKM: Double = 0.0
        val selectQuery = "SELECT * FROM $TABLE_NAME1"
        val db = this.readableDatabase

        val cursor: Cursor?

        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: Exception) {
            e.printStackTrace()
            db.execSQL(selectQuery)
            return sumAllKM
        }

        var km: String

        if(cursor.moveToFirst()){
            do{
                km = cursor.getString(3)
                val addkm = km.toDoubleOrNull()
                if (addkm != null) {
                    sumAllKM += addkm
                }
            }while (cursor.moveToNext())
        }

        return sumAllKM
    }

    fun addCARFIX(std: CarModel): Long {
        val db = this.writableDatabase

        val contentValues = ContentValues()

        contentValues.put(FIXDETAILS, std.fixdetails)
        contentValues.put(FIXPRICE, std.fixprice)
        contentValues.put(CARKM, std.carkm)
        contentValues.put(FIXDATE, std.fixdate)

        val success = db.insert(TABLE_NAME2, null, contentValues)
        db.close()
        return success
    }

    fun getCARFIX(): ArrayList<CarModel> {
        val fixList: ArrayList<CarModel> = ArrayList()
        val selectQuery = "SELECT * FROM $TABLE_NAME2"
        val db = this.readableDatabase

        val cursor: Cursor?

        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: Exception) {
            e.printStackTrace()
            db.execSQL(selectQuery)
            return ArrayList()
        }

        var details: String
        var price: String
        var carkm: String
        var fixdate:String

        if (cursor.moveToFirst()){
            do{
                details = cursor.getString(1)
                price = cursor.getString(2)
                carkm = cursor.getString(3)
                fixdate = cursor.getString(4)

                val std = CarModel(fixdetails = details, fixprice = price, carkm = carkm, fixdate = fixdate)
                fixList.add(std)
            }while (cursor.moveToNext())
        }
        return  fixList

    }

    fun sumALLCAR(): Double {
        var sumAllCar: Double = 0.0
        val selectQuery = "SELECT * FROM $TABLE_NAME2"
        val db = this.readableDatabase

        val cursor: Cursor?

        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: Exception) {
            e.printStackTrace()
            db.execSQL(selectQuery)
            return sumAllCar
        }

        var Price: String

        if(cursor.moveToFirst()){
            do{
                Price = cursor.getString(2)
                val addCarprice = Price.toDoubleOrNull()
                if (addCarprice != null) {
                    sumAllCar += addCarprice
                }
            }while (cursor.moveToNext())
        }

        return sumAllCar
    }

    fun addFUEL(std: FuelModel): Long {
        val db = this.writableDatabase

        val contentValues = ContentValues()

        contentValues.put(FUELDETAILS, std.detailsfuel)
        contentValues.put(FUELKM, std.kmfuel)
        contentValues.put(LITERPRICE, std.priceliter)
        contentValues.put(LITERS, std.liters)

        val success = db.insert(TABLE_NAME3, null, contentValues)
        db.close()
        return  success
    }

    fun getFUEL(): ArrayList<FuelModel> {
        val fuelList: ArrayList<FuelModel> = ArrayList()
        val selectQuery = "SELECT * FROM $TABLE_NAME3"
        val db = this.readableDatabase

        val cursor: Cursor?

        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: Exception) {
            e.printStackTrace()
            db.execSQL(selectQuery)
            return ArrayList()
        }

        var details: String
        var km: String
        var priceliter: String
        var liter:String
        var date:String

        if (cursor.moveToFirst()){
            do{
                details = cursor.getString(1)
                km = cursor.getString(2)
                priceliter = cursor.getString(3)
                liter = cursor.getString(4)
                date = cursor.getString(cursor.getColumnIndexOrThrow("data"))

                val std = FuelModel(detailsfuel = details, kmfuel = km, priceliter = priceliter, liters = liter, datefuel = date)
                fuelList.add(std)
            }while (cursor.moveToNext())
        }
        return  fuelList

    }

    fun sumALLFUEL(): Double {
        var sumAllFUEL: Double = 0.0
        val selectQuery = "SELECT * FROM $TABLE_NAME3"
        val db = this.readableDatabase

        val cursor: Cursor?

        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: Exception) {
            e.printStackTrace()
            db.execSQL(selectQuery)
            return sumAllFUEL
        }

        var price: String

        if(cursor.moveToFirst()){
            do{
                price = cursor.getString(4)
                val addFuelPrice = price.toDoubleOrNull()
                if (addFuelPrice != null) {
                    sumAllFUEL += addFuelPrice
                }
            }while (cursor.moveToNext())
        }

        return sumAllFUEL
    }

    fun sumALLFUELLITERS(): Double {
        var sumAllFUELLITERS: Double = 0.0
        val selectQuery = "SELECT * FROM $TABLE_NAME3"
        val db = this.readableDatabase

        val cursor: Cursor?

        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: Exception) {
            e.printStackTrace()
            db.execSQL(selectQuery)
            return sumAllFUELLITERS
        }

        var price: String
        var priceliter: String
        var liters: Double

        if(cursor.moveToFirst()){
            do{
                price = cursor.getString(4)
                priceliter = cursor.getString(3)
                var addFuelPrice = price.toDoubleOrNull()
                var addPriceLiter = priceliter.toDoubleOrNull()
                if (addFuelPrice != null) {
                    if (addPriceLiter != null) {
                        liters = addFuelPrice/addPriceLiter
                        sumAllFUELLITERS += liters
                    }
                }
            }while (cursor.moveToNext())
        }

        return sumAllFUELLITERS
    }

    fun avgALLFUELPRICE(): Double {
        var avgFuelPrice: Double = 0.0
        val selectQuery = "SELECT * FROM $TABLE_NAME3"
        val db = this.readableDatabase

        val cursor: Cursor?

        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: Exception) {
            e.printStackTrace()
            db.execSQL(selectQuery)
            return avgFuelPrice
        }

        var priceliter: String
        var count: Int = 0
        var pricelitersum: Double = 0.0

        if(cursor.moveToFirst()){
            do{
                priceliter = cursor.getString(3)
                count += 1
                var addpriceliter = priceliter.toDoubleOrNull()
                if (addpriceliter != null) {
                    pricelitersum += addpriceliter
                    avgFuelPrice = pricelitersum/count
                }
            }while (cursor.moveToNext())
        }
        return avgFuelPrice
    }

    fun planJOB(std: PlanModel): Long {
        val db = this.writableDatabase

        val contentValues = ContentValues()

        contentValues.put(PLANJOBSTART, std.jobstart)
        contentValues.put(PLANJOBDATE, std.jobdate)
        contentValues.put(PLANJOBPRICE, std.jobprice)

        val success = db.insert(TABLE_NAME4, null, contentValues)
        db.close()
        return success
    }

    fun getplanJOB(): ArrayList<PlanModel> {
        val planJobList: ArrayList<PlanModel> = ArrayList()
        val selectQuery = "SELECT * FROM $TABLE_NAME4"
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
        var date: String
        var price: String

        if (cursor.moveToFirst()){
            do{
                start = cursor.getString(1)
                date = cursor.getString(2)
                price = cursor.getString(3)

                val std = PlanModel(jobstart = start, jobdate = date, jobprice = price)
                planJobList.add(std)
            }while (cursor.moveToNext())
        }
        return  planJobList

    }
}