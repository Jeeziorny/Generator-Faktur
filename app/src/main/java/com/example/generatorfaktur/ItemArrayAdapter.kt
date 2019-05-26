package com.example.generatorfaktur

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.generatorfaktur.invoiceProperties.InvoiceItem
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*

class ItemArrayAdapter(context : Context, var data : ArrayList<InvoiceItem>) :
    ArrayAdapter<InvoiceItem>(context, R.layout.invoice_item, data){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView

        if (view == null) {
            val inflater = LayoutInflater.from(context)
            view = inflater.inflate(R.layout.invoice_item, parent, false)
        }

        val item = data[position]

        //TODO zaokrąglanie do 2 miejsc po przecinku

        val symbols = DecimalFormatSymbols(Locale.getDefault())
        symbols.decimalSeparator = '.'
        val df = DecimalFormat("#.00",symbols)
        view!!.findViewById<TextView>(R.id.itemNameText).text = item.name
        view.findViewById<TextView>(R.id.quantityText).text = df.format(item.quantity)
        view.findViewById<TextView>(R.id.vatText).text = DecimalFormat("#%").format(item.vat)
        view.findViewById<TextView>(R.id.priceText).text = df.format(item.baseValue)

        return view
    }

}