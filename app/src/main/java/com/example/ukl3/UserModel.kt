package com.example.ukl3

data class UserModel(
    var userId: String = "",
    var userName: String = "", // Ubah menjadi userName (huruf "N" besar)
    var passwordPegawai: String = "",
    var roleKasir : Boolean = false,
    var roleManager: Boolean = false
)
