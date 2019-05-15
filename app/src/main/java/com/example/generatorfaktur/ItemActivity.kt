package com.example.generatorfaktur

import android.content.ClipData
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import com.example.generatorfaktur.invoiceProperties.InvoiceItem
import kotlinx.android.synthetic.main.item_list_activity.*

class ItemActivity : AppCompatActivity() {

    var itemList = ArrayList<InvoiceItem>()
    private lateinit var itemArrayAdapter: ItemArrayAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.item_list_activity)
        supportActionBar!!.hide()

        itemArrayAdapter = ItemArrayAdapter(this, itemList)
        itemListView.adapter = itemArrayAdapter

        itemList.add(InvoiceItem("name","unit", 1.2, 40.0, 100.0, 40.0, 140.0))

    }

    fun itemFABOnClick(view: View) {
        val li = LayoutInflater.from(this)
        val dialog = li.inflate(R.layout.item_dialog, null)

        val alertDialogBuilder = AlertDialog.Builder(this)

        alertDialogBuilder.setView(dialog)

        alertDialogBuilder
            .setCancelable(true)
            .setPositiveButton("DODAJ") {  _, _ ->

                addItem()

                Snackbar.make(view, "Dodano przedmiot.", Snackbar.LENGTH_SHORT)
                    .setAction("Action", null).show()

                itemArrayAdapter.notifyDataSetChanged()
            }
            .setNegativeButton("ANULUJ") { _, _ ->

            }

        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()

    }



    fun addItem() {

    }
}