package com.example.health_track

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class AktivAdapter(private  var Aktiv: List<Aktiv> ):
        RecyclerView.Adapter<AktivAdapter.AktivHolder>() {

    class AktivHolder(view: View): RecyclerView.ViewHolder(view){
        val gymnastic : TextView = view.findViewById(R.id.gymnastic)
        val amount : TextView = view.findViewById(R.id.amount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AktivHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activ_layout,parent,false)
        return AktivHolder(view)
    }

    override fun onBindViewHolder(holder: AktivHolder, position: Int) {
        val aktivity = Aktiv[position]
        val context = holder.amount.context

        if (aktivity.amount < 0){

            holder.amount.text = "- %.2f Ккал".format(Math.abs(aktivity.amount))
            holder.amount.setTextColor(ContextCompat.getColor(context,R.color.green))
        }else{
            holder.amount.text = "+ %.2f Ккал".format(aktivity.amount)
            holder.amount.setTextColor(ContextCompat.getColor(context,R.color.red))
        }

        holder.gymnastic.text = aktivity.label
        
        holder.itemView.setOnClickListener { 
            val intent = Intent(context, DetailedActivity::class.java)
            intent.putExtra("aktivity",aktivity)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return Aktiv.size
    }
    fun setData(Aktiv: List<Aktiv>){
        this.Aktiv = Aktiv
        notifyDataSetChanged()

    }

}