package com.example.generatorfaktur.invBuilder

import android.content.Context
import com.example.generatorfaktur.Invoice
import com.example.generatorfaktur.invoiceProperties.Entity
import com.example.generatorfaktur.invoiceProperties.InvoiceItem
import com.example.generatorfaktur.strategies.CHTMLExportStrategy
import com.example.generatorfaktur.strategies.HTMLExportStrategy
import com.example.generatorfaktur.strategies.templates.HTMLTemplateType
import java.math.BigDecimal

abstract class AbstractInvcBuilder(
    val context: Context
) {
    //TODO:
    /*
    Builder moze zawierac wiele strategii, np.: HTML, XML, JSON.
    W razie potrzeby mozna dodac inne, ktore implementuja interfejs
    <typ>ExportStrategy.
     */
    val HTMLExporter: HTMLExportStrategy = CHTMLExportStrategy()
    val invoice = Invoice()
    abstract fun setPaymentProperty(paymentForm: String,
                                    paymentDate: String,
                                    bank: String,
                                    accountNumber: String) : AbstractInvcBuilder
    //abstract fun addInvoiceItem(name: String, q: Double, netto: Double, vat: Double) : AbstractInvcBuilder
    abstract fun addInvoiceItem(name: String, q: BigDecimal, netto: BigDecimal, vat: BigDecimal) : InvoiceItem
    abstract fun setDealer(dealer: Entity) : AbstractInvcBuilder
    abstract fun setBuyer(buyer: Entity) : AbstractInvcBuilder
    abstract fun setReicipient(recipient: Entity) : AbstractInvcBuilder
    abstract fun setProperties(currentDate: String,
                               id: String) : AbstractInvcBuilder
    abstract fun removeItem(posId: Int) : AbstractInvcBuilder

    fun generate() : String {
        return HTMLExporter.generateHTML(invoice, HTMLTemplateType.BASIC, context)
/*
        in future there could be also e.g.
        XMLExporter.generateXML(inovice, XMLTemplateType.BASIC)
*/
    }
}