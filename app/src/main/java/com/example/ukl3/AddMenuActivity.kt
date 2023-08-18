package com.example.ukl3


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.ukl3.databinding.ActivityAddMenuBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class AddMenuActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddMenuBinding
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference
    private lateinit var storageReference: StorageReference
    private lateinit var getContent: ActivityResultLauncher<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)
        storageReference = FirebaseStorage.getInstance().reference
        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.reference.child("Menu")
        storageReference = FirebaseStorage.getInstance().reference.child("menu_images")

        getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                binding.tvImage.setImageURI(uri)
                binding.tvImage.tag = uri  // Tambahkan ini
            }
        }

        binding.tvImage.setOnClickListener {
            getContent.launch("image/*")
        }

        binding.btnAddMenu.setOnClickListener {
            val menuName = binding.edtMenu.text.toString()
            val menuPrice = binding.edtPriceMenu.text.toString()
            val menuDesc = binding.edtDescMenu.text.toString()

            if (menuName.isNotEmpty() && menuPrice.isNotEmpty() && menuDesc.isNotEmpty()) {
                val menuId = databaseReference.push().key!!
                val menu = MenuModel(menuId, menuName, menuPrice, menuDesc)

                val imageUri = binding.tvImage.tag as Uri?
                if (imageUri != null) {
                    val imageRef = storageReference.child("$menuId.jpg")
                    imageRef.putFile(imageUri)
                        .addOnSuccessListener {
                            imageRef.downloadUrl.addOnSuccessListener { uri ->
                                menu.imageUrl = uri.toString()
                                databaseReference.child(menuId).setValue(menu)
                                    .addOnCompleteListener {
                                        Toast.makeText(this, "Tambah Menu Sukses", Toast.LENGTH_SHORT).show()
                                        clearFields()
                                    }.addOnFailureListener { err ->
                                        Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_SHORT).show()
                                    }
                            }
                        }
                        .addOnFailureListener { err ->
                            Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_SHORT).show()
                        }
                } else {
                    Toast.makeText(this, "Please select an image", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Field Text cannot be empty", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun clearFields() {
        binding.edtMenu.text?.clear()
        binding.edtPriceMenu.text?.clear()
        binding.edtDescMenu.text?.clear()
        binding.tvImage.setImageResource(R.drawable.ic_launcher_foreground)
        binding.tvImage.tag = null
    }
}
