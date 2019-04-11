package com.example.generatorfaktur.invBuilder

import com.example.generatorfaktur.Invoice
import com.example.generatorfaktur.invoiceProperties.Entity
import com.example.generatorfaktur.invoiceProperties.InvoiceItem
import com.example.generatorfaktur.strategies.DBExportStrategy
import com.example.generatorfaktur.strategies.DBImportStrategy
import com.example.generatorfaktur.strategies.HTMLExportStrategy
import java.util.*

abstract class AbstractInvcBuilder(
    val DBExporter: DBExportStrategy,
    val DBImporter: DBImportStrategy,
    val HTMLExporter: HTMLExportStrategy

) {
    abstract fun setPaymentProperty(paymentForm: String,
                                    paymentDate: Date,
                                    bank: String,
                                    accountNumber: String) : Invoice
    abstract fun addInvoiceItem(item: InvoiceItem) :            Invoice
    abstract fun setDealer(dealer: Entity) :                    Invoice
    abstract fun setBuyer(buyer: Entity) :                      Invoice
    abstract fun setReicipient(reicipient: Entity) :            Invoice
    abstract fun setProperties(currentDate: Date,
                               id: String,
                               issuePlace: String) :            Invoice
    abstract fun createSumTable()
    abstract fun createSumProperty()
}

/*
    val sumTable: Summation,
    val sumProperty: SumProperty
 */