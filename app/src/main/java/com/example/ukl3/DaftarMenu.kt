package com.example.ukl3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.view.menu.MenuAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class DaftarMenu : AppCompatActivity() {

    private lateinit var menuRecycleView: RecyclerView
    private lateinit var tvLoadingData: TextView
    private lateinit var menuList: ArrayList<MenuModel>
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daftar_menu)

        menuRecycleView = findViewById(R.id.rvMenu)
        menuRecycleView.layoutManager = LinearLayoutManager(this)
        menuRecycleView.setHasFixedSize(true)
        tvLoadingData = findViewById(R.id.tvLoadingData)

        menuList = arrayListOf<MenuModel>()

        getMenuData()

    }

    private fun getMenuData() {
        menuRecycleView.visibility = View.GONE
        tvLoadingData.visibility = View.VISIBLE

        dbRef = FirebaseDatabase.getInstance().getReference("Menu")

        dbRef.addValueEventListener(object  : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                menuList.clear()
                if (snapshot.exists()){
                    for (menuSnap in snapshot.children){
                        val menuData = menuSnap.getValue(MenuModel::class.java)
                        menuList.add(menuData!!)
                    }
                    val mAdapter = MenuAdapter(menuList)
                    menuRecycleView.adapter = mAdapter



                    mAdapter.setOnItemClickListener(object : com.example.ukl3.MenuAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {
                            val intent = Intent(this@DaftarMenu, MenuDetail::class.java )

                            intent.putExtra("menuId", menuList[position].menuId)
                            intent.putExtra("menuName", menuList[position].menuName)
                            intent.putExtra("menuPrice", menuList[position].menuPrice)
                            intent.putExtra("menuDesc", menuList[position].menuDesc)
                            startActivity(intent)
                        }

                    })

                    menuRecycleView.visibility = View.VISIBLE
                    tvLoadingData.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}