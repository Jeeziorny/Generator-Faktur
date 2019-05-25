package com.example.generatorfaktur.strategies

import android.content.Context
import com.example.generatorfaktur.Invoice
import com.example.generatorfaktur.strategies.templates.HTMLSimpleTemplate
import com.example.generatorfaktur.strategies.templates.HTMLTemplateType

//Concrete HTMLExportStrategy for better reading
class CHTMLExportStrategy : HTMLExportStrategy  {
    override fun generateHTML(invoice: Invoice,
                              type: HTMLTemplateType,
                              context: Context
    ) : String {
        /*
        generates invoice by template type.
        For now there is only basic template,
        which is connected with HTMLSimpleTemplate
         */
        when (type) {
            HTMLTemplateType.BASIC ->
                return HTMLSimpleTemplate(invoice, context).generate()
        }
    }
}