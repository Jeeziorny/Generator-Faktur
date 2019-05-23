package com.example.generatorfaktur

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import com.beardedhen.androidbootstrap.BootstrapEditText
import com.example.generatorfaktur.DBManager.SellerData
import com.example.generatorfaktur.invoiceProperties.Entity
import kotlinx.android.synthetic.main.invoice_activity.*

class PrefsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.prefs_activity)

    }

    fun dealerOnClick(view: View) {
        val li = LayoutInflater.from(this)
        val dialog = li.inflate(R.layout.fab_dialog, null)
        val alertDialogBuilder = AlertDialog.Builder(this, R.style.FABDialog)

        alertDialogBuilder.setView(dialog)

        alertDialogBuilder
            .setCancelable(true)
            .setPositiveButton("DODAJ") { _, _ ->
                val entity = Entity()
                entity.name=dialog.findViewById<BootstrapEditText>(R.id.entityName).text.toString()
                entity.nip=dialog.findViewById<BootstrapEditText>(R.id.entityNIP).text.toString()
                entity.address=dialog.findViewById<BootstrapEditText>(R.id.entityAddress).text.toString()
                entity.postal=dialog.findViewById<BootstrapEditText>(R.id.entityPostal).text.toString()
                entity.phoneNumber=dialog.findViewById<BootstrapEditText>(R.id.entityPhone).text.toString()

                //TODO: pojedyńczo sprawdzić czy pola entity są ok
                //TODO: Jeżeli tak to :
                val seller = SellerData(this)
                seller.setSeller(entity)
                setTexts()
            }

        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    fun setTexts() {
        val seller = SellerData(this)
        dealerNameText.text = "Nazwa : ${seller.getName()}"
        dealerNIPText.text = "NIP : ${seller.getNip()}"
        dealerAdressText.text = "Adres : ${seller.getAddress()}"
        dealerPostalText.text = "Kod pocztowy : ${seller.getPostal()}"
        dealerPhoneText.text = "Telefon : ${seller.getPhone()}"

    }

    //TODO: zwrócić czy dane są okej, najlepiej od razu obsłużyć prycisk cofania
    fun confirmOnClick(view: View) {
        val myIntent = Intent(this, MainActivity::class.java)
        startActivity(myIntent)
    }
}