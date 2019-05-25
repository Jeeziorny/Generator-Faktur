package com.example.generatorfaktur.invoiceProperties

import java.math.BigDecimal
import java.text.DecimalFormat

class InvoiceItem (
    val name: String,
    val quantity: BigDecimal,

    /** price per one without tax */
    val baseValue: BigDecimal,

    /** price multiplied by quantity without tax */
    var value: BigDecimal,

    /** tax */
    val vat: BigDecimal,

    /** baseValue with tax*/
    var grossValue: BigDecimal,
    var id: Int
) {

    fun setValue() {
        value = quantity.multiply(baseValue)
    }

    fun setItemId(id: Int) {
        this.id = id
    }

    /**
     * return semicolon separated list of properties of InvoiceItem
     * Additionally inserts <br> for each 27 chars, for better look
     * of invoice item name.
     */
    fun getAsString() : Pair<String, Int> {
        val p = insertPeriodically(name, "<br>", 27)
        val newName = p.first
        val height = p.second
        val df = DecimalFormat("#.00")
        val q = df.format(quantity)
        val b = df.format(baseValue)
        val v = df.format(value)
        val t = DecimalFormat("#%").format(vat)
        val g = df.format(grossValue)
        return Pair("$newName;$q;$b;$v;$t;$g", height)
    }

    /**
     * Inserts <br> for each 27 chars
     * Returns result String with information (Int) about how many
     * lines it contains.
     *
     * <br> is HTML separator responsible for splitting line of text
     * to two lines when inserted once
     */
    fun insertPeriodically(
        text: String, insert: String, period: Int
    ): Pair<String, Int> {
        //                                                                 |<- how many <br> it will insert ->|
        val builder = StringBuilder(text.length + insert.length * (text.length / period) + 1)
        var index = 0
        var prefix = ""
        var insertionCounter = 0
        while (index < text.length) {
            builder.append(prefix)
            prefix = insert
            //Math.min when there is less letters in text than period
            builder.append(text.substring(index, Math.min(index + period, text.length)))
            index += period
            insertionCounter++
        }
        return Pair(builder.toString(), insertionCounter)
    }
}