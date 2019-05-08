package com.example.generatorfaktur.invoiceProperties

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
class PaymentProperty : Parcelable {
    var paymentForm = ""
    var paymentDate = Date()
    var bank = ""
    var accountNumber = ""

    fun toArrayOfString() : ArrayList<String> {
        val result = ArrayList<String>()
        result.addAll(listOf(paymentForm, paymentDate.toString(), bank, accountNumber))
        return result
    }
}