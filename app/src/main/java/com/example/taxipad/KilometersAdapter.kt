package com.example.taxipad

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class KilometersAdapter : RecyclerView.Adapter<KilometersAdapter.KilometersViewHolder>() {
    private var kmList: ArrayList<KmModel> = ArrayList()

    fun addItems(items:ArrayList<KmModel>) {
        this.kmList = items
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = KilometersViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.card_items_km, parent, false)
            )

    override fun onBindViewHolder(holder: KilometersViewHolder, position: Int) {
        val std = kmList[position]
        holder.bindView(std)
    }

    override fun getItemCount(): Int {
        return kmList.size
    }
    class KilometersViewHolder(var view: View): RecyclerView.ViewHolder(view){
        private var startkm = view.findViewById<TextView>(R.id.startKmTextView)
        private var endkm = view.findViewById<TextView>(R.id.endKmTextView)
        private var sum = view.findViewById<TextView>(R.id.kmDrivenTextView)
        private var datekm = view.findViewById<TextView>(R.id.kmDateTextView)

        fun bindView(std: KmModel){
            val string = std.datekm
            val dateArray: List<String> = string.split(" ")
            startkm.text = buildString {
                append("Km startowe: ")
                append(std.startkm)
            }
            endkm.text = buildString {
                append("Km końcowe: ")
                append(std.endkm)
            }
            sum.text = buildString {
                append("Suma: ")
                append(std.drivenkm)
                append(" km")
            }
            datekm.text = buildString {
                append("Data: ")
                append(dateArray[0])
            }

        }
    }
}