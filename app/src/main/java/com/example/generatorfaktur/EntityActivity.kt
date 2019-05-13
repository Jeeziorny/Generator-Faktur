package com.example.generatorfaktur

import android.os.AsyncTask
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.widget.SearchView
import com.example.generatorfaktur.DBManager.BasicDBManager
import com.example.generatorfaktur.DBManager.DBManager
import com.example.generatorfaktur.invoiceProperties.Entity
import kotlinx.android.synthetic.main.entity_activity.*
import java.util.*

class EntityActivity : AppCompatActivity() {

    private var entityList = ArrayList<Entity>()
    private lateinit var entityArrayAdapter: EntityArrayAdapter
    private lateinit var database : DBManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.entity_activity)
        supportActionBar!!.hide()

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

        //TODO : Obsługa long clicka
        entityListView.setOnItemLongClickListener { parent, view, position, id ->
            val li = LayoutInflater.from(this)
            val dialog = li.inflate(R.layout.dialog_long_click, null)

            val alertDialogBuilder = AlertDialog.Builder(this)

            alertDialogBuilder.setView(dialog)

            alertDialogBuilder.setCancelable(true)

            val alertDialog = alertDialogBuilder.create()
            alertDialog.show()
            true
        }


        //TODO : Obsługa clicka
        entityListView.setOnItemClickListener { parent, view, position, id ->

        }
    }

    fun syncListViewWithDb() {
        AsyncTask.execute {
            entityList.addAll(database.getAllEntity())
            entityArrayAdapter.notifyDataSetChanged()
        }
    }

    fun fabOnClick(view: View) {



        //TODO: Obsługa FAB
        val li = LayoutInflater.from(this)
        val dialog = li.inflate(R.layout.fab_dialog, null)

        val alertDialogBuilder = AlertDialog.Builder(this)

        alertDialogBuilder.setView(dialog)

        alertDialogBuilder
            .setCancelable(true)
            .setPositiveButton("DODAJ") {  _, _ ->

                addEntity()

                Snackbar.make(view, "Dodano klienta.", Snackbar.LENGTH_SHORT)
                    .setAction("Action", null).show()

                entityArrayAdapter.notifyDataSetChanged()
        }
            .setNegativeButton("ANULUJ") { _, _ ->

            }

        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()


    }

    private fun addEntity() {

    }

}