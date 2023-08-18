package com.example.ukl3


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ukl3.databinding.ActivityDaftarMenuBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class DaftarMenu : AppCompatActivity() {


    private lateinit var binding: ActivityDaftarMenuBinding
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDaftarMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        databaseReference = FirebaseDatabase.getInstance().getReference("Menu")

        val menuList = mutableListOf<MenuModel>()
        val menuAdapter = MenuAdapter(menuList)

        binding.rvMenu.apply {
            layoutManager = LinearLayoutManager(this@DaftarMenu)
            adapter = menuAdapter
        }

        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                menuList.clear()
                for (dataSnapshot in snapshot.children) {
                    val menu = dataSnapshot.getValue(MenuModel::class.java)
                    menu?.let {
                        menuList.add(it)
                    }
                }
                menuAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
            }
        })

        // Set onClickListener for btnUpdateMenu using ViewBinding
        menuAdapter.setOnItemClickListener(object : MenuAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                val intent = Intent(this@DaftarMenu, menuDetail::class.java)
                // Add any extra data you want to pass to MenuDetailActivity using intent.putExtra()
                startActivity(intent)
            }
        })
    }
}









