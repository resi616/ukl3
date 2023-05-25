package com.example.ukl3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.database.FirebaseDatabase

class MejaDetail : AppCompatActivity() {

    private lateinit var tvMejaId: TextView
    private lateinit var tvMejaName2: TextView
    private lateinit var btnUpdate: Button
    private lateinit var btnDelete: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meja_detail)
        initView()
        setValuesToViews()

        btnUpdate.setOnClickListener {
            openUpdateDialog(
                intent.getStringExtra("mejaId").toString(),
                intent.getStringExtra("mejaName").toString()
            )
        }

        btnDelete.setOnClickListener {
            deleteRecord(
                intent.getStringExtra("mejaId").toString()
            )
        }
    }

    private fun deleteRecord(
        id:String
    ){
        val dbRef = FirebaseDatabase.getInstance().getReference("Meja").child(id)
        val mTask = dbRef.removeValue()

        mTask.addOnSuccessListener {
            Toast.makeText(this,"Meja Data Deleted", Toast.LENGTH_LONG).show()
            val intent = Intent(this, DaftarMeja::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener {error ->
            Toast.makeText(this,"Deleting Err ${error.message}", Toast.LENGTH_LONG).show()

        }
    }
    private fun initView() {
        tvMejaId = findViewById(R.id.tvMejaId)
        tvMejaName2 = findViewById(R.id.tvMejaName2)


        btnUpdate = findViewById(R.id.btnUpdate_meja)
        btnDelete = findViewById(R.id.btnDelete_meja)
    }

    private fun setValuesToViews() {

        tvMejaId.text = intent.getStringExtra("mejaId")
        tvMejaName2.text = intent.getStringExtra("mejaName")



    }

    fun openUpdateDialog(mejaId: String, mejaName: String) {
        val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.update_meja_dialog, null)

        mDialog.setView(mDialogView)

        val etMejaName = mDialogView.findViewById<EditText>(R.id.etMejaName)


        val btnUpdateData = mDialogView.findViewById<Button>(R.id.btnUpdateData_meja)

        etMejaName.setText(intent.getStringExtra("mejaName").toString())


        mDialog.setTitle("updating $mejaName Record")

        val alertDialog = mDialog.create()
        alertDialog.show()

        btnUpdateData.setOnClickListener {
            updateMejaData(
                mejaId,
                etMejaName.text.toString()
            )

            Toast.makeText(applicationContext, "Meja Data Updated", Toast.LENGTH_LONG).show()

            //
            tvMejaName2.text = etMejaName.text.toString()


            alertDialog.dismiss()
        }


    }

    private fun updateMejaData(
        id: String,
        name: String
    ) {
        val dbRef = FirebaseDatabase.getInstance().getReference("Meja").child(id)
        val mejaInfo = MejaModel(id, name)
        dbRef.setValue(mejaInfo)
    }
}