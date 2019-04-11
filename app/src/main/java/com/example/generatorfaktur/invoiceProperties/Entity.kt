package com.example.generatorfaktur.invoiceProperties

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Entity (
    val name: String,
    val adress: String,
    val postal: String,
    val nip: String,
    val phoneNumber: String
) : Parcelable