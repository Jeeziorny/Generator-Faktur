package com.example.generatorfaktur

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.generatorfaktur.invBuilder.AbstractInvcBuilder
import com.example.generatorfaktur.invBuilder.InvcBuilder
import com.example.generatorfaktur.invoiceProperties.Entity
import com.example.generatorfaktur.invoiceProperties.InvoiceItem
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val builder: AbstractInvcBuilder = InvcBuilder(applicationContext)

        val buyer = Entity("Tomek", "Stodola", "64-600", "432432423")
        val seller = Entity("Tomek", "Stodola", "64-600", "432432423")
        val reicipient = Entity("Tomek", "Stodola", "64-600", "432432423")

        builder.setBuyer(buyer).setDealer(seller).setReicipient(reicipient)

        builder.setProperties("23-04-1004", "siema")
        builder.setPaymentProperty("a", "22-33-4444", "c", "d")
        builder.addInvoiceItem("cebula", 2.0, 4.0, 0.23)

        builder.generate()
    }
}
