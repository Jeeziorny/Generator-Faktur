package com.example.generatorfaktur.strategies

import android.content.Context
import com.example.generatorfaktur.Invoice
import com.example.generatorfaktur.strategies.templates.HTMLSimpleTemplate
import com.example.generatorfaktur.strategies.templates.HTMLTemplateType

class CHTMLExportStrategy : HTMLExportStrategy  {
    //Concrete HTML Export Strategy
    override fun generateHTML(invoice: Invoice,
                              type: HTMLTemplateType,
                              context: Context
    ) {
        /*
        generates invoice by template type.
         */
        when (type) {
            HTMLTemplateType.BASIC ->
                HTMLSimpleTemplate(invoice, context).generate()
        }
    }
}