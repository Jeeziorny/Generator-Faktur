package com.example.generatorfaktur

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.beardedhen.androidbootstrap.BootstrapEditText
import com.beardedhen.androidbootstrap.TypefaceProvider
import com.example.generatorfaktur.DBManager.BasicDBManager
import com.example.generatorfaktur.invBuilder.AbstractInvcBuilder
import com.example.generatorfaktur.invBuilder.InvcBuilder
import com.example.generatorfaktur.invoiceProperties.Entity
import com.example.generatorfaktur.invoiceProperties.InvoiceItem
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_invoice1.*

class InvoiceActivity : AppCompatActivity() {


    var itemList = ArrayList<InvoiceItem>()
    private lateinit var itemArrayAdapter: ItemArrayAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContentView(R.layout.activity_invoice)
        TypefaceProvider.registerDefaultIconSets()
        //setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //Itemki dodane na sztywno, żeby zobaczyć czy bangla

        itemArrayAdapter = ItemArrayAdapter(this, itemList)

        itemArrayAdapter = ItemArrayAdapter(this, itemList)
        itemListView.adapter = itemArrayAdapter

        itemList.add(InvoiceItem("name",8.0, 1.2, 40.0, 100.0, 40.0, 140))
        itemList.add(InvoiceItem("name1",8.0, 1.2, 40.0, 100.0, 40.0, 140))
        itemList.add(InvoiceItem("name2",0.0, 1.2, 40.0, 100.0, 40.0, 140))
        itemList.add(InvoiceItem("name3",1.1, 1.2, 40.0, 100.0, 40.0, 140))
        itemList.add(InvoiceItem("name4",0.0, 1.2, 40.0, 100.0, 40.0, 140))
        itemList.add(InvoiceItem("name5",1.1, 1.2, 40.0, 100.0, 40.0, 140))
        itemList.add(InvoiceItem("name6",1.1, 1.2, 40.0, 100.0, 40.0, 140))


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.invoice_create_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_create -> {
            //TODO : TWORZENIE FAKTURY Z ZEBRANYCH DANYCH

            val builder: AbstractInvcBuilder = InvcBuilder(applicationContext)

            val buyer = Entity("Tomek", "Stodola", "64-600", "432432423", "")
            val seller = Entity("Tomek", "Stodola", "64-600", "432432423", "")
            val reicipient = Entity("Tomek", "Stodola", "64-600", "432432423", "")

            builder.setBuyer(buyer).setDealer(seller).setReicipient(reicipient)
            builder.setProperties("23-04-1004", "siema")
            builder.setPaymentProperty("a", "22-33-4444", "c", "d")

            builder.addInvoiceItem("cebula", 2.0, 4.0, 0.23)
            builder.addInvoiceItem("cebula", 2.0, 4.0, 0.23)
            builder.addInvoiceItem("cebula", 2.0, 4.0, 0.23)
            builder.addInvoiceItem("cebula", 2.0, 4.0, 0.23)
            builder.addInvoiceItem("cebula", 2.0, 4.0, 0.23)
            builder.addInvoiceItem("cebula", 2.0, 4.0, 0.23)
            builder.addInvoiceItem("cebula", 2.0, 4.0, 0.23)
            builder.addInvoiceItem("cebula", 2.0, 4.0, 0.23)
            builder.addInvoiceItem("cebula", 2.0, 4.0, 0.23)
            builder.addInvoiceItem("cebula", 2.0, 4.0, 0.23)
            builder.addInvoiceItem("cebula", 2.0, 4.0, 0.23)
            builder.addInvoiceItem("cebula", 2.0, 4.0, 0.23)
            builder.addInvoiceItem("cebula", 2.0, 4.0, 0.23)
            builder.addInvoiceItem("cebula", 2.0, 4.0, 0.23)
            builder.addInvoiceItem("cebula", 2.0, 4.0, 0.23)
            builder.addInvoiceItem("cebula", 2.0, 4.0, 0.23)
            builder.addInvoiceItem("cebula", 2.0, 4.0, 0.23)
            builder.addInvoiceItem("cebula", 2.0, 4.0, 0.23)
            builder.addInvoiceItem("cebula", 2.0, 4.0, 0.23)
            builder.addInvoiceItem("cebula", 2.0, 4.0, 0.23)
            builder.addInvoiceItem("cebula", 2.0, 4.0, 0.23)
            builder.addInvoiceItem("cebula", 2.0, 4.0, 0.23)
            builder.addInvoiceItem("cebula", 2.0, 4.0, 0.23)
            builder.addInvoiceItem("cebula", 2.0, 4.0, 0.23)
            builder.addInvoiceItem("cebula", 2.0, 4.0, 0.23)

            val result = builder.generate()

            val myIntent = Intent(this, PreviewActivity::class.java)
            myIntent.putExtra("HTML", result)
            myIntent.putExtra("ID", builder.invoice.invoiceId)
            startActivity(myIntent)
            true
        }

        else -> {
            // If we got here, the user's action was not recognized.
            // Invoke the superclass to handle it.
            super.onOptionsItemSelected(item)
        }
    }

    fun buyerOnClick(view: View) {
        choosePersonDialog("buyer")
    }

    fun recipientOnClick(view: View) {
        choosePersonDialog("recipient")
    }



    fun choosePersonDialog(who: String) {
        val builder = AlertDialog.Builder(this)
        if(who == "buyer") {
            builder.setTitle("Wybierz nabywcę")
        } else {
            builder.setTitle("Wybuerz odbiorcę")
        }

        val dataBase = BasicDBManager(this)
        val entityList = ArrayList<Entity>()
        entityList.clear()
        entityList.addAll(dataBase.getAllEntity())



        builder
            .setSingleChoiceItems(EntityArrayAdapter(this, entityList), -1, object : DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface?, which: Int) {
                val result = ArrayList<String>()
                result.add(entityList[which].name)
                result.add(entityList[which].nip)
                result.add(entityList[which].address)
                result.add(entityList[which].postal)
                result.add(entityList[which].phoneNumber)
                setTexts(result, who)
            }
        })
            .setCancelable(true)
            .setPositiveButton("NOWY") { _, _ ->
                doDialog(who)
            }

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
            "recipient" -> {
                recipientNameText.text = "Nazwa : ${data[0]}"
                recipientNIPText.text = "NIP : ${data[1]}"
                recipientAdressText.text = "Adres : ${data[2]}"
                recipientPostalText.text = "Kod pocztowy : ${data[3]}"
                recipientPhoneText.text = "Telefon : ${data[4]}"
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

        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()

    }

    fun addItem() {
        //TODO : dodawanie itemu do listy, tworzenie go
    }
}