package com.example.generatorfaktur

import android.os.Parcelable
import com.example.generatorfaktur.invoiceProperties.*
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
class Invoice : Parcelable {
    var currDate: Date? = null
    var id: String = ""
    var issuePlace: String = ""
    var dealer: Entity? = null
    var buyer: Entity? = null
    var recipient: Entity? = null
    var items: ArrayList<InvoiceItem> = ArrayList()
    var paymentProperty: PaymentProperty? = null
    var sumTable: ArrayList<Summation> = ArrayList()
    var sumProperty: SumProperty? = null

    //TODO: implementacja metod sumTable & sumProperty na podstawie wczesniejszych danych
}