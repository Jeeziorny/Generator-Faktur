package com.example.generatorfaktur.itmBuilder

import com.example.generatorfaktur.Invoice

abstract class AbstractItmBuilder(
    var invoice: Invoice
) {
    abstract fun setName(name: String) : Invoice
    abstract fun setUnit(unit: String) : Invoice
    abstract fun setQuantity(q: Double) : Invoice
    abstract fun setVat(vat: Double) : Invoice
    abstract fun setValue(value: Double): Invoice
    abstract fun setVatValue(vv: Double) : Invoice
    abstract fun setGrossValue(gv: Double) : Invoice

    abstract fun build() : Invoice
}