package com.example.generatorfaktur.invoiceProperties

class InvoiceItem (
    val name: String,
    val quantity: Double,
    val baseValue: Double,
    var value: Double,
    val vat: Double,
    var grossValue: Double,
    var id: Int
) {

    fun setValue() {
        value = quantity * baseValue
    }

    fun setItemId(id: Int) {
        this.id = id
    }

    //TODO: aby latwo rozdzielic: string.split(";")
    fun getAsString() : Pair<String, Int> {
        val p = insertPeriodically(name, "<br>", 27)
        val newName = p.first
        val height = p.second
        val q = "%.2f".format(quantity)
        val b = "%.2f".format(baseValue)
        val v = "%.2f".format(value)
        val t = (vat*100).toInt()
        val g = "%.2f".format(grossValue)
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