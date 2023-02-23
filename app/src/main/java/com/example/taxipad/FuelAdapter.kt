package com.example.taxipad

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
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
        private var date = view.findViewById<TextView>(R.id.fuelDateView)
        private var editButton = view.findViewById<Button>(R.id.editButton)

        fun bindView(std: FuelModel){
            val string = std.datefuel
            val dateArray: List<String> = string.split(" ")
                details.text = buildString {
                append("Nazwa i adres: ")
                append(std.detailsfuel)
            }
            liters.text = buildString {
                append(std.kmfuel)
                append(" km")
            }
            litersprice.text = buildString {
                append("Cena: ")
                append(std.liters)
                append("zł")
            }
            km.text = buildString {
                append(std.priceliter)
                append(" zł/l")
            }
            date.text = dateArray[0]
            editButton.setOnClickListener {
                val intentFuel = Intent(itemView.context,FuelActivity::class.java)
                intentFuel.putExtra("DetailsFuel",std.detailsfuel)
                intentFuel.putExtra("KmFuel",std.kmfuel)
                intentFuel.putExtra("FuelPrice",std.liters)
                intentFuel.putExtra("PriceLiter",std.priceliter)
                intentFuel.putExtra("FuelDate",std.datefuel)
                intentFuel.putExtra("Id",(std.id).toString())
                itemView.context.startActivity(intentFuel)
            }
        }
    }
}