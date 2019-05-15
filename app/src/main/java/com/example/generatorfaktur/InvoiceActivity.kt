package com.example.generatorfaktur

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import com.beardedhen.androidbootstrap.BootstrapEditText
import kotlinx.android.synthetic.main.invoice_activity.*

class InvoiceActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.invoice_activity)
        supportActionBar!!.hide()
    }

    fun buyerOnClick(view: View) {
        doDialog("buyer")
    }

    fun dealerOnClick(view: View) {
        doDialog("dealer")
    }

    fun doDialog(who: String){
        val li = LayoutInflater.from(this)
        val dialog = li.inflate(R.layout.fab_dialog, null)
        val result = ArrayList<String>()
        val alertDialogBuilder = AlertDialog.Builder(this)

        alertDialogBuilder.setView(dialog)

        alertDialogBuilder
            .setCancelable(true)
            .setNegativeButton("ANULUJ") { _, _ ->

            }
            .setPositiveButton("DODAJ") { _, _ ->
                result.add(dialog.findViewById<BootstrapEditText>(R.id.entityName).text.toString())
                result.add(dialog.findViewById<BootstrapEditText>(R.id.entityNIP).text.toString())
                result.add(dialog.findViewById<BootstrapEditText>(R.id.entityAddress).text.toString())
                result.add(dialog.findViewById<BootstrapEditText>(R.id.entityPostal).text.toString())
                result.add(dialog.findViewById<BootstrapEditText>(R.id.entityPhone).text.toString())
                when (who) {
                    "dealer" -> {
                        setTexts(result, "dealer")
                    }
                    "buyer" -> {
                        setTexts(result, "buyer")
                    }
                }
            }

        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    fun setTexts(data: ArrayList<String>, who: String) {
        when (who) {
            "dealer" -> {
                dealerNameText.text = "Nazwa : ${data[0]}"
                dealerNIPText.text = "NIP : ${data[1]}"
                dealerAdressText.text = "Adres : ${data[2]}"
                dealerPostalText.text = "Kod pocztowy : ${data[3]}"
                dealerPhoneText.text = "Telefon : ${data[4]}"
            }
            "buyer" -> {
                buyerNameText.text = "Nazwa : ${data[0]}"
                buyerNIPText.text = "NIP : ${data[1]}"
                buyerAdressText.text = "Adres : ${data[2]}"
                buyerPostalText.text = "Kod pocztowy : ${data[3]}"
                buyerPhoneText.text = "Telefon : ${data[4]}"
            }
        }
    }

    fun nextOnClick(view: View) {
        val myIntent = Intent(this, ItemActivity::class.java)
        startActivityForResult(myIntent, 999)
    }
}