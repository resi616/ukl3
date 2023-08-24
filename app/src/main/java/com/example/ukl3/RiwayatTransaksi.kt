package com.example.ukl3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ukl3.databinding.ActivityRiwayatTransaksiBinding
import com.google.firebase.database.*

class RiwayatTransaksi : AppCompatActivity() {

    private lateinit var binding: ActivityRiwayatTransaksiBinding
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRiwayatTransaksiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        databaseReference = FirebaseDatabase.getInstance().getReference("transaksi")

        val transaksiList = mutableListOf<TransaksiModel>()
        val transaksiAdapter = TransaksiAdapter(transaksiList)

        binding.rvTransaksi.apply {
            layoutManager = LinearLayoutManager(this@RiwayatTransaksi)
            adapter = transaksiAdapter
        }

        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                transaksiList.clear()
                for (dataSnapshot in snapshot.children) {
                    val transaksi = dataSnapshot.getValue(TransaksiModel::class.java)
                    transaksi?.let {
                        transaksiList.add(it)
                    }
                }
                transaksiAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
            }
        })
    }
}