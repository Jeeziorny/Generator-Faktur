package com.example.generatorfaktur.strategies

import android.content.Context
import android.util.Log
import com.example.generatorfaktur.Invoice
import com.example.generatorfaktur.R
import com.example.generatorfaktur.strategies.templates.HTMLSimpleTemplate
import com.example.generatorfaktur.strategies.templates.HTMLTemplateType
import java.io.BufferedReader
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStreamReader

class CHTMLExportStrategy : HTMLExportStrategy  {
    override fun generateHTML(invoice: Invoice,
                              context: Context,
                              type: HTMLTemplateType) {
        when (type) {
            HTMLTemplateType.BASIC ->
                HTMLSimpleTemplate(context, invoice).generate()
        }
    }
}