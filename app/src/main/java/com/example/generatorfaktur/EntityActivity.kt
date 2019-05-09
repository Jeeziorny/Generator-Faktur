package com.example.generatorfaktur

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.example.generatorfaktur.DBManager.BasicDBManager
import com.example.generatorfaktur.invoiceProperties.Entity
import kotlinx.android.synthetic.main.entity_activity.*

class EntityActivity : AppCompatActivity() {

    private var entityList = ArrayList<Entity>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.entity_activity)
        supportActionBar!!.hide()
        entityList = BasicDBManager(this).getAllEntity()
        val entityArrayAdapter = EntityArrayAdapter(this, entityList)

        entityListView.adapter = entityArrayAdapter

        //TODO : Obsługa long clicka

        entityListView.setOnItemLongClickListener { parent, view, position, id ->
            true
        }


        //TODO : Obsługa clicka

        entityListView.setOnItemClickListener { parent, view, position, id ->

        }
    }

    fun fabOnClick(view: View) {

        //TODO: Obsługa FAB
        addEntity()
        Snackbar.make(view, "Item added to list", Snackbar.LENGTH_SHORT)
            .setAction("Action", null).show()

    }

    private fun addEntity() {

    }

    //TODO : Gdy już będzie jakaś lista, poprawić entity.xml
}