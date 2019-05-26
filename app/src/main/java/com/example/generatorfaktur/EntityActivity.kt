package com.example.generatorfaktur

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.SearchView
import com.beardedhen.androidbootstrap.BootstrapEditText
import com.example.generatorfaktur.DBManager.BasicDBManager
import com.example.generatorfaktur.DBManager.DBManager
import com.example.generatorfaktur.invoiceProperties.Entity
import kotlinx.android.synthetic.main.entity_activity.*
import kotlinx.android.synthetic.main.fab_dialog.view.*
import java.util.*

class EntityActivity : AppCompatActivity() {

    /** list with customers in database */
    private var entityList = ArrayList<Entity>()
    private lateinit var entityArrayAdapter: EntityArrayAdapter
    private lateinit var database: DBManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.entity_activity)
        window.setSoftInputMode(
            WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)

        initDbAndList()
        setupSearchView()

        /** function to create dialog for long click event(at customers list) */
        entityListView.setOnItemLongClickListener { parent, view, position, id ->

            val dialog = getView(R.layout.dialog_long_click)
            val alertDialogBuilder = AlertDialog.Builder(this, R.style.CustomDialog)

            alertDialogBuilder.setView(dialog)
            alertDialogBuilder.setCancelable(true)

            val alertDialog = alertDialogBuilder.create()

            /** button "delete" handling in dialog
             * it deletes chosen customer from database and customers list
             * */
            dialog.findViewById<Button>(R.id.delete_button).setOnClickListener {
                deleteEntity(entityArrayAdapter.displayData[position])
                alertDialog.dismiss()
            }

            /** button "edit" handling in dialog
             * it creates dialog, to change data about chosen customer
             * */
            dialog.findViewById<Button>(R.id.edit_button).setOnClickListener {
                val secDialog = getView(R.layout.fab_dialog)
                initTextEdits(secDialog, position)

                val secAlertDialogBuilder = AlertDialog.Builder(this)
                secAlertDialogBuilder.setView(secDialog)
                secAlertDialogBuilder.setCancelable(true)


                val secAlertDialog = secAlertDialogBuilder.create()

                /** button "add" handling
                 * it updates customer's data in database and customers list
                 * */
                secDialog.findViewById<Button>(R.id.addFAB).setOnClickListener {
                    val entity = parseToEntity(secDialog)
                    updateEntity(entity)

                    secAlertDialog.dismiss()
                    alertDialog.dismiss()
                }

                secAlertDialog.show()

            }
            alertDialog.show()
            true
        }

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)

    }

    /** parsing data from text fields in dialog to Entity object */
    private fun parseToEntity(secDialog: View): Entity {
        val entity = Entity()
        entity.name = secDialog.entityName.text.toString()
        entity.address = secDialog.entityAddress.text.toString()
        entity.nip = secDialog.entityNIP.text.toString()
        entity.phoneNumber = secDialog.entityPhone.text.toString()
        entity.postal = secDialog.entityPostal.text.toString()
        return entity
    }

    /** filling edit texts when user clicks "edit" button in dialog */
    private fun initTextEdits(secDialog: View, position: Int) {
        secDialog.findViewById<BootstrapEditText>(R.id.entityNIP).setText(entityArrayAdapter.displayData[position].nip)
        secDialog.findViewById<BootstrapEditText>(R.id.entityAddress)
            .setText(entityArrayAdapter.displayData[position].address)
        secDialog.findViewById<BootstrapEditText>(R.id.entityName)
            .setText(entityArrayAdapter.displayData[position].name)
        secDialog.findViewById<BootstrapEditText>(R.id.entityPostal)
            .setText(entityArrayAdapter.displayData[position].postal)
        secDialog.findViewById<BootstrapEditText>(R.id.entityPhone)
            .setText(entityArrayAdapter.displayData[position].phoneNumber)
        secDialog.findViewById<BootstrapEditText>(R.id.entityNIP).isEnabled = false
    }

    private fun initDbAndList() {
        database = BasicDBManager(this)
        entityArrayAdapter = EntityArrayAdapter(this, entityList)
        entityListView.adapter = entityArrayAdapter
        syncListViewWithDb()
    }

    private fun setupSearchView() {
        searchView.setIconifiedByDefault(false)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                entityArrayAdapter.filter(newText)
                return true
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                return true
            }
        })
    }

    fun syncListViewWithDb() {
        AsyncTask.execute {
            entityList.clear()
            entityList.addAll(database.getAllEntity())
            runOnUiThread {
                entityArrayAdapter.notifyDataSetChanged()
            }
        }
    }

    /** Floating Action Button handling
     * it creates the dialog to add a new customer
     * */
    fun fabOnClick(view: View) {
        val dialog = getView(R.layout.fab_dialog)
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setView(dialog)

        val entityNameEditText = dialog.findViewById<BootstrapEditText>(R.id.entityName)
        val entityNIPEditText = dialog.findViewById<BootstrapEditText>(R.id.entityNIP)
        val entityPhoneEditText = dialog.findViewById<BootstrapEditText>(R.id.entityPhone)
        val entityPostalEditText = dialog.findViewById<BootstrapEditText>(R.id.entityPostal)
        val entityAddressEditText = dialog.findViewById<BootstrapEditText>(R.id.entityAddress)
        val entityCityPostalEditText = dialog.findViewById<BootstrapEditText>(R.id.entityCityPostal)


        //for better understanding of this methoods please check InvoiceActivity class, doDialog methood.
        entityNameEditText.onFocusChangeListener =
            View.OnFocusChangeListener { v, hasFocus ->
                if (!hasFocus) {
                    if (entityNameEditText.text.toString().isBlank()
                    )
                        entityNameEditText.background = ColorDrawable(Color.rgb(255, 125, 127))
                    else
                        entityNameEditText.background = ColorDrawable(Color.WHITE)
                }
            }

        entityNIPEditText.onFocusChangeListener =
            View.OnFocusChangeListener { v, hasFocus ->
                if (!hasFocus) {
                    if (!Validator.checkNip(entityNIPEditText.text.toString()))
                        entityNIPEditText.background = ColorDrawable(Color.rgb(255, 125, 127))
                    else
                        entityNIPEditText.background = ColorDrawable(Color.WHITE)
                }
            }

        entityPhoneEditText.onFocusChangeListener =
            View.OnFocusChangeListener { v, hasFocus ->
                if (!hasFocus) {
                    if (!Validator.isNumeric(entityPhoneEditText.text.toString()))
                        entityPhoneEditText.background = ColorDrawable(Color.rgb(255, 125, 127))
                    else
                        entityPhoneEditText.background = ColorDrawable(Color.WHITE)

                }
            }

        entityPostalEditText.onFocusChangeListener =
            View.OnFocusChangeListener { v, hasFocus ->
                if (!hasFocus) {
                    if (!Validator.checkPostal(entityPostalEditText.text.toString()))
                        entityPostalEditText.background = ColorDrawable(Color.rgb(255, 125, 127))
                    else
                        entityPostalEditText.background = ColorDrawable(Color.WHITE)

                }
            }

        entityAddressEditText.onFocusChangeListener =
            View.OnFocusChangeListener { v, hasFocus ->
                if (!hasFocus) {
                    if (entityAddressEditText.text.toString().isBlank()
                    )
                        entityAddressEditText.background = ColorDrawable(Color.rgb(255, 125, 127))
                    else
                        entityAddressEditText.background = ColorDrawable(Color.WHITE)

                }
            }

        entityCityPostalEditText.onFocusChangeListener =
            View.OnFocusChangeListener { v, hasFocus ->
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

        /** "add" button handling in dialog
         * it adds a new customer to the data base and customers list
         * */
        dialog.findViewById<Button>(R.id.addFAB).setOnClickListener {
            val entity = Entity()
            entity.name = dialog.entityName.text.toString()
            entity.address = dialog.entityAddress.text.toString()
            entity.nip = dialog.entityNIP.text.toString()
            entity.phoneNumber = dialog.entityPhone.text.toString()
            entity.postal = dialog.entityPostal.text.toString() + " " + entityCityPostalEditText.text.toString()
            addEntity(entity)

            entityArrayAdapter.notifyDataSetChanged()
            alertDialog.dismiss()
        }
        alertDialog.show()
    }

    //podaj nip

    private fun getView(resource: Int): View {
        val li = LayoutInflater.from(this)
        return li.inflate(resource, null)
    }

    private fun addEntity(entity: Entity) {
        database.addEntity(entity)
        syncListViewWithDb()
    }

    private fun deleteEntity(entity: Entity) {
        database.deleteEntity(entity)
        syncListViewWithDb()
    }

    private fun updateEntity(entity: Entity) {
        database.updateEntity(entity)
        syncListViewWithDb()
    }
}