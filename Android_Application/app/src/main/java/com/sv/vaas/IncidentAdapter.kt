package com.sv.vaas

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sv.vaas.model.Feed
import com.sv.vaas.model.locations

class IncidentAdapter(val incidents:List<Feed>):
RecyclerView.Adapter<IncidentAdapter.viewHolder>(){
    inner class viewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val time:TextView=itemView.findViewById(R.id.inci_time)
        val gforce:TextView=itemView.findViewById(R.id.gforce)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        return viewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.list_layout,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        val incident = incidents[position]
        Log.i("vaas", "onBindViewHolder: in binding")
        val incTime = incident.field4
        val incGf = incident.field3
        holder.time.text="TOI:$incTime"
        holder.gforce.text="G-force:$incGf"
        holder.itemView.setOnClickListener {
            val intent = Intent(it.context,MapsActivity::class.java)
                .putExtra("Lat",incident.field1)
                .putExtra("Long",incident.field2)
            it.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return incidents.size
    }


}