package com.example.ukl3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener

class loginpegawai : AppCompatActivity() {
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference
    private lateinit var btnLogin: Button
    private lateinit var edtUsername: EditText
    private lateinit var edtPassword: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loginpegawai)

        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.reference.child("User")

        btnLogin = findViewById(R.id.login_button)
        edtUsername = findViewById(R.id.usernamePegawai)
        edtPassword = findViewById(R.id.passwordPegawai)

        btnLogin.setOnClickListener {
            val userName = edtUsername.text.toString().trim()
            val password = edtPassword.text.toString()

            if (userName.isNotEmpty() && password.isNotEmpty()) {
                loginPegawai(userName, password)
            } else {
                Toast.makeText(this, "Username dan Password harus diisi", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loginPegawai(username: String, password: String) {
        val query: Query = databaseReference.orderByChild("userName").equalTo(username)
        query.addListenerForSingleValueEvent(object : ValueEventListener{

            override fun onDataChange(dataSnapshot: DataSnapshot) {

                if (dataSnapshot.exists()) {
                    for (userSnapshot in dataSnapshot.children) {
                        val user = userSnapshot.getValue(UserModel::class.java)
                        if (user?.passwordPegawai == password) {
                            if (user.roleKasir) {

                                val intent = Intent(this@loginpegawai, MainActivity::class.java)
                                intent.putExtra("role", "kasir")
                                startActivity(intent)
                                finish()
                            } else if (user.roleManager) {

                                val intent = Intent(this@loginpegawai, managerOption::class.java)
                                intent.putExtra("role", "manager")
                                startActivity(intent)
                                finish()
                            } else {

                                Toast.makeText(this@loginpegawai, "Anda tidak memiliki akses yang valid", Toast.LENGTH_SHORT).show()
                            }
                        } else {

                            Toast.makeText(this@loginpegawai, "Password salah", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {

                    Toast.makeText(this@loginpegawai, "Username tidak ditemukan", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {

                Toast.makeText(this@loginpegawai, "Error: ${databaseError.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}