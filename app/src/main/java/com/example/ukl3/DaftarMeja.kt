package com.example.ukl3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class DaftarMeja : AppCompatActivity() {

    private lateinit var mejaRecycleView: RecyclerView
    private lateinit var tvLoadingData: TextView
    private lateinit var mejaList: ArrayList<MejaModel>
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daftar_meja)

        mejaRecycleView = findViewById(R.id.rvMeja)
        mejaRecycleView.layoutManager = LinearLayoutManager(this)
        mejaRecycleView.setHasFixedSize(true)
        tvLoadingData = findViewById(R.id.tvLoadingData)

        mejaList = arrayListOf<MejaModel>()

        getMejaData()
    }

    private fun getMejaData() {
        mejaRecycleView.visibility = View.GONE
        tvLoadingData.visibility = View.VISIBLE

        dbRef = FirebaseDatabase.getInstance().getReference("Meja")

        dbRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                mejaList.clear()
                if(snapshot.exists()){
                    for (mejaSnap in snapshot.children){
                        val mejaData = mejaSnap.getValue(MejaModel::class.java)
                        mejaList.add(mejaData!!)
                    }
                    val mAdapter = MejaAdapter(mejaList)
                    mejaRecycleView.adapter = mAdapter

                    mAdapter.setOnItemClickListener(object : MejaAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {
                            val intent = Intent(this@DaftarMeja, MejaDetail::class.java)

                            intent.putExtra("mejaId", mejaList[position].mejaId)
                            intent.putExtra("mejaName", mejaList[position].mejaName)
                            startActivity(intent)
                        }

                    })

                    mejaRecycleView.visibility = View.VISIBLE
                    tvLoadingData.visibility = View.GONE


                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}