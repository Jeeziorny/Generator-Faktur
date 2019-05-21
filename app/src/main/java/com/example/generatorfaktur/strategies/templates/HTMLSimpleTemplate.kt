package com.example.generatorfaktur.strategies.templates

import android.content.Context
import android.util.Log
import com.example.generatorfaktur.Invoice
import com.example.generatorfaktur.R
import java.io.*




class HTMLSimpleTemplate (
    val invoice: Invoice,
    val context: Context
) : HTMLTemplate{
    var base = StringBuilder()
    var items = ArrayList<Pair<StringBuilder, Int>>()
    var table = StringBuilder()
    var result = String()

    //Przerwa pomiedzy stronami PDF w przypadku, gdy nie miesci sie na jednej
    var pageGap = 2


    //TODO: zamien na big decimal
    override fun generate() : String {
        setProperties()
        setIvnoiceItems()
        setTable()
        assembly()
        export()
        return result
    }

    fun export() {
        //TODO: enkodowanie
        val file = File(context.filesDir, "myFile.html")
        file.createNewFile()
        file.bufferedWriter().use { out ->
            out.write(result)
        }
    }

    fun assembly() {
        result = base.toString()
        result = result.replaceFirst("<!-- Table -->", table.toString())
    }

    fun setTable() {
        var prefix = ""
        var counter = 0
        //TODO: boundValue to liczba wierszy, ktora miesci sie na pierwszej stronie
        //Po pierwszej iteracji petli zewnetrznej ustawiana jest na inna wartosc,
        //ktora jest dopasowana do calej strony.
        var boundValue = 18
        val acc = StringBuilder()
        //val tableFile = "E:\\git\\studies\\TS\\testy\\resources\\table.txt"
        //var tableTemplate = File(tableFile).readText()
        val inputStream = context.resources.openRawResource(R.raw.tablecontent)
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
    }

    fun insertGap(template: String) : String {
        val inputStream = context.resources.openRawResource(R.raw.tablecontent)
        val inputreader = InputStreamReader(inputStream)
        val buffreader = BufferedReader(inputreader)
        var separator = buffreader.readText()
        var sepContent = ""
        for (i in 0..pageGap)
            sepContent += "<br>"
        separator = separator.replaceFirst("!!Sep!!", sepContent)
        return template.replaceFirst("<!-- Separator -->", separator)
    }

    fun setIvnoiceItems() {
        //itemki. Drugi parametr to wysokosc, jezeli nie miesci sie w jednym wierszu np.
        //sprawdz czy dziala tak jak chcesz.
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
            row = row.replaceFirst("!c!", cells[1], false)
            row = row.replaceFirst("!n!", cells[2], false)
            row = row.replaceFirst("!nv!", cells[3], false)
            row = row.replaceFirst("!tv!", cells[4], false)
            row = row.replaceFirst("!gv!", cells[5], false)
            items.add(Pair(StringBuilder(row), p.second))
            row = rowTemplate
            rowIterator++
        }
    }

    fun setProperties() {
    //ta funkcja jest przetestowana i dziala ok.
        val invoiceProperties = invoice.getProperties()
        invoiceProperties.add("12312312")
        val inputStream = context.resources.openRawResource(R.raw.base)
        val inputreader = InputStreamReader(inputStream)
        val buffreader = BufferedReader(inputreader)
        var lines = buffreader.readLines()
        var begOfKeyword = 0
        var endOfKeyword = 0
        var lastPos = 0
        var temp = ""
        lines.forEach {
            begOfKeyword = it.indexOf("!!", 0, false)
            temp = it
            if (begOfKeyword != -1) {
                endOfKeyword = it.indexOf("!!", begOfKeyword + 1, false) + 2
                temp = it.replaceRange(begOfKeyword, endOfKeyword, invoiceProperties[lastPos])
                lastPos++
            }
            base.append(temp)
        }
    }
}