package com.example.ukl3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import android.net.Uri
import com.example.ukl3.DaftarMenu
import com.example.ukl3.MenuModel
import com.example.ukl3.R

class menuDetail : AppCompatActivity() {
    private lateinit var tvMenuId: TextView
    private lateinit var tvMenuName2: TextView
    private lateinit var tvMenuPrice2: TextView
    private lateinit var tvMenuDesc2: TextView
    private lateinit var btnUpdate: Button
    private lateinit var btnDelete: Button
    private lateinit var btnUpdateImage: Button
    private lateinit var btnUpdateData: Button
    private lateinit var ivMenuImagePreview: ImageView

    private lateinit var storageReference: StorageReference
    private var selectedImageUri: Uri? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_detail)
        initView()
        setValuesToViews()

        storageReference = FirebaseStorage.getInstance().reference

        btnUpdateImage.setOnClickListener {
            // Buka pemilih gambar
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, PICK_IMAGE_REQUEST)
        }

        btnUpdate.setOnClickListener {
            openUpdateDialog(
                intent.getStringExtra("menuId").toString(),
                intent.getStringExtra("menuName").toString()
            )
        }


        btnDelete.setOnClickListener {
            deleteRecord(
                intent.getStringExtra("menuId").toString(),
                intent.getStringExtra("menuName").toString()
            )
        }

        btnUpdateData.setOnClickListener { // Perbaikan di sini
            // Panggil fungsi untuk mengunggah gambar dan memperbarui data menu
            if (selectedImageUri != null) {
                uploadImage(intent.getStringExtra("menuId").toString())
            }
            updateMenuData(
                intent.getStringExtra("menuId").toString(),
                tvMenuName2.text.toString(),
                tvMenuPrice2.text.toString(),
                tvMenuDesc2.text.toString()
            )
            Toast.makeText(this, "Menu Data Updated", Toast.LENGTH_LONG).show()
            setValuesToViews()
        }
    }

    private fun updateMenuData(id: String, name: String, price: String, desc: String) {
        val dbRef = FirebaseDatabase.getInstance().getReference("Menu").child(id)
        val menuInfo = MenuModel(id, name, price, desc, intent.getStringExtra("imageUrl").toString())
        dbRef.setValue(menuInfo)
            .addOnSuccessListener {
                Toast.makeText(this, "Menu Data Updated", Toast.LENGTH_LONG).show()
            }
            .addOnFailureListener { error ->
                Toast.makeText(this, "Updating Error: ${error.message}", Toast.LENGTH_LONG).show()
            }
    }

    private fun deleteRecord(id: String, menuName: String) {
        val dbRef = FirebaseDatabase.getInstance().getReference("Menu").child(id)
        dbRef.removeValue()
            .addOnSuccessListener {
                Toast.makeText(this, "$menuName Menu Deleted", Toast.LENGTH_LONG).show()
                val intent = Intent(this, DaftarMenu::class.java)
                finish()
                startActivity(intent)
            }
            .addOnFailureListener { error ->
                Toast.makeText(this, "Deleting Error: ${error.message}", Toast.LENGTH_LONG).show()
            }
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

        etMenuName.setText(menuName)
        etMenuPrice.setText(tvMenuPrice2.text.toString())
        etMenuDesc.setText(tvMenuDesc2.text.toString())

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

            tvMenuName2.text = etMenuName.text.toString()
            tvMenuPrice2.text = etMenuPrice.text.toString()
            tvMenuDesc2.text = etMenuDesc.text.toString()

            alertDialog.dismiss()
        }
    }



    private fun initView() {
        tvMenuId = findViewById(R.id.tvMenuId)
        tvMenuName2 = findViewById(R.id.tvMenuName2)
        tvMenuPrice2 = findViewById(R.id.tvMenuPrice2)
        tvMenuDesc2 = findViewById(R.id.tvMenuDesc2)

        btnUpdate = findViewById(R.id.btnUpdate)
        btnDelete = findViewById(R.id.btnDelete)
        btnUpdateImage = findViewById(R.id.btnChooseImage)
        btnUpdateData = findViewById(R.id.btnUpdateData)
    }

    private fun setValuesToViews() {
        tvMenuId.text = intent.getStringExtra("menuId")
        tvMenuName2.text = intent.getStringExtra("menuName")
        tvMenuPrice2.text = intent.getStringExtra("menuPrice")
        tvMenuDesc2.text = intent.getStringExtra("menuDesc")
    }

    // Fungsi openUpdateDialog tetap sama

    // Fungsi updateMenuData tetap sama

    private fun uploadImage(menuId: String) {
        val imageRef = storageReference.child("menu_images").child("$menuId.jpg")

        imageRef.putFile(selectedImageUri!!)
            .addOnSuccessListener {
                imageRef.downloadUrl.addOnSuccessListener { uri ->

                    val dbRef = FirebaseDatabase.getInstance().getReference("Menu").child(menuId)
                    val menuInfo = MenuModel(
                        menuId,
                        tvMenuName2.text.toString(),
                        tvMenuPrice2.text.toString(),
                        tvMenuDesc2.text.toString(),
                        uri.toString()
                    )
                    dbRef.setValue(menuInfo)
                }
            }
            .addOnFailureListener {

            }
    }

    companion object {
        private const val PICK_IMAGE_REQUEST = 1
    }
}