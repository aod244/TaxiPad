package com.example.taxipad

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class JobAdapter : RecyclerView.Adapter<JobAdapter.JobViewHolder>() {
    private var jobList: ArrayList<JobModel> = ArrayList()

    fun addItems(items:ArrayList<JobModel>) {
        this.jobList = items
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = JobViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.card_items_job, parent, false)
    )

    override fun onBindViewHolder(holder: JobViewHolder, position: Int) {
        val std = jobList[position]

        holder.bindView(std)
    }

    override fun getItemCount(): Int {
        return jobList.size
    }

    class JobViewHolder(var view: View): RecyclerView.ViewHolder(view){
        private var start = view.findViewById<TextView>(R.id.startTextView)
        private var end = view.findViewById<TextView>(R.id.endTextView)
        private var price = view.findViewById<TextView>(R.id.priceTextView)
        private var km = view.findViewById<TextView>(R.id.kmTextView)
        private var date = view.findViewById<TextView>(R.id.dateTextViewJobCard)
        private var editButton = view.findViewById<Button>(R.id.editButton)

        fun bindView(std: JobModel){
            val list = std.datejob.split(' ')
            start.text = std.start
            end.text = std.end
            price.text = buildString {
                append(std.price)
                append(" zł")
            }
            km.text = buildString {
                append(std.km)
                append(" km")
            }
            date.text = buildString {
                append(list[0])
            }
        }
        init {
            editButton.setOnClickListener {
                Toast.makeText(itemView.context, "Click", Toast.LENGTH_SHORT).show()
            }
        }
    }
}