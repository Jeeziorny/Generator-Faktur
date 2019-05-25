package com.example.generatorfaktur.invoiceProperties

import java.math.BigDecimal
import java.text.DecimalFormat

class InvoiceItem (
    val name: String,
    val quantity: BigDecimal,
    val baseValue: BigDecimal,
    var value: BigDecimal,
    val vat: BigDecimal,
    var grossValue: BigDecimal,
    var id: Int
) {

    fun setValue() {
        value = quantity.multiply( baseValue)
    }

    fun setItemId(id: Int) {
        this.id = id
    }

    //TODO: aby latwo rozdzielic: string.split(";")
    fun getAsString() : Pair<String, Int> {
        val p = insertPeriodically(name, "<br>", 27)
        val newName = p.first
        val height = p.second
        val df = DecimalFormat("#.00")
        val q = df.format(quantity)
        val b = df.format(baseValue)
        val v = df.format(value)
        val t = DecimalFormat("#").format(vat.multiply(BigDecimal(100)))
        val g = df.format(grossValue)
        return Pair("$newName;$q;$b;$v;$t%;$g", height)
    }

    fun insertPeriodically(
        text: String, insert: String, period: Int
    ): Pair<String, Int> {
        val builder = StringBuilder(text.length + insert.length * (text.length / period) + 1)
        var index = 0
        var prefix = ""
        var insertionCounter = 0
        while (index < text.length) {
            builder.append(prefix)
            prefix = insert
            builder.append(text.substring(index, Math.min(index + period, text.length)))
            index += period
            insertionCounter++
        }
        return Pair(builder.toString(), insertionCounter)
    }
}