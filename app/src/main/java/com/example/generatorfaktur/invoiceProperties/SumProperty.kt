package com.example.generatorfaktur.invoiceProperties

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class SumProperty (
    var netSum: Double,
    var taxSum: Double,
    var pSum: Double
) : Parcelable {

    fun getAsString() : String {
        return "$netSum;$taxSum;$pSum"
    }
}