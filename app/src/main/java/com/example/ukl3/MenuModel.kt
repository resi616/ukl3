package com.example.ukl3

import android.os.Parcel
import android.os.Parcelable


data class MenuModel(
    var menuId: String = "",
    var menuName: String = "",
    var menuPrice: String = "",
    var menuDesc: String = "",
    var imageUrl: String = ""
)
