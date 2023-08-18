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
import com.example.ukl3.MenuModel

class MenuAdapter(private val menuList: List<MenuModel>) : RecyclerView.Adapter<MenuAdapter.ViewHolder>() {

    private val selectedMenus: MutableList<MenuModel> = mutableListOf()
    private lateinit var mListener: OnItemClickListener

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        mListener = listener
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivMenuImage: ImageView = itemView.findViewById(R.id.ivMenuImage)
        val tvMenuName: TextView = itemView.findViewById(R.id.tvMenuName)
        val tvMenuPrice: TextView = itemView.findViewById(R.id.tvMenuPrice)
        val tvMenuDesc: TextView = itemView.findViewById(R.id.tvMenuDesc)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    mListener.onItemClick(position)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.listmenu, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = menuList[position]

        holder.tvMenuName.text = currentItem.menuName
        holder.tvMenuPrice.text = currentItem.menuPrice
        holder.tvMenuDesc.text = currentItem.menuDesc

        Glide.with(holder.itemView.context)
            .load(currentItem.imageUrl)
            .transform(CenterCrop(), RoundedCorners(8))
            .placeholder(R.drawable.ic_launcher_foreground)
            .into(holder.ivMenuImage)
    }

    override fun getItemCount() = menuList.size

    fun getSelectedMenus(): List<MenuModel> {
        return selectedMenus
    }
}
