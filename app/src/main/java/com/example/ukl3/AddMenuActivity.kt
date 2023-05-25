package com.example.ukl3

import android.app.Activity
import android.app.Instrumentation.ActivityResult
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import com.example.ukl3.databinding.ActivityAddMenuBinding

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class AddMenuActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddMenuBinding
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Init

        firebaseDatabase = FirebaseDatabase.getInstance()

        databaseReference = FirebaseDatabase.getInstance().getReference("Menu")

        binding.btnAddMenu.setOnClickListener {
            val menuName = binding.edtMenu.text.toString()
            val menuPrice = binding.edtPriceMenu.text.toString()
            val menuDesc = binding.edtDescMenu.text.toString()

            if (menuName.isNotEmpty() && menuPrice.isNotEmpty() && menuDesc.isNotEmpty()) {
                val menuId = databaseReference.push().key!!
                val menu = MenuModel(menuId, menuName, menuPrice, menuDesc)
                databaseReference.child(menuId).setValue(menu) // Create child using menuId
                    .addOnCompleteListener {
                        Toast.makeText(this, "Tambah Menu Sukses", Toast.LENGTH_SHORT).show()
                        binding.edtMenu.text?.clear()
                        binding.edtPriceMenu.text?.clear()
                        binding.edtDescMenu.text?.clear()
                    }.addOnFailureListener { err ->
                        Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_SHORT).show()
                    }
            } else {
                Toast.makeText(this, "Field Text cannot be empty", Toast.LENGTH_SHORT).show()
            }

        }

    }
}