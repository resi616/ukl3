package com.example.ukl3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Transaction
import com.google.firebase.database.ValueEventListener
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

class TambahTransaksi : AppCompatActivity() {

    private lateinit var database: FirebaseDatabase
    private lateinit var menuRef: DatabaseReference
    private lateinit var spinnerMenu: Spinner
    private lateinit var spinnerPayment: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tambah_transaksi)

        database = FirebaseDatabase.getInstance()
        menuRef = database.getReference("Menu")
        spinnerPayment = findViewById(R.id.spinner_payment)
        spinnerMenu = findViewById(R.id.spinner_menu)

        setupSpinnerMenu()

        val btnAddTransaksi = findViewById<Button>(R.id.addTransaksi)
        btnAddTransaksi.setOnClickListener {
            tambahTransaksi()
        }
    }

    private fun tambahTransaksi() {
        val idTransaksi = UUID.randomUUID().toString()
        val menu = spinnerMenu.selectedItem.toString()
        val selectedPayment = spinnerPayment.selectedItem.toString()
        val tanggal = getCurrentDate()

        val transaction = TransaksiModel(idTransaksi, menu, tanggal, selectedPayment)
        val transactionRef = database.getReference("transaksi").child(idTransaksi)

        transactionRef.setValue(transaction)
            .addOnSuccessListener {
                Toast.makeText(this, "Transaksi berhasil ditambahkan", Toast.LENGTH_SHORT).show()

            }
            .addOnFailureListener {
                Toast.makeText(this, "Gagal menambahkan transaksi", Toast.LENGTH_SHORT).show()

            }
    }


    private fun setupSpinnerMenu() {
        val placeholder = "Pilih Menu"
        val listMenu = mutableListOf(placeholder)

        val paymentMethods = listOf("Cash", "Debit", "QRIS")
        val paymentAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, paymentMethods)
        paymentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerPayment.adapter = paymentAdapter

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, listMenu)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerMenu.adapter = adapter

        menuRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                listMenu.clear()
                listMenu.add(placeholder)
                for (menuSnapshot in snapshot.children) {
                    val menuName = menuSnapshot.child("menuName").getValue(String::class.java)
                    menuName?.let { listMenu.add(it) }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
            }
        })



    }

    private fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        val date = Date()
        return dateFormat.format(date)
    }
}




