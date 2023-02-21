package com.example.taxipad

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CarAdapter : RecyclerView.Adapter<CarAdapter.CarViewHolder>() {
    private var carList : ArrayList<CarModel> = ArrayList()

    fun addItems(items:ArrayList<CarModel>) {
        this.carList = items
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CarViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.card_items_car, parent, false)
    )

    override fun onBindViewHolder(holder: CarViewHolder, position: Int) {
        val std = carList[position]
        holder.bindView(std)
    }

    override fun getItemCount(): Int {
        return carList.size
    }
    class CarViewHolder(var view: View): RecyclerView.ViewHolder(view){
        private var details = view.findViewById<TextView>(R.id.fixDetailsTextView)
        private var price = view.findViewById<TextView>(R.id.fixPriceTextView)
        private var date = view.findViewById<TextView>(R.id.fixDateTextView)
        private var km = view.findViewById<TextView>(R.id.fixKmTextView)
        private var editButton = view.findViewById<Button>(R.id.editButtonCar)

        fun bindView(std: CarModel){
            details.text = buildString {
                append("Opis naprawy: ")
                append(std.fixdetails)
            }
            price.text = buildString {
                append(std.fixprice)
                append(" zł")
            }
            date.text = std.fixdate
            km.text = buildString {
                append(std.carkm)
                append(" km")
            }
            editButton.setOnClickListener {
                val intentCar = Intent(itemView.context,CarActivity::class.java)
                intentCar.putExtra("Details",std.fixdetails)
                intentCar.putExtra("Price",std.fixprice)
                intentCar.putExtra("FixDate",std.fixdate)
                intentCar.putExtra("FixKm",std.carkm)
                intentCar.putExtra("Id",std.id)
                itemView.context.startActivity(intentCar)
            }
        }
    }
}