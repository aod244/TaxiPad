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

        fun bindView(std: KmModel){
            startkm.text = "Km startowe: ${std.startkm}"
            endkm.text = "Km końcowe: ${std.endkm}"
            sum.text = "Suma: ${std.drivenkm} km"
        }
    }
}