package com.example.generatorfaktur

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.View.OnFocusChangeListener
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import com.beardedhen.androidbootstrap.BootstrapEditText
import com.beardedhen.androidbootstrap.TypefaceProvider
import com.example.generatorfaktur.DBManager.BasicDBManager
import com.example.generatorfaktur.DBManager.SellerData
import com.example.generatorfaktur.generators.InvoiceNumber
import com.example.generatorfaktur.invBuilder.AbstractInvcBuilder
import com.example.generatorfaktur.invBuilder.InvcBuilder
import com.example.generatorfaktur.invoiceProperties.Entity
import com.example.generatorfaktur.invoiceProperties.InvoiceItem
import kotlinx.android.synthetic.main.content_invoice1.*
import java.lang.Exception
import java.math.BigDecimal
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class InvoiceActivity : AppCompatActivity() {


    var itemList = ArrayList<InvoiceItem>()
    private lateinit var itemArrayAdapter: ItemArrayAdapter
    lateinit var builder: AbstractInvcBuilder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_invoice)

        TypefaceProvider.registerDefaultIconSets()
        itemArrayAdapter = ItemArrayAdapter(this, itemList)
        itemArrayAdapter = ItemArrayAdapter(this, itemList)
        itemListView.adapter = itemArrayAdapter

        itemListView.setOnItemLongClickListener { parent, view, position, id ->

            val dialog = getView(R.layout.dialog_long_click)
            val alertDialogBuilder = AlertDialog.Builder(this, R.style.CustomDialog)

            alertDialogBuilder.setView(dialog)
            alertDialogBuilder.setCancelable(true)

            val alertDialog = alertDialogBuilder.create()

            dialog.findViewById<Button>(R.id.delete_button).setOnClickListener {
                builder.removeItem(position)
                itemList.removeAt(position)
                itemArrayAdapter.notifyDataSetChanged()
                alertDialog.dismiss()
            }

            dialog.findViewById<Button>(R.id.edit_button).setOnClickListener {
                val secDialog = getView(R.layout.item_dialog)
                initTextEdits(secDialog, position)
                val secAlertDialogBuilder = AlertDialog.Builder(this)

                secAlertDialogBuilder.setView(secDialog)
                secAlertDialogBuilder.setCancelable(true)

                val secAlertDialog = secAlertDialogBuilder.create()

                secDialog.findViewById<Button>(R.id.addBDI).setOnClickListener {

                    builder.removeItem(position)
                    itemList.removeAt(position)

                    itemList.add(
                        position,
                        builder.addInvoiceItem(
                            secDialog.findViewById<EditText>(R.id.itemName).text.toString(),
                            secDialog.findViewById<EditText>(R.id.itemQuantity).text.toString().toBigDecimal(),
                            secDialog.findViewById<EditText>(R.id.itemPrice).text.toString().toBigDecimal(),
                            secDialog.findViewById<EditText>(R.id.itemVAT).text.toString().toBigDecimal().divide(
                                BigDecimal(100)
                            )
                        )
                    )
                    itemArrayAdapter.notifyDataSetChanged()

                    secAlertDialog.dismiss()
                    alertDialog.dismiss()

                }
                secAlertDialog.show()
            }
            alertDialog.show()
            true
        }

        builder = InvcBuilder(applicationContext)


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.invoice_create_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    @SuppressLint("SimpleDateFormat")
    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_create -> {
            val buyer = Entity(
                buyerNameText.text.toString(),
                buyerAdressText.text.toString(),
                buyerPostalText.text.toString(),
                buyerPhoneText.text.toString(),
                buyerNIPText.text.toString()
            )

            val sellerData = SellerData(this)
            val seller = Entity(
                sellerData.getName(),
                sellerData.getAddress(),
                sellerData.getPostal() + " " + sellerData.getCity(),
                sellerData.getPhone(),
                sellerData.getNip()
            )

            val reicipient = Entity(
                recipientNameText.text.toString(),
                recipientAdressText.text.toString(),
                recipientPostalText.text.toString(),
                recipientPhoneText.text.toString(),
                recipientNIPText.text.toString()
            )

            builder.setBuyer(buyer).setDealer(seller).setReicipient(reicipient)

            val invoiceID = InvoiceNumber(this)
            val sdf = SimpleDateFormat("dd-MM-yyyy")
            val date = sdf.format(Calendar.getInstance().time)

            builder.setProperties(date, invoiceID.generate())


            PropertyDialog()

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

    //Dialog wyświetlający listę posiadanych w bazie klientów
    //Pozwala przejść do dialogu dodającego nowego klienta
    fun choosePersonDialog(who: String) {
        val builder = AlertDialog.Builder(this)
        if (who == "buyer") {
            builder.setTitle("Wybierz nabywcę")
        } else {
            builder.setTitle("Wybierz odbiorcę")
        }

        val dataBase = BasicDBManager(this)
        val entityList = ArrayList<Entity>()
        val entityArrayAdapter = EntityArrayAdapter(this, entityList)


        AsyncTask.execute {
            entityList.clear()
            entityList.addAll(dataBase.getAllEntity())
            runOnUiThread {
                entityArrayAdapter.notifyDataSetChanged()
            }
        }

        builder
            .setSingleChoiceItems(entityArrayAdapter, -1, object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    val result = ArrayList<String>()
                    result.add(entityList[which].name)
                    result.add(entityList[which].nip)
                    result.add(entityList[which].address)
                    result.add(entityList[which].postal)
                    result.add(entityList[which].phoneNumber)
                    setTexts(result, who)
                    dialog?.dismiss()
                }
            })
            .setCancelable(true)
            .setPositiveButton("NOWY") { _, _ ->
                doDialog(who)
            }
        val alertDialog = builder.create()
        alertDialog.show()

    }

    //Dialog wybierający metodę płatności i kończący generowanie faktury
    @SuppressLint("InflateParams", "SimpleDateFormat")
    fun PropertyDialog() {
        val li = LayoutInflater.from(this)
        val dialog = li.inflate(R.layout.invoice_parametrs_dialog, null)
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setView(dialog)
        alertDialogBuilder
            .setCancelable(true)
        val alertDialog = alertDialogBuilder.create()

        dialog.findViewById<Button>(R.id.confirmIPD).setOnClickListener {
            val sdf = SimpleDateFormat("dd-MM-yyyy")
            val seller = SellerData(this)
            when (dialog.findViewById<RadioGroup>(R.id.paymentGroup).checkedRadioButtonId) {
                R.id.paymentCashButton -> {
                    val date = sdf.format(Calendar.getInstance().time)
                    builder.setPaymentProperty("Gotówka", date, seller.getBankName(), seller.getBankNumber())
                }
                else -> {
                    val paymentDuration = dialog.findViewById<EditText>(R.id.paymentDurationText).text.toString()
                    val calendar = Calendar.getInstance()
                    if (paymentDuration != "") {
                        calendar.add(Calendar.DATE, paymentDuration.toInt())
                    }
                    val date = sdf.format(calendar.time)
                    builder.setPaymentProperty("Przelew", date, seller.getBankName(), seller.getBankNumber())
                }
            }

            val result = builder.generate()

            val myIntent = Intent(this, PreviewActivity::class.java)
            myIntent.putExtra("HTML", result)
            myIntent.putExtra("ID", builder.invoice.invoiceId)
            startActivity(myIntent)
        }



        alertDialog.show()
        dialog.findViewById<RadioGroup>(R.id.paymentGroup).setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.paymentCashButton -> {
                    val editText = dialog.findViewById<EditText>(R.id.paymentDurationText)
                    editText.isEnabled = false
                    editText.setText("")
                }
                else -> {
                    val editText = dialog.findViewById<EditText>(R.id.paymentDurationText)
                    editText.isEnabled = true
                }
            }
        }
    }


    //Uruchamia dialog do wypełnienia danych klienta/sprzedającego
    @SuppressLint("InflateParams")
    fun doDialog(who: String) {
        val li = LayoutInflater.from(this)
        val dialog = li.inflate(R.layout.fab_dialog, null)
        val result = ArrayList<String>()
        val alertDialogBuilder = AlertDialog.Builder(this)

        alertDialogBuilder.setView(dialog)

        //I've defined vals for faster access
        val entityNameEditText = dialog.findViewById<BootstrapEditText>(R.id.entityName)
        val entityNIPEditText = dialog.findViewById<BootstrapEditText>(R.id.entityNIP)
        val entityPhoneEditText = dialog.findViewById<BootstrapEditText>(R.id.entityPhone)
        val entityPostalEditText = dialog.findViewById<BootstrapEditText>(R.id.entityPostal)
        val entityAddressEditText = dialog.findViewById<BootstrapEditText>(R.id.entityAddress)
        val entityCityPostalEditText = dialog.findViewById<BootstrapEditText>(R.id.entityCityPostal)

        /*
        while focus on another textField, this lambda changes background
        on red if input was incorrect, on white if opposite.

        It sticks to each editText on this dialog.
         */
        entityNameEditText.onFocusChangeListener =
            OnFocusChangeListener { _, hasFocus ->
                //hasFocus get false when losing focus
                if (!hasFocus) {
                    //validation for blank input (e.g. only white characters)
                    if (entityNameEditText.text.toString().isBlank()
                    )
                        entityNameEditText.background = ColorDrawable(Color.rgb(255, 125, 127))
                    else
                        entityNameEditText.background = ColorDrawable(Color.WHITE)
                }
            }

        entityNIPEditText.onFocusChangeListener =
            OnFocusChangeListener { v, hasFocus ->
                if (!hasFocus) {
                    if (!Validator.checkNip(entityNIPEditText.text.toString()))
                        entityNIPEditText.background = ColorDrawable(Color.rgb(255, 125, 127))
                    else
                        entityNIPEditText.background = ColorDrawable(Color.WHITE)
                }
            }

        entityPhoneEditText.onFocusChangeListener =
            OnFocusChangeListener { v, hasFocus ->
                if (!hasFocus) {
                    if (!Validator.isNumeric(entityPhoneEditText.text.toString()))
                        entityPhoneEditText.background = ColorDrawable(Color.rgb(255, 125, 127))
                    else
                        entityPhoneEditText.background = ColorDrawable(Color.WHITE)

                }
            }

        entityPostalEditText.onFocusChangeListener =
            OnFocusChangeListener { v, hasFocus ->
                if (!hasFocus) {
                    if (!Validator.checkPostal(entityPostalEditText.text.toString()))
                        entityPostalEditText.background = ColorDrawable(Color.rgb(255, 125, 127))
                    else
                        entityPostalEditText.background = ColorDrawable(Color.WHITE)

                }
            }

        entityAddressEditText.onFocusChangeListener =
            OnFocusChangeListener { v, hasFocus ->
                if (!hasFocus) {
                    if (entityAddressEditText.text.toString().isBlank()
                    )
                        entityAddressEditText.background = ColorDrawable(Color.rgb(255, 125, 127))
                    else
                        entityAddressEditText.background = ColorDrawable(Color.WHITE)

                }
            }

        entityCityPostalEditText.onFocusChangeListener =
            OnFocusChangeListener { v, hasFocus ->
                if (!hasFocus) {
                    if (entityCityPostalEditText.text.toString().isBlank()
                    )
                        entityCityPostalEditText.background = ColorDrawable(Color.rgb(255, 125, 127))
                    else
                        entityCityPostalEditText.background = ColorDrawable(Color.WHITE)

                }
            }

        alertDialogBuilder
            .setCancelable(true)

        val alertDialog = alertDialogBuilder.create()


        dialog.findViewById<Button>(R.id.addFAB).setOnClickListener {
            result.add(entityNameEditText.text.toString())
            result.add(entityNIPEditText.text.toString())
            result.add(entityAddressEditText.text.toString())
            result.add(entityPostalEditText.text.toString() + " " + entityCityPostalEditText.text.toString())
            result.add(entityPhoneEditText.text.toString())

            setTexts(result, who)

            alertDialog.dismiss()
        }
        alertDialog.show()
    }

    //Ustawia TextViews w zależności od wprowadzonych danych po zamknięciu dialogu.
    //Można wykorzystać, przesyłając w Intencie z Activity klientów, dla wygenerowania faktury konkretnemu klientowi
    //Można wypełniać dane sprzedającego automatycznie, wczytując liste stringów z danymi dla Dealera, np. z shared preferences
    fun setTexts(data: ArrayList<String>, who: String) {
        when (who) {
            "recipient" -> {
                recipientNameText.text = data[0]
                recipientNIPText.text = data[1]
                recipientAdressText.text = data[2]
                recipientPostalText.text = data[3]
                recipientPhoneText.text = data[4]
            }
            "buyer" -> {
                buyerNameText.text = data[0]
                buyerNIPText.text = data[1]
                buyerAdressText.text = data[2]
                buyerPostalText.text = data[3]
                buyerPhoneText.text = data[4]
            }
        }
    }

    //Odpowiada za FAB na liście itemów
    @SuppressLint("InflateParams")
    fun itemFABOnClick(view: View) {
        val li = LayoutInflater.from(this)
        val dialog = li.inflate(R.layout.item_dialog, null)

        val alertDialogBuilder = AlertDialog.Builder(this)

        alertDialogBuilder.setView(dialog)

        alertDialogBuilder
            .setCancelable(true)

        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
        dialog.findViewById<Button>(R.id.addBDI).setOnClickListener {

            val itemName = dialog.findViewById<EditText>(R.id.itemName).text.toString()
            val itemQuantity = dialog.findViewById<EditText>(R.id.itemQuantity).text.toString()
            val itemPrice = dialog.findViewById<EditText>(R.id.itemPrice).text.toString()
            val itemVAT = dialog.findViewById<EditText>(R.id.itemVAT).text.toString()
            if (itemName.isNotBlank() && itemQuantity.isNotBlank() && itemPrice.isNotBlank()
                && itemVAT.isNotBlank() && !itemVAT.contains('.')
            ) {

                try {
                    itemList.add(
                        builder.addInvoiceItem(
                            itemName,
                            itemQuantity.toBigDecimal(),
                            itemPrice.toBigDecimal(),
                            itemVAT.toBigDecimal().divide(BigDecimal(100))
                        )
                    )
                    itemArrayAdapter.notifyDataSetChanged()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            alertDialog.dismiss()

        }

    }

    private fun getView(resource: Int): View {
        val li = LayoutInflater.from(this)
        return li.inflate(resource, null)
    }

    private fun initTextEdits(secDialog: View, position: Int) {
        val symbols = DecimalFormatSymbols(Locale.getDefault())
        symbols.decimalSeparator = '.'
        secDialog.findViewById<EditText>(R.id.itemName).setText(itemList[position].name)
        secDialog.findViewById<EditText>(R.id.itemPrice)
            .setText(DecimalFormat("#.0",symbols).format(itemList[position].baseValue))
        secDialog.findViewById<EditText>(R.id.itemQuantity)
            .setText(DecimalFormat("#.00",symbols).format(itemList[position].quantity))
        secDialog.findViewById<EditText>(R.id.itemVAT)
            .setText(DecimalFormat("#",symbols).format(itemList[position].vat.multiply(BigDecimal(100))))
    }
}