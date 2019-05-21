package com.example.generatorfaktur

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import com.beardedhen.androidbootstrap.BootstrapEditText
import com.example.generatorfaktur.invBuilder.AbstractInvcBuilder
import com.example.generatorfaktur.invBuilder.InvcBuilder
import com.example.generatorfaktur.invoiceProperties.Entity
import com.example.generatorfaktur.invoiceProperties.InvoiceItem
import kotlinx.android.synthetic.main.invoice_activity.*
import kotlinx.android.synthetic.main.invoice_activity.itemListView

class InvoiceActivity : AppCompatActivity() {


    var itemList = ArrayList<InvoiceItem>()
    private lateinit var itemArrayAdapter: ItemArrayAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /*
        setContentView(R.layout.invoice_activity)
        supportActionBar!!.hide()

        //Itemki dodane na sztywno, żeby zobaczyć czy bangla

        itemArrayAdapter = ItemArrayAdapter(this, itemList)

        itemArrayAdapter = ItemArrayAdapter(this, itemList)
        itemListView.adapter = itemArrayAdapter

        //itemList.add(InvoiceItem("name","unit", 1.2, 40.0, 100.0, 40.0, 140))
        //itemList.add(InvoiceItem("name1","unit1", 1.2, 40.0, 100.0, 40.0, 140))
        //itemList.add(InvoiceItem("name2","unit2", 1.2, 40.0, 100.0, 40.0, 140))
        //itemList.add(InvoiceItem("name3","unit3", 1.2, 40.0, 100.0, 40.0, 140))
        */

        val builder: AbstractInvcBuilder = InvcBuilder(applicationContext)

        val buyer = Entity("Tomek", "Stodola", "64-600", "432432423", "")
        val seller = Entity("Tomek", "Stodola", "64-600", "432432423", "")
        val reicipient = Entity("Tomek", "Stodola", "64-600", "432432423", "")

        builder.setBuyer(buyer)
               .setDealer(seller)
               .setReicipient(reicipient)

        builder.setProperties("23-04-1004", "siema")
        builder.setPaymentProperty("a", "22-33-4444", "c", "d")
        builder.addInvoiceItem("cebula", 2.0, 4.0, 0.23)

        builder.generate()

        val myIntent = Intent(this, PreviewActivity::class.java)
        startActivity(myIntent)

    }

    fun buyerOnClick(view: View) {
        doDialog("buyer")
    }

    fun dealerOnClick(view: View) {
        doDialog("dealer")
    }

    //Uruchamia dialog do wypełnienia danych klienta/sprzedającego

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

    //Ustawia TextViews w zależności od wprowadzonych danych po zamknięciu dialogu.
    //Można wykorzystać, przesyłając w Intencie z Activity klientów, dla wygenerowania faktury konkretnemu klientowi
    //Można wypełniać dane sprzedającego automatycznie, wczytując liste stringów z danymi dla Dealera, np. z shared preferences
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

    //Odpowiada za przycisk generuj, powinna zbierać dane i tworzyć fakturę oraz uruchamiać widok PDF/do druku
    fun generateOnClick(view: View) {
        //TODO : TWORZENIE FAKTURY Z ZEBRANYCH DANYCH

        val myIntent = Intent(this, PreviewActivity::class.java)
        startActivity(myIntent)
    }


    //Odpowiada za FAB na liście itemów
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
        //TODO : dodawanie itemu do listy, tworzenie go
    }
}