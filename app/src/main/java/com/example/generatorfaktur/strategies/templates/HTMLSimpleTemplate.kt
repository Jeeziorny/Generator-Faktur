package com.example.generatorfaktur.strategies.templates

import android.content.Context
import com.example.generatorfaktur.Invoice
import com.example.generatorfaktur.R
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

class HTMLSimpleTemplate (
    val context: Context,
    val invoice: Invoice
) : HTMLTemplate{
    var result = String()
    var properties = StringBuilder()
    var items = StringBuilder()
    var summation = StringBuilder()
    var sumItems = StringBuilder()

    override fun generate() {
        invoice.generateSummation()
        setProperties()
        setIvnoiceItems()
        setSumItems()
        setSummation()
        assembly()
    }

    fun assembly() {
        result = properties.toString()
        result.replaceFirst("<!--ItemListBegin -->", items.toString())
        result.replaceFirst("<!--SummationBegin-->", )
    }

/*
    TODO:
    properties zawiera items i summation.
    Summation zawiera sumItems.

    Calosc sklada sie na gotowy plik html.

    Sklej go poprawnie i zacznij poprawiac bledy.
    W celu testowania calego bubla musisz zadbac
    o klase AbstractItmBuilder i ConcreteItemBuilder.

    Nastepnie w mainie ustawiasz builderem wszystkie
    komponenty Invoice korzystajac z buildera.
    Klikasz builder.generate() i powinienes otrzymac gotowy plik HTML.

    Zwroc uwage, ze trzeba zajac sie eksportem gotowego pliku do
    folderu assets.

    Testy, Testy, Testy

    - Zamien Double na BigDecimal,

    Zastanow sie nad sytuacja, w ktorej tabela z pozycjami
    bedzie miala wiecej rekordow i wyjedzie poza strone.

    - zbadaj graniczna liczbe rekordow,
    - Wyodrebnienie tabeli z pozycjami pozwoli
      odtworzyc taka sama na kolejnej stronie
    - wystaw odpowiedni interfejs do budowania faktury
*/

    fun setSumItems() {
        val positions = invoice.sumTable
        val inputStream =
            context.resources.openRawResource(R.raw.sumTableItem)
        val inputReader = InputStreamReader(inputStream)
        val br = BufferedReader(inputReader)
        val rowTemplate = br.use {it.readText()}
        var row = rowTemplate
        for (p in positions) {
            row.replaceFirst("!ns!", p.totalNetto.toString(), false)
            row.replaceFirst("!t!", p.vat.toString(), false)
            row.replaceFirst("!ts!", p.totalVat.toString(), false)
            row.replaceFirst("!pS!", p.totalGros.toString(), false)
            sumItems.append(row)
            row = rowTemplate
        }
    }


    fun setSummation() {
        val sumProperties = invoice.getSumProperties()
        val inputStream =
            context.resources.openRawResource(R.raw.summation)
        val inputReader = InputStreamReader(inputStream)
        val br = BufferedReader(inputReader)
        var lastPos = 0
        var begOfKeyword = 0
        var endOfKeyword = 0
        br.useLines { lines -> {
            lines.forEach {
                begOfKeyword = it.indexOf("!!", 0, false)
                if (begOfKeyword != -1) {
                    endOfKeyword = it.indexOf("!!", begOfKeyword + 1, false)
                    it.replaceRange(begOfKeyword, endOfKeyword, sumProperties[lastPos])
                    lastPos++
                }
                summation.append(it)
            }
        }}
    }

    fun setIvnoiceItems() {
        val positions = invoice.getItemsAsArrayList()
        val inputStream =
            context.resources.openRawResource(R.raw.tableItemRow)
        val inputReader = InputStreamReader(inputStream)
        val br = BufferedReader(inputReader)
        val rowTemplate = br.use {it.readText()}
        var row = rowTemplate
        var cells: ArrayList<String>
        var rowIterator = 0
        for (p in positions) {
            cells = ArrayList(p.split(";"))
            row.replaceFirst("!i!", rowIterator.toString(), false)
            row.replaceFirst("!s/p!", cells[0], false)
            row.replaceFirst("!c!", cells[1], false)
            row.replaceFirst("!n!", cells[2], false)
            row.replaceFirst("!nv!", cells[3], false)
            row.replaceFirst("!tv!", cells[4], false)
            row.replaceFirst("!gv!", cells[5], false)
            items.append(row)
            row = rowTemplate
            rowIterator++
        }
    }

    fun setProperties() {
        val invoiceProperties
                = invoice.getPropertiesKeywords()
        val inputStream =
            context.resources.openRawResource(R.raw.result)
        val inputReader = InputStreamReader(inputStream)
        val br = BufferedReader(inputReader)
        var lastPos = 0
        var begOfKeyword = 0
        var endOfKeyword = 0
        br.useLines { lines -> {
            lines.forEach {
                begOfKeyword = it.indexOf("!!", 0, false)
                if (begOfKeyword != -1) {
                    endOfKeyword = it.indexOf("!!", begOfKeyword + 1, false)
                    it.replaceRange(begOfKeyword, endOfKeyword, invoiceProperties[lastPos])
                    lastPos++
                }
                properties.append(it)
            }
        }}
    }
}