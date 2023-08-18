package com.example.ukl3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val role = intent.getStringExtra("role")

        val pindahAdminOption = findViewById<Button>(R.id.masuk_admin)
        val pindahKasirOption = findViewById<Button>(R.id.masuk_kasir)
        val pindahManagerOption = findViewById<Button>(R.id.masuk_manager)

        if (role == "kasir") {
            pindahKasirOption.visibility = View.VISIBLE
            pindahAdminOption.visibility = View.GONE
            pindahManagerOption.visibility = View.GONE
        } else if (role == "manager") {
            pindahKasirOption.visibility = View.GONE
            pindahAdminOption.visibility = View.GONE
            pindahManagerOption.visibility = View.VISIBLE
        } else {
            pindahKasirOption.visibility = View.VISIBLE
            pindahAdminOption.visibility = View.VISIBLE
            pindahManagerOption.visibility = View.VISIBLE
        }

        pindahAdminOption.setOnClickListener {
            val intent = Intent(this, adminOption::class.java)
            startActivity(intent)
        }

        pindahKasirOption.setOnClickListener {
            val intent = Intent(this, menuKasir::class.java)
            startActivity(intent)
        }

        pindahManagerOption.setOnClickListener {
            val intent = Intent(this, managerOption::class.java)
            startActivity(intent)
        }
    }
}
