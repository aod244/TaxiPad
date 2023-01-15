package com.example.taxipad

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FuelAdapter : RecyclerView.Adapter<FuelAdapter.FuelViewHolder>() {
    private var fuelList: ArrayList<FuelModel> = ArrayList()

    fun addItems(items:ArrayList<FuelModel>) {
        this.fuelList = items
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = FuelViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.card_items_fuel, parent, false)
            )

    override fun onBindViewHolder(holder: FuelViewHolder, position: Int) {
        val std = fuelList[position]
        holder.bindView(std)
    }

    override fun getItemCount(): Int {
        return fuelList.size
    }
    // do zmiany wyswietlanie i opcje
    class FuelViewHolder(var view: View): RecyclerView.ViewHolder(view){
        private var details = view.findViewById<TextView>(R.id.fuelDetailsTextView)
        private var liters = view.findViewById<TextView>(R.id.fuelPriceTextView)
        private var litersprice = view.findViewById<TextView>(R.id.fuelDateTextView)
        private var km = view.findViewById<TextView>(R.id.fuelKmTextView)

        fun bindView(std: FuelModel){
            details.text = "Nazwa i adres: ${std.detailsfuel}"
            liters.text = std.liters
            litersprice.text = "Cena: ${std.kmfuel}"
            km.text = "${std.priceliter} km"
        }
    }
}