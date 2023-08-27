package com.example.ukl3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class adminOption : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_option)

        val pindahTambahMenu = findViewById<Button>(R.id.tambah_menu)
        pindahTambahMenu.setOnClickListener {

            val intent = Intent(this, AddMenuActivity::class.java)
            startActivity(intent)

        }

        val pindahDaftarMenu = findViewById<Button>(R.id.daftar_menu)
        pindahDaftarMenu.setOnClickListener {

            val intent = Intent(this, DaftarMenu::class.java)
            startActivity(intent)

        }

        val pindahTambahMeja = findViewById<Button>(R.id.tambah_meja)
        pindahTambahMeja.setOnClickListener {

            val intent = Intent(this, AddMejaActivity::class.java)
            startActivity(intent)

        }

        val pindahDaftarMeja = findViewById<Button>(R.id.daftar_meja)
        pindahDaftarMeja.setOnClickListener {

            val intent = Intent(this, DaftarMeja::class.java)
            startActivity(intent)

        }

        val pindahDaftarUser = findViewById<Button>(R.id.daftar_user)
        pindahDaftarUser.setOnClickListener {

            val intent = Intent(this, DaftarUser::class.java)
            startActivity(intent)

        }


        val pindahTambahUser = findViewById<Button>(R.id.tambah_user)
        pindahTambahUser.setOnClickListener {

            val intent = Intent(this, AddUserActivity::class.java)
            startActivity(intent)

        }
    }
}