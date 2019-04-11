package com.example.generatorfaktur.invBuilder

import com.example.generatorfaktur.invoiceProperties.Entity
import com.example.generatorfaktur.invoiceProperties.InvoiceItem
import com.example.generatorfaktur.strategies.HTMLExportStrategy
import java.util.*

class InvcBuilder(e: HTMLExportStrategy) : AbstractInvcBuilder(e) {

    override fun setPaymentProperty(
        paymentForm: String,
        paymentDate: Date,
        bank: String,
        accountNumber: String
    ): AbstractInvcBuilder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun addInvoiceItem(item: InvoiceItem): AbstractInvcBuilder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setDealer(dealer: Entity): AbstractInvcBuilder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setBuyer(buyer: Entity): AbstractInvcBuilder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setReicipient(recipient: Entity): AbstractInvcBuilder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setProperties(currentDate: Date, id: String, issuePlace: String): AbstractInvcBuilder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun removeItem(itm: InvoiceItem): AbstractInvcBuilder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun createSumTable() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun createSumProperty() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}