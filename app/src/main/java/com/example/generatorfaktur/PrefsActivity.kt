package com.example.generatorfaktur

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import com.beardedhen.androidbootstrap.BootstrapEditText
import kotlinx.android.synthetic.main.invoice_activity.*

class PrefsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.prefs_activity)

    }

    fun dealerOnClick(view: View) {
        val li = LayoutInflater.from(this)
        val dialog = li.inflate(R.layout.fab_dialog, null)
        val result = ArrayList<String>()
        val alertDialogBuilder = AlertDialog.Builder(this, R.style.FABDialog)

        alertDialogBuilder.setView(dialog)

        alertDialogBuilder
            .setCancelable(true)
            .setPositiveButton("DODAJ") { _, _ ->
                result.add(dialog.findViewById<BootstrapEditText>(R.id.entityName).text.toString())
                result.add(dialog.findViewById<BootstrapEditText>(R.id.entityNIP).text.toString())
                result.add(dialog.findViewById<BootstrapEditText>(R.id.entityAddress).text.toString())
                result.add(dialog.findViewById<BootstrapEditText>(R.id.entityPostal).text.toString())
                result.add(dialog.findViewById<BootstrapEditText>(R.id.entityPhone).text.toString())
                setTexts(result)
            }

        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    fun setTexts(data: ArrayList<String>) {

        dealerNameText.text = "Nazwa : ${data[0]}"
        dealerNIPText.text = "NIP : ${data[1]}"
        dealerAdressText.text = "Adres : ${data[2]}"
        dealerPostalText.text = "Kod pocztowy : ${data[3]}"
        dealerPhoneText.text = "Telefon : ${data[4]}"

    }

    fun confirmOnClick(view: View) {
        val myIntent = Intent(this, MainActivity::class.java)
        startActivity(myIntent)
    }
}