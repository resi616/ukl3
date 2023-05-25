package com.example.ukl3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val pindahAdminOption = findViewById<Button>(R.id.masuk_admin)
        pindahAdminOption.setOnClickListener {

            val intent = Intent(this, adminOption::class.java)
            startActivity(intent)

        }

        val pindahKasirOption = findViewById<Button>(R.id.masuk_kasir)
        pindahKasirOption.setOnClickListener {
            // Pindah ke LoginKasirActivity
            val intent = Intent(this, kasirOption::class.java)
            startActivity(intent)

        }
    }
}