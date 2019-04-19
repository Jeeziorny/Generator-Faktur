package com.example.generatorfaktur.invoiceProperties

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Entity (
    var name: String,
    var adress: String,
    var postal: String,
    var nip: String,
    var phoneNumber: String
) : Parcelable{
    constructor() : this("","","","","")
}