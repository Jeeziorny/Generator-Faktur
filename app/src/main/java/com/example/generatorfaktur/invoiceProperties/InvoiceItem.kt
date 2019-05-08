package com.example.generatorfaktur.invoiceProperties

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class InvoiceItem (
    val name: String,
    val quantity: Double,
    val baseValue: Double,
    var value: Double,
    val vat: Double,
    var grossValue: Double
) : Parcelable {

    fun setValue() {
        value = quantity * baseValue
    }

    fun setGrossValue() {
        grossValue = value*(1+vat)
    }

    fun getAsString() : String {
        val newName = name.replace(Regex("(.{10})"), "<br>")
        return "$newName;$quantity;$baseValue;$value;$vat;$grossValue"
    }
}