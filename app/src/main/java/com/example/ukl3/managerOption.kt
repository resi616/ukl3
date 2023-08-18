package com.example.ukl3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class managerOption : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manager_option)

        val riwayatTransaksi = findViewById<Button>(R.id.historyTransaksi)




        riwayatTransaksi.setOnClickListener {
            val intent = Intent(this, RiwayatTransaksi::class.java)
            startActivity(intent)
        }
    }
}