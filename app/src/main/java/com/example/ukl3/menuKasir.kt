package com.example.ukl3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button


class menuKasir : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kasir_option)


        val tambahTransaksi = findViewById<Button>(R.id.transaksiBaru)
        val riwayatTransaksi = findViewById<Button>(R.id.historyTransaksi)


        tambahTransaksi.setOnClickListener {
            val intent = Intent(this, TambahTransaksi::class.java)
            startActivity(intent)
        }

        riwayatTransaksi.setOnClickListener {
            val intent = Intent(this, RiwayatTransaksi::class.java)
            startActivity(intent)
        }
    }
}





