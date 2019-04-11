package com.example.generatorfaktur.invoiceProperties

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
class PaymentProperty (
    val paymentForm: String,
    val paymentDate: Date,
    val bank: String,
    val accountNumber: String
) : Parcelable