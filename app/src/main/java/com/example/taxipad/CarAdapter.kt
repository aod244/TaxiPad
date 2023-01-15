package com.example.taxipad

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

        fun bindView(std: CarModel){
            details.text = "Opis naprawy: ${std.fixdetails}"
            price.text = "${std.fixprice} zł"
            date.text = std.fixdate
            km.text = "${std.carkm} km"
        }
    }
}