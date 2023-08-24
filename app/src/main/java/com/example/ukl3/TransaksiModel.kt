package com.example.ukl3

data class TransaksiModel(
    val idTransaksi: String = "",
    val menu: String = "",
    val tanggal: String = "",
    val selectedPayment: String = "",
    val harga: String = "",
)
