package com.example.generatorfaktur.strategies.templates

import android.content.Context
import com.example.generatorfaktur.Invoice
import com.example.generatorfaktur.R
import java.io.*




class HTMLSimpleTemplate (
    val invoice: Invoice,
    val context: Context
) : HTMLTemplate{
    /** result html string */
    var base = StringBuilder()

    /** items as resulting html with information how many lines it contains */
    var items = ArrayList<Pair<StringBuilder, Int>>()

    var table = StringBuilder()
    var result = String()

    //Gap between pages in terms of new lines (for now is 15 lines)
    var pageGap = 15

    override fun generate() : String {
        setProperties()
        setIvnoiceItems()
        setTable()
        assembly()
        export()
        return result
    }

    fun export() {
        //TODO: idk to chyba niepotrzebne Matik, i tak zwracam Ci Stringa
        val file = File(context.filesDir, "myFile.html")
        file.createNewFile()
        file.bufferedWriter().use { out ->
            out.write(result)
        }
    }

    private fun assembly() {
        result = base.toString()
        result = result.replaceFirst("<!-- Table -->", table.toString())
    }

    private fun setTable() {
        val prefix = ""
        var counter = 0
        /*
        boundValue is limit number of lines on page. When items takes more
        boundValue indicates to switch to another page with new table.
         */
        var boundValue = 21
        val acc = StringBuilder()

        //taking html table template from raw
        val inputStream = context.resources.openRawResource(R.raw.table)
        val inputreader = InputStreamReader(inputStream)
        val buffreader = BufferedReader(inputreader)
        var tableTemplate = buffreader.readText()

        var i = 0
        while (i < items.size) {
            while (counter < boundValue && i < items.size) {
                acc.append(items[i].first)
                counter += items[i].second
                i++
            }
            table.append(tableTemplate.replaceFirst("<!-- Items -->", acc.toString()))
            tableTemplate = insertGap(tableTemplate)
            acc.clear()
            acc.append(prefix)
            counter = 0
            boundValue = 39
        }
        /*
        - tableTemplate
        Represents one table with headers for each column

        - table
        Contains multiple tableTemplates which are filled
        with concrete invoiceItems. It was needed, because printer
        cuts tables in wrong place, so I need to insert page separators
         */
    }

    private fun insertGap(template: String) : String {
        //page separator is template for separating page (row of main table)
        val inputStream = context.resources.openRawResource(R.raw.pageseparator)
        val inputreader = InputStreamReader(inputStream)
        val buffreader = BufferedReader(inputreader)
        var separator = buffreader.readText()
        var sepContent = ""
        for (i in 0..pageGap)
            sepContent += "<br>"

        //replaces "!!Sep!!" in separator to concrete number of <br>
        //(you could choose how many lines will separate pages on invoice PDF)
        separator = separator.replaceFirst("!!Sep!!", sepContent)
        return template.replaceFirst("<!-- Separator -->", separator)
    }

    private fun setIvnoiceItems() {
        val positions = invoice.getItemsAsArrayList()
        val inputStream = context.resources.openRawResource(R.raw.tablecontent)
        val inputreader = InputStreamReader(inputStream)
        val buffreader = BufferedReader(inputreader)
        val rowTemplate = buffreader.readText()

        var row = rowTemplate
        var cells: ArrayList<String>
        var rowIterator = 1
        for (p in positions) {
            cells = ArrayList(p.first.split(";"))
            row = row.replaceFirst("!i!", rowIterator.toString(), false)
            row = row.replaceFirst("!s/p!", cells[0], false)
            row = row.replaceFirst("!c!",  cells[1], false)
            row = row.replaceFirst("!n!", cells[2], false)
            row = row.replaceFirst("!nv!", cells[3], false)
            row = row.replaceFirst("!tv!", cells[4], false)
            row = row.replaceFirst("!gv!", cells[5], false)

            //insert to items field of class resulting htmls of each item in table.
            items.add(Pair(StringBuilder(row), p.second))
            row = rowTemplate
            rowIterator++
        }
    }

    //sets properties of Invoice (e.g. current date)
    private fun setProperties() {
        val invoiceProperties = invoice.getProperties()
        invoiceProperties.add("12312312")
        val inputStream = context.resources.openRawResource(R.raw.base)
        val inputreader = InputStreamReader(inputStream)
        val buffreader = BufferedReader(inputreader)
        val lines = buffreader.readLines()
        var begOfKeyword: Int
        var endOfKeyword: Int
        var lastPos = 0
        var temp: String
        lines.forEach {
            //when it finds "!!" it is signal, that word to replace begin
            //-1 when it won't find !!.
            begOfKeyword = it.indexOf("!!", 0, false)
            temp = it
            if (begOfKeyword != -1) {
                //take index of end of keyword
                endOfKeyword = it.indexOf("!!", begOfKeyword + 1, false) + 2
                //replace current world with next in invoiceProperties ArrayList
                temp = it.replaceRange(begOfKeyword, endOfKeyword, invoiceProperties[lastPos])
                lastPos++
            }
            //it iterates once and each resulting line puts to base String
            base.append(temp)
        }
    }
}