package com.example.generatorfaktur

import android.annotation.SuppressLint
import android.os.Parcelable
import com.example.generatorfaktur.invoiceProperties.*
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
class Invoice : Parcelable {
    var currDate: Date = Date()
    var id: String = ""
    var sellDate: Date? = Date()
    var dealer: Entity? = null
    var buyer: Entity? = null
    var recipient: Entity? = null
    var items: ArrayList<InvoiceItem> = ArrayList()
    var paymentProperty: PaymentProperty = PaymentProperty()
    var sumTable: ArrayList<Summation> = ArrayList()
    var sumProperty: SumProperty = SumProperty(0.0,0.0,0.0)

    fun getPropertiesKeywords() : ArrayList<String> {
        val result = ArrayList<String>()
        result.addAll(dealer!!.getKeywords())
        result.addAll(listOf(id, currDate.toString(), sellDate.toString()))
        result.addAll(buyer!!.getKeywords())
        result.addAll(recipient!!.getKeywords())
        return result
    }

    fun getItemsAsArrayList() : ArrayList<String> {
        val result = ArrayList<String>()
        for (i in items)
            result.add(i.getAsString())
        return result
    }

    fun generateSummation() {
        val partSum = HashMap<Double, Summation>()
        var totalNetto = 0.0
        var totalGros = 0.0
        var totalTax = 0.0
        for (i in items) {
            if (!partSum.contains(i.vat)) {
                partSum.put(i.vat, Summation(i.baseValue,
                    i.vat*i.value,
                    i.grossValue,
                    i.vat))
            } else {
                val s = partSum.get(i.vat)
                s!!.addTotalGros(i.grossValue)
                s.addTotalNetto(i.value)
                s.addTotalVat(i.vat*i.baseValue)
            }
            totalNetto += i.value
            totalGros += i.grossValue
            totalTax += i.value*i.vat
        }
        sumTable.addAll(partSum.values)
        sumProperty!!.netSum = totalNetto
        sumProperty!!.pSum = totalGros
        sumProperty!!.taxSum = totalTax
    }

    fun getSumProperties() : ArrayList<String> {
        //TODO: Wywolaj generateSummation() przed ta metoda!
        val result = ArrayList<String>()
        result.addAll(paymentProperty.toArrayOfString())
        result.add(sumProperty.pSum.toString())
        return result
    }
}