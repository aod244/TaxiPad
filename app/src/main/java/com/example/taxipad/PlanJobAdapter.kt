package com.example.taxipad

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
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

        init {
        }

        fun bindView(std: PlanModel){
            start.text = std.jobstart
            date.text = std.jobdate
            price.text = buildString {
                append(std.jobprice)
                append(" zł")
            }
            itemView.findViewById<Button>(R.id.jobDoneButton).setOnClickListener { v ->
                val context: Context = v.context
                val intent = Intent(context, JobAddActivity::class.java)
                val start = start.text
                val date = date.text
                val price = price.text
                val stringid = (std.id)
                val success = "1"
                intent.putExtra("Start",start)
                intent.putExtra("Date",date)
                intent.putExtra("Price",price)
                intent.putExtra("Success",success)
                intent.putExtra("ID",stringid)
                context.startActivity(intent)
            }
        }

    }

}