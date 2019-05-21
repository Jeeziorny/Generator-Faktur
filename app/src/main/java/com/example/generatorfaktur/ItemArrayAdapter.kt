package com.example.generatorfaktur

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.generatorfaktur.invoiceProperties.InvoiceItem

class ItemArrayAdapter(context : Context, var data : ArrayList<InvoiceItem>) :
    ArrayAdapter<InvoiceItem>(context, R.layout.invoice_item, data){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView

        if (view == null) {
            val inflater = LayoutInflater.from(context)
            view = inflater.inflate(R.layout.invoice_item, parent, false)
        }

        val item = data[position]

        view!!.findViewById<TextView>(R.id.itemNameText).text = item.name
        view.findViewById<TextView>(R.id.quantityText).text = item.quantity.toString()
        view.findViewById<TextView>(R.id.vatText).text = item.vat.toString()

        return view
    }
}