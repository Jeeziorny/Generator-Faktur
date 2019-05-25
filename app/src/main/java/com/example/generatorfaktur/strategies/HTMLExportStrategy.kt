package com.example.generatorfaktur.strategies

import android.content.Context
import com.example.generatorfaktur.Invoice
import com.example.generatorfaktur.strategies.templates.HTMLTemplateType

interface HTMLExportStrategy {
    /*
    Type of strategy used in AbstractInvcBuilder
     */
    /**
     * Delegates generating invoice html to concrete template
     * based on enum HTMLTemplateType.
     */
    //context is needed for calling files in raw directory
    fun generateHTML(invoice: Invoice, type: HTMLTemplateType, context: Context) : String
}