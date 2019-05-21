package com.example.generatorfaktur.strategies

import android.content.Context
import com.example.generatorfaktur.Invoice
import com.example.generatorfaktur.strategies.templates.HTMLTemplateType

interface HTMLExportStrategy {
    fun generateHTML(invoice: Invoice, type: HTMLTemplateType, context: Context) : String
}