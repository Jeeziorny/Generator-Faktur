package com.example.generatorfaktur.invoiceProperties

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Summation (
    val totalNetto: Double,
    val totalVat: Double,
    val totalGros: Double
) : Parcelable