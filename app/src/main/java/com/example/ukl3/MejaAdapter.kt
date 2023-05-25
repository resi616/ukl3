package com.example.ukl3

import android.text.style.ClickableSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class MejaAdapter (private val mejaList: ArrayList<MejaModel>) : RecyclerView.Adapter<MejaAdapter.ViewHolder>(){

    private lateinit var mListener: onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(clickListener: onItemClickListener){
        mListener = clickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.listmeja, parent, false)
        return ViewHolder(itemView, mListener)
    }

    override fun onBindViewHolder(holder: MejaAdapter.ViewHolder, position: Int) {
        val currentMeja = mejaList[position]
        holder.tvMejaName.text = currentMeja.mejaName
    }

    override fun getItemCount(): Int {
        return mejaList.size
    }

    class ViewHolder(itemView: View, clickListener: onItemClickListener) : RecyclerView.ViewHolder(itemView) {
        val tvMejaName : TextView = itemView.findViewById(R.id.tvMejaName)

        init {
            itemView.setOnClickListener{
                clickListener.onItemClick(adapterPosition)
            }
        }
    }

}