package com.example.ukl3

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MenuAdapter (private val menulist: ArrayList<MenuModel>) :
    RecyclerView.Adapter<MenuAdapter.ViewHolder>(){

    private lateinit var mListener: onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(clickListener: onItemClickListener){
        mListener = clickListener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.listmenu, parent, false)
        return ViewHolder(itemView, mListener)
    }

    override fun onBindViewHolder(holder: MenuAdapter.ViewHolder, position: Int) {
        val currentMenu = menulist [ position]
        holder.tvMenuName.text = currentMenu.menuName
        holder.tvMenuPrice.text = currentMenu.menuPrice
        holder.tvMenuDesc.text = currentMenu.menuDesc
//        holder.btnEdit
//        holder.btnHapus
    }




    override fun getItemCount(): Int {
        return menulist.size
    }
    class ViewHolder (itemView: View,clickListener: onItemClickListener): RecyclerView.ViewHolder(itemView) {
//        val btnEdit: Button = itemView.findViewById(R.id.btnEdtMenu)
//        val btnHapus: Button = itemView.findViewById(R.id.btnDeleteMenu)
        val tvMenuName : TextView = itemView.findViewById(R.id.tvMenuName)
        val tvMenuPrice : TextView = itemView.findViewById(R.id.tvMenuPrice)
        val tvMenuDesc : TextView = itemView.findViewById(R.id.tvMenuDesc)

        init {
            itemView.setOnClickListener{
                clickListener.onItemClick(adapterPosition)
            }
        }
    }
}