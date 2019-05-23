package com.example.generatorfaktur

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

    private var entityList = ArrayList<Entity>()
    private lateinit var entityArrayAdapter: EntityArrayAdapter
    private lateinit var database: DBManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.entity_activity)

        initDbAndList()
        setupSearchView()

        entityListView.setOnItemLongClickListener { parent, view, position, id ->

            val dialog = getView(R.layout.dialog_long_click)
            val alertDialogBuilder = AlertDialog.Builder(this, R.style.CustomDialog)

            alertDialogBuilder.setView(dialog)
            alertDialogBuilder.setCancelable(true)
            val alertDialog = alertDialogBuilder.create()

            dialog.findViewById<Button>(R.id.delete_button).setOnClickListener {
                deleteEntity(entityArrayAdapter.displayData[position])
                alertDialog.hide()
            }

            dialog.findViewById<Button>(R.id.edit_button).setOnClickListener {
                val secDialog = getView(R.layout.fab_dialog)
                initTextEdits(secDialog, position)

                val secAlertDialogBuilder = AlertDialog.Builder(this)
                secAlertDialogBuilder.setView(secDialog)
                secAlertDialogBuilder.setCancelable(true)


                val secAlertDialog = secAlertDialogBuilder.create()

                secDialog.findViewById<Button>(R.id.addFAB).setOnClickListener {
                    val entity = parseToEntity(secDialog)
                    updateEntity(entity)
                }

                secAlertDialog.show()
                alertDialog.hide()

            }
            alertDialog.show()
            true
        }

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)

    }

    private fun parseToEntity(secDialog: View): Entity {
        val entity = Entity()
        entity.name = secDialog.entityName.text.toString()
        entity.address = secDialog.entityAddress.text.toString()
        entity.nip = secDialog.entityNIP.text.toString()
        entity.phoneNumber = secDialog.entityPhone.text.toString()
        entity.postal = secDialog.entityPostal.text.toString()
        return entity
    }

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

    fun fabOnClick(view: View) {
        val dialog = getView(R.layout.fab_dialog)
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setView(dialog)

        alertDialogBuilder
            .setCancelable(true)

        val alertDialog = alertDialogBuilder.create()

        dialog.findViewById<Button>(R.id.addFAB).setOnClickListener {
            val entity = Entity()
            entity.name = dialog.entityName.text.toString()
            entity.address = dialog.entityAddress.text.toString()
            entity.nip = dialog.entityNIP.text.toString()
            entity.phoneNumber = dialog.entityPhone.text.toString()
            entity.postal = dialog.entityPostal.text.toString()
            addEntity(entity)

            entityArrayAdapter.notifyDataSetChanged()
        }
        alertDialog.show()
    }

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