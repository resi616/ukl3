package com.example.ukl3

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

class TransaksiAdapter (private val transaksiList: List<TransaksiModel>) : RecyclerView.Adapter<TransaksiAdapter.ViewHolder>() {

    private val selectedTransactions: MutableList<TransaksiModel> = mutableListOf()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTransaksiMenu: TextView = itemView.findViewById(R.id.tvTransaksiMenu)
        val tvMetodePembayaran: TextView = itemView.findViewById(R.id.tvMetodePembayaran)
        val tvTanggalTransaksi: TextView = itemView.findViewById(R.id.tvTanggalTransaksi)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.listtransaksi, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount() = transaksiList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = transaksiList[position]
        holder.tvTransaksiMenu.text = currentItem.menu
        holder.tvMetodePembayaran.text = currentItem.selectedPayment
        holder.tvTanggalTransaksi.text = currentItem.tanggal


    }
}