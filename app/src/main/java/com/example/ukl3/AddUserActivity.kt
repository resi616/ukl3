package com.example.ukl3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.ukl3.databinding.ActivityAddUserBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AddUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddUserBinding
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddUserBinding.inflate((layoutInflater))
        setContentView(binding.root)

        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = FirebaseDatabase.getInstance().getReference("User")

        binding.btnAddUser.setOnClickListener{
            val userName = binding.edtUser.text.toString()
            val passwordPegawai = binding.edtPaswordPegawai.text.toString()
            val roleKasir =  binding.checkboxKasir.isChecked
            val roleManager = binding.checkboxManager.isChecked

            if(userName.isNotEmpty()){
                val userId = databaseReference.push().key!!
                val user = UserModel(userId, userName,passwordPegawai, roleKasir, roleManager)
                databaseReference.child(userId).setValue(user)
                    .addOnCompleteListener{
                        Toast.makeText(this, "Tambah User Sukses", Toast.LENGTH_SHORT).show()
                        binding.edtUser.text?.clear()
                        binding.edtPaswordPegawai.text?.clear()
                        binding.checkboxKasir.isChecked = false
                        binding.checkboxManager.isChecked = false
                    }.addOnFailureListener{err ->
                    }
            }else  {
                Toast.makeText(this, "Field Text cannot be empty", Toast.LENGTH_SHORT).show()

            }
        }
    }
}