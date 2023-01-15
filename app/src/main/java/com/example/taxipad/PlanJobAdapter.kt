package com.example.taxipad

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PlanJobAdapter : RecyclerView.Adapter<PlanJobAdapter.PlanJobViewHolder>() {
    private var planJobList: ArrayList<PlanModel> = ArrayList()

    fun addItems(items:ArrayList<PlanModel>) {
        this.planJobList = items
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = PlanJobViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.card_items_planjob, parent, false)
    )

    override fun onBindViewHolder(holder: PlanJobViewHolder, position: Int) {
        val std = planJobList[position]
        holder.bindView(std)
    }

    override fun getItemCount(): Int {
        return planJobList.size
    }
    class PlanJobViewHolder(var view: View): RecyclerView.ViewHolder(view){
        private var start = view.findViewById<TextView>(R.id.startPlanJobTextView)
        private var date = view.findViewById<TextView>(R.id.dateTextView)
        private var price = view.findViewById<TextView>(R.id.pricePlanJobTextView)

        fun bindView(std: PlanModel){
            start.text = "Start: ${std.jobstart}"
            date.text = "Data: ${std.jobdate}"
            price.text = "${std.jobprice} zł"
        }
    }
}