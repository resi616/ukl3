package com.example.ukl3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.text.FieldPosition

class DaftarUser : AppCompatActivity() {

    private lateinit var userRecyclerView: RecyclerView
    private lateinit var tvLoadingData: TextView
    private lateinit var userList: ArrayList<UserModel>
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daftar_user)

        userRecyclerView = findViewById(R.id.rvUser)
        userRecyclerView.layoutManager = LinearLayoutManager(this)
        userRecyclerView.setHasFixedSize(true)
        tvLoadingData = findViewById(R.id.tvLoadingData)

        userList = arrayListOf<UserModel>()

        getUserData()
    }

    private fun getUserData() {
        userRecyclerView.visibility = View.GONE
        tvLoadingData.visibility = View.VISIBLE

        dbRef = FirebaseDatabase.getInstance().getReference("User")

        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                userList.clear()
                if (snapshot.exists()) {
                    for (userSnap in snapshot.children) {
                        val userData = userSnap.getValue(UserModel::class.java)
                        userList.add(userData!!)
                    }
                    val mAdapter = UserAdapter(userList)
                    userRecyclerView.adapter = mAdapter

                    mAdapter.setOnItemClickListener(object : UserAdapter.onItemClickListener {
                        override fun onItemClick(position: Int) {
                            val intent = Intent(this@DaftarUser, UserDetail::class.java)

                            intent.putExtra("userId", userList[position].userId)
                            intent.putExtra("userName", userList[position].userName)
                            startActivity(intent)
                        }

                    })
                }
                userRecyclerView.visibility = View.VISIBLE
                tvLoadingData.visibility = View.GONE
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }
}
