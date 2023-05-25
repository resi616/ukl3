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

class MenuDetail : AppCompatActivity() {

    private lateinit var tvMenuId: TextView
    private lateinit var tvMenuName2: TextView
    private lateinit var tvMenuPrice2: TextView
    private lateinit var tvMenuDesc2: TextView
    private lateinit var btnUpdate: Button
    private lateinit var btnDelete: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_menu)
        initView()
        setValuesToViews()

        btnUpdate.setOnClickListener {
            openUpdateDialog(
                intent.getStringExtra("menuId").toString(),
                intent.getStringExtra("menuName").toString()
            )
        }

        btnDelete.setOnClickListener {
            deleteRecord(
                intent.getStringExtra("menuId").toString(),
            )
        }
    }

    private fun deleteRecord(
        id:String
    ){
        val dbRef = FirebaseDatabase.getInstance().getReference("Menu").child(id)
        val mTask = dbRef.removeValue()

        mTask.addOnSuccessListener {
            Toast.makeText(this,"Menu Data Deleted", Toast.LENGTH_LONG).show()
            val intent = Intent(this, DaftarMenu::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener {error ->
            Toast.makeText(this,"Deleting Err ${error.message}", Toast.LENGTH_LONG).show()

        }
    }
    private fun initView() {
        tvMenuId = findViewById(R.id.tvMenuId)
        tvMenuName2 = findViewById(R.id.tvMenuName2)
        tvMenuPrice2 = findViewById(R.id.tvMenuPrice2)
        tvMenuDesc2 = findViewById(R.id.tvMenuDesc2)

        btnUpdate = findViewById(R.id.btnUpdate)
        btnDelete = findViewById(R.id.btnDelete)
    }

    private fun setValuesToViews() {

        tvMenuId.text = intent.getStringExtra("menuId")
        tvMenuName2.text = intent.getStringExtra("menuName")
        tvMenuPrice2.text = intent.getStringExtra("menuPrice")
        tvMenuDesc2.text = intent.getStringExtra("menuDesc")


    }

    fun openUpdateDialog(menuId: String, menuName: String) {
        val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.update_menu_dialog, null)

        mDialog.setView(mDialogView)

        val etMenuName = mDialogView.findViewById<EditText>(R.id.etMenuName)
        val etMenuPrice = mDialogView.findViewById<EditText>(R.id.etMenuPrice)
        val etMenuDesc = mDialogView.findViewById<EditText>(R.id.etMenuDesc)

        val btnUpdateData = mDialogView.findViewById<Button>(R.id.btnUpdateData)

        etMenuName.setText(intent.getStringExtra("menuName").toString())
        etMenuPrice.setText(intent.getStringExtra("menuPrice").toString())
        etMenuDesc.setText(intent.getStringExtra("menuDesc").toString())

        mDialog.setTitle("Updating $menuName Record")

        val alertDialog = mDialog.create()
        alertDialog.show()

        btnUpdateData.setOnClickListener {
            updateMenuData(
                menuId,
                etMenuName.text.toString(),
                etMenuPrice.text.toString(),
                etMenuDesc.text.toString()
            )

            Toast.makeText(applicationContext, "Menu Data Updated", Toast.LENGTH_LONG).show()

            //
            tvMenuName2.text = etMenuName.text.toString()
            tvMenuPrice2.text = etMenuPrice.text.toString()
            tvMenuDesc2.text = etMenuDesc.text.toString()

            alertDialog.dismiss()
        }


    }

    private fun updateMenuData(
        id: String,
        name: String,
        price: String,
        desc: String
    ) {
        val dbRef = FirebaseDatabase.getInstance().getReference("Menu").child(id)
        val menuInfo = MenuModel(id, name, price, desc)
        dbRef.setValue(menuInfo)
    }
}