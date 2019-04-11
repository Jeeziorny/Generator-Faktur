package com.example.generatorfaktur

import android.os.Parcelable
import com.example.invoicebuilder.invoiceProperties.*
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
class Invoice (
    val currDate: Date,
    val id: String,
    val issuePlace: String,
    val dealer: Entity,
    val buyer: Entity,
    val recipient: Entity,
    val items: ArrayList<InvoiceItem>,
    val paymentProperty: PaymentProperty,
    val sumTable: ArrayList<Summation>,
    val sumProperty: SumProperty
) : Parcelable