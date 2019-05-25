package com.example.generatorfaktur.invBuilder

import android.content.Context
import com.example.generatorfaktur.Invoice
import com.example.generatorfaktur.invoiceProperties.Entity
import com.example.generatorfaktur.invoiceProperties.InvoiceItem
import com.example.generatorfaktur.strategies.CHTMLExportStrategy
import com.example.generatorfaktur.strategies.HTMLExportStrategy
import com.example.generatorfaktur.strategies.templates.HTMLTemplateType
import java.math.BigDecimal


/*
Builder can contain a lot different strategies,
for example for HTML, XML or JSON. Each of this
could be implemented here as strategy (design pattern).

For now we only need HTMLExportStrategy.
 */
abstract class AbstractInvcBuilder(
    val context: Context
) {
    //call HTMLExport to make invoice on html
    val HTMLExporter: HTMLExportStrategy = CHTMLExportStrategy()
    val invoice = Invoice()

    /**
     * payment properties on invoice
     */
    abstract fun setPaymentProperty(paymentForm: String,
                                    paymentDate: String,
                                    bank: String,
                                    accountNumber: String) : AbstractInvcBuilder

    /**
     * name - name of item on invoice
     * q - quantity of product/service
     * netto - net value of (one) product/service
     * vat - tax value we name in Poland
     */
    abstract fun addInvoiceItem(name: String, q: BigDecimal, netto: BigDecimal, vat: BigDecimal) : InvoiceItem

    /**
     * Sides on invoice
     */
    abstract fun setDealer(dealer: Entity) : AbstractInvcBuilder
    abstract fun setBuyer(buyer: Entity) : AbstractInvcBuilder
    abstract fun setReicipient(recipient: Entity) : AbstractInvcBuilder

    /**
     * currentDate - date of invoice issue
     * id - unique number of invoice (delivered by InvoiceNumber class)
     */
    abstract fun setProperties(currentDate: String,
                               id: String) : AbstractInvcBuilder

    /**
     * posId - unique number of item on invoice
     */
    abstract fun removeItem(posId: Int) : AbstractInvcBuilder


    //for now generate() makes only html type of invoice
    /**
     * Generates HTML String which contains concrete type of invoice based
     * on previously setted properties
     */
    fun generate() : String {
        return HTMLExporter.generateHTML(invoice, HTMLTemplateType.BASIC, context)
    }
}