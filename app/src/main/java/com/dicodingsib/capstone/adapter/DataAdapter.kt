package com.dicodingsib.capstone.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dicodingsib.capstone.R
import com.dicodingsib.capstone.model.Tabungan


class DataAdapter(var list:ArrayList<Tabungan>) :RecyclerView.Adapter<DataAdapter.ViewHolder>(){
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val tvKategori : TextView = itemView.findViewById(R.id.tvKategori)
        val tvDate : TextView = itemView.findViewById(R.id.tvDate)
        val tvBerat : TextView = itemView.findViewById(R.id.tvBerat)
        val tvSaldo : TextView = itemView.findViewById(R.id.tvSaldo)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_history, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvKategori.text=list[position].kategori
        holder.tvDate.text=list[position].tanggal
        holder.tvBerat.text= list[position].berat.toString()
        holder.tvSaldo.text=list[position].total.toString()

    }

    override fun getItemCount(): Int {
        return list.size
    }

}