package com.example.generatorfaktur

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class InvoiceActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.invoice_activity_buyer)
        supportActionBar!!.hide()
    }
}