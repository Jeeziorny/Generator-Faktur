package com.example.generatorfaktur.invBuilder

import android.content.Context
import com.example.generatorfaktur.invoiceProperties.Entity
import com.example.generatorfaktur.invoiceProperties.InvoiceItem
import java.math.BigDecimal

class InvcBuilder(context: Context) : AbstractInvcBuilder(context) {

    override fun removeItem(posId: Int): AbstractInvcBuilder {
        invoice.removeInvoiceItem(posId)
        return this
    }

    override fun addInvoiceItem(name: String, q: BigDecimal, netto: BigDecimal, vat: BigDecimal): InvoiceItem {
        //value is equal to price per item on invoice multiplied by quantity
        //gross value is equal to value with tax
        //
        //for now id of item is equal to 0. It changes when added to invoice
        val invoiceItem = InvoiceItem(name, q, netto, netto*q, vat, (netto*q)*(BigDecimal(1)+vat), 0)
        invoice.addInvoiceItem(invoiceItem)
        return invoiceItem
    }

    //podaj nip

    override fun setPaymentProperty(
        paymentForm: String,
        paymentDate: String,
        bank: String,
        accountNumber: String
    ): AbstractInvcBuilder {
        invoice.paymentForm = paymentForm
        invoice.paymentDate = paymentDate
        invoice.bank = bank
        invoice.accountNumber = accountNumber
        return this
    }

    override fun setDealer(dealer: Entity): AbstractInvcBuilder {
        invoice.dealer = dealer
        return this
    }

    override fun setBuyer(buyer: Entity): AbstractInvcBuilder {
        invoice.buyer = buyer
        return this
    }

    override fun setReicipient(recipient: Entity): AbstractInvcBuilder {
        invoice.recipient = recipient
        return this
    }

    override fun setProperties(currentDate: String, id: String): AbstractInvcBuilder {
        invoice.currentDate = currentDate
        invoice.invoiceId = id
        return this
    }
}