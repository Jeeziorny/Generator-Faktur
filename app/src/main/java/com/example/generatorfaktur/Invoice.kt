package com.example.generatorfaktur

import com.example.generatorfaktur.invoiceProperties.*
import java.util.*

class Invoice {
    var currentDate: String = ""
    var invoiceId: String = ""

    var dealer: Entity? = null
    var buyer: Entity? = null
    var recipient: Entity? = null

    var items: ArrayList<InvoiceItem> = ArrayList()

    var paymentForm = ""
    var paymentDate = ""
    var bank = ""
    var accountNumber = ""

    var totalNetto = 0.0
    var totalTax = 0.0
    var totalGross = 0.0

    var posIdIterator = 0

    fun getProperties() : ArrayList<String> {
        val result = ArrayList<String>()
        result.addAll(dealer!!.getKeywords())
        result.addAll(listOf(invoiceId, currentDate))
        result.addAll(buyer!!.getKeywords())
        result.addAll(recipient!!.getKeywords())
        result.addAll(listOf(paymentForm, paymentDate, bank, accountNumber))
        result.addAll(listOf("%.2f".format(totalNetto), "%.2f".format(totalTax), "%.2f".format(totalGross)))
        result.add("%.2f".format(totalGross))
        return result
    }

    fun getItemsAsArrayList() : ArrayList<Pair<String, Int>> {
        val result = ArrayList<Pair<String, Int>>()
        for (i in items)
            result.add(i.getAsString())
        return result
    }

    fun addInvoiceItem(itm: InvoiceItem) {
        itm.setItemId(posIdIterator++)
        totalNetto += itm.value
        totalTax += itm.value * itm.vat
        totalGross += itm.value*(1+itm.vat)
        items.add(itm)
    }

    fun removeInvoiceItem(id: Int) {
        var index = -1
        for (i in 0 until items.size) {
            if (id == items[i].id)
                index = i
        }
        if (index != -1) {
            val temp = items.removeAt(index)
            totalNetto -= temp.value
            totalTax -= temp.value * temp.vat
            totalGross -= temp.value*(1+temp.vat)
        }
    }
}