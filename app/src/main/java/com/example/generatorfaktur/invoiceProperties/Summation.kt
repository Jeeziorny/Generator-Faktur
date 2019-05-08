package com.example.generatorfaktur.invoiceProperties

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Summation (
    var totalNetto: Double,
    var totalVat: Double,
    var totalGros: Double,
    var vat: Double
) : Parcelable {

    fun getAsString() : String {
        return "$totalNetto;$vat;$totalVat;$totalGros"
    }

    fun addTotalNetto(d: Double) {
        totalNetto += d
    }

    fun addTotalVat(v: Double) {
        totalVat += v
    }

    fun addTotalGros(g: Double) {
        totalGros += g
    }
}