package com.dicodingsib.capstone.adapter

import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dicodingsib.capstone.R
import com.dicodingsib.capstone.model.Tabungan
import com.dicodingsib.capstone.utility.Extensions.rupiahFormat


class DataAdapter(var list:ArrayList<Tabungan>) :RecyclerView.Adapter<DataAdapter.ViewHolder>(){
    private lateinit var onItemClickCallback: OnItemClickCallback
    private lateinit var image: Image

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val btnDelete: ImageView = itemView.findViewById(R.id.btn_delete)
        val tvKategori : TextView = itemView.findViewById(R.id.tvKategori)
        val tvDate : TextView = itemView.findViewById(R.id.tvDate)
        val tvBerat : TextView = itemView.findViewById(R.id.tvBerat)
        val tvSaldo : TextView = itemView.findViewById(R.id.tvSaldo)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_history, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvKategori.text="Sampah "+list[position].kategori
        holder.tvDate.text=list[position].tanggal
        holder.tvBerat.text= "Berat : "+list[position].berat.toString()+" Kg"
        holder.tvSaldo.text="Pendapatan : "+ rupiahFormat(list[position].total!!)

        holder.btnDelete.setOnClickListener { onItemClickCallback.onItemClicked(list[position]) }


    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Tabungan)
    }
}
