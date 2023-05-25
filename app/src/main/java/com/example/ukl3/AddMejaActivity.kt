package com.example.ukl3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.ukl3.databinding.ActivityAddMejaBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AddMejaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddMejaBinding
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddMejaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = FirebaseDatabase.getInstance().getReference("Meja")

        binding.btnAddMeja.setOnClickListener {
            val mejaName = binding.edtMeja.text.toString()

            if (mejaName.isNotEmpty()) {
                val mejaId = databaseReference.push().key!!
                val meja = MejaModel(mejaId, mejaName)
                databaseReference.child(mejaId).setValue(meja)
                    .addOnCompleteListener {
                        Toast.makeText(this, "Tambah Meja Sukses", Toast.LENGTH_SHORT).show()
                        binding.edtMeja.text?.clear()
                    }.addOnFailureListener { err ->
                    }
            } else {
                Toast.makeText(this, "Field Text cannot be empty", Toast.LENGTH_SHORT).show()
            }

        }

    }
}