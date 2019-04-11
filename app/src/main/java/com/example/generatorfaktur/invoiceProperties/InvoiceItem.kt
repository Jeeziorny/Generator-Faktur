package com.example.generatorfaktur.invoiceProperties

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class InvoiceItem (
    val name: String,
    val unit: String,
    val quantity: Double,
    val vat: Double,
    val value: Double,
    val vatValue: Double,
    val grossValue: Double
) : Parcelable