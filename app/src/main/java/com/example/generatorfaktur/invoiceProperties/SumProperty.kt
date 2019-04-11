package com.example.generatorfaktur.invoiceProperties

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class SumProperty (
    val total: Double,
    val advance: Double,
    val toPay: Double
) : Parcelable