package com.example.generatorfaktur

import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
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

        database = BasicDBManager(this)
        entityArrayAdapter = EntityArrayAdapter(this, entityList)
        entityListView.adapter = entityArrayAdapter
        syncListViewWithDb()

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


        entityListView.setOnItemLongClickListener { parent, view, position, id ->
            val li = LayoutInflater.from(this)
            val dialog = li.inflate(R.layout.dialog_long_click, null)



            val alertDialogBuilder = AlertDialog.Builder(this, R.style.CustomDialog)


            alertDialogBuilder.setView(dialog)

            alertDialogBuilder.setCancelable(true)

            val alertDialog = alertDialogBuilder.create()

            alertDialog.show()

            dialog.findViewById<Button>(R.id.delete_button).setOnClickListener {
                deleteEntity(entityArrayAdapter.displayData[position])
                alertDialog.hide()
            }

            dialog.findViewById<Button>(R.id.edit_button).setOnClickListener {


                val secLi = LayoutInflater.from(this)

                val secDialog = secLi.inflate(R.layout.fab_dialog, null)

                secDialog.findViewById<BootstrapEditText>(R.id.entityNIP).setText(entityArrayAdapter.displayData[position].nip)
                secDialog.findViewById<BootstrapEditText>(R.id.entityAddress).setText(entityArrayAdapter.displayData[position].address)
                secDialog.findViewById<BootstrapEditText>(R.id.entityName).setText(entityArrayAdapter.displayData[position].name)
                secDialog.findViewById<BootstrapEditText>(R.id.entityPostal).setText(entityArrayAdapter.displayData[position].postal)
                secDialog.findViewById<BootstrapEditText>(R.id.entityPhone).setText(entityArrayAdapter.displayData[position].phoneNumber)
                secDialog.findViewById<BootstrapEditText>(R.id.entityNIP).isEnabled = false

                val secAlertDialogBuilder = AlertDialog.Builder(this, R.style.FABDialog)
                secAlertDialogBuilder.setView(secDialog)
                secAlertDialogBuilder.setCancelable(true)


                secAlertDialogBuilder.setPositiveButton("EDYTUJ") { _, _ ->
                    val entity = Entity()
                    entity.name = secDialog.entityName.text.toString()
                    entity.address = secDialog.entityAddress.text.toString()
                    entity.nip = secDialog.entityNIP.text.toString()
                    entity.phoneNumber = secDialog.entityPhone.text.toString()
                    entity.postal = secDialog.entityPostal.text.toString()
                    updateEntity(entity)
                }




                val secAlertDialog = secAlertDialogBuilder.create()
                secAlertDialog.show()
                alertDialog.hide()

            }
            true
        }

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
        val li = LayoutInflater.from(this)
        val dialog = li.inflate(R.layout.fab_dialog, null)

        val alertDialogBuilder = AlertDialog.Builder(this, R.style.FABDialog)

        alertDialogBuilder.setView(dialog)

        alertDialogBuilder
            .setCancelable(true)
            .setPositiveButton("DODAJ") { _, _ ->

                val entity = Entity()
                entity.name = dialog.entityName.text.toString()
                entity.address = dialog.entityAddress.text.toString()
                entity.nip = dialog.entityNIP.text.toString()
                entity.phoneNumber = dialog.entityPhone.text.toString()
                entity.postal = dialog.entityPostal.text.toString()
                addEntity(entity)

                entityArrayAdapter.notifyDataSetChanged()
        }

        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
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