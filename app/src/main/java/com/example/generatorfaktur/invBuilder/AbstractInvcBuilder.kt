package com.example.generatorfaktur.invBuilder

import com.example.generatorfaktur.invoiceProperties.Entity
import com.example.generatorfaktur.invoiceProperties.InvoiceItem
import com.example.generatorfaktur.strategies.HTMLExportStrategy
import java.util.*

abstract class AbstractInvcBuilder(
    val HTMLExporter: HTMLExportStrategy

) {
    //builder interface
    abstract fun setPaymentProperty(paymentForm: String,
                                    paymentDate: Date,
                                    bank: String,
                                    accountNumber: String) :    AbstractInvcBuilder
    abstract fun addInvoiceItem(item: InvoiceItem) :            AbstractInvcBuilder
    abstract fun setDealer(dealer: Entity) :                    AbstractInvcBuilder
    abstract fun setBuyer(buyer: Entity) :                      AbstractInvcBuilder
    abstract fun setReicipient(recipient: Entity) :             AbstractInvcBuilder
    abstract fun setProperties(currentDate: Date,
                               id: String,
                               issuePlace: String) :            AbstractInvcBuilder

    //TODO: usuwanie z parametrem typu InvoiceItem:
    //TODO: user wybiera opcje: pokaz elementy na fakturze,
    //TODO: longClick daje mozliwosc usuniecia zaznaczonego,
    //TODO: mamy referencje, mozna usuwac.
    abstract fun removeItem(itm: InvoiceItem) :                 AbstractInvcBuilder
    /*
    example call
    builder.setDealer(dealer)
            .setBuyer(buyer)
            .setProperties(properties);
     */
    //builder private
    abstract fun createSumTable()
    abstract fun createSumProperty()
}