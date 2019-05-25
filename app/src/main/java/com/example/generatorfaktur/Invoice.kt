package com.example.generatorfaktur

import com.example.generatorfaktur.invoiceProperties.*
import java.math.BigDecimal
import java.text.DecimalFormat
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

    var totalNetto = BigDecimal(0)
    var totalTax = BigDecimal(0)
    var totalGross = BigDecimal(0)

    //it is used in setting invoice item Id
    var posIdIterator = 0

    /**
     * returns all properties as ArrayList
     */
    fun getProperties(): ArrayList<String> {
        val df = DecimalFormat("#.00")
        val result = ArrayList<String>()
        result.addAll(dealer!!.getKeywords())
        result.addAll(listOf(invoiceId, currentDate))
        result.addAll(buyer!!.getKeywords())
        result.addAll(recipient!!.getKeywords())
        result.addAll(listOf(paymentForm, paymentDate, bank, accountNumber))
        result.addAll(listOf(df.format(totalNetto), df.format(totalTax), df.format(totalGross)))
        result.add(df.format(totalGross))
        return result
    }

    /**
     * returns all items as ArrayList
     */
    fun getItemsAsArrayList(): ArrayList<Pair<String, Int>> {
        val result = ArrayList<Pair<String, Int>>()
        for (i in items)
            result.add(i.getAsString())
        return result
    }

    fun addInvoiceItem(itm: InvoiceItem) {
        itm.setItemId(posIdIterator++)
        totalNetto = totalNetto.add(itm.value)
        totalTax = totalTax.add(itm.value.multiply( itm.vat))
        totalGross =totalGross.add(itm.value.multiply(BigDecimal(1).add( itm.vat)))
        items.add(itm)
    }

    fun removeInvoiceItem(id: Int) {
        var index = -1
        for (i in 0 until items.size) {
            if (id == items[i].id)
                index = i
        }
        if (index != -1) {
            //when removing subtract value from total, tax and gross.
            val temp = items.removeAt(index)
            totalNetto =totalNetto.subtract(temp.value)
            totalTax = totalTax.subtract(temp.value.multiply(temp.vat))
            totalGross = totalGross.subtract(temp.value.multiply(BigDecimal(1).add( temp.vat)))
        }
    }
}