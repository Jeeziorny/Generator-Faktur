package com.example.generatorfaktur

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.beardedhen.androidbootstrap.TypefaceProvider
import com.example.generatorfaktur.DBManager.BasicDBManager
import com.example.generatorfaktur.DBManager.DBManager
import com.example.generatorfaktur.invoiceProperties.Entity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        TypefaceProvider.registerDefaultIconSets()
        supportActionBar!!.hide()

        //Wypełnieine DB do testów !!
        fillDB()
    }

    fun customerOnClick(view: View) {
        val myIntent = Intent(this, EntityActivity::class.java)
        startActivityForResult(myIntent, 997)
    }

    fun invoiceOnClick(view: View) {
        val myIntent = Intent(this, InvoiceActivity::class.java)
        startActivityForResult(myIntent, 998)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            997 -> {

            }
            998 -> {

            }
        }
    }

    fun fillDB() {
        val dbManager: DBManager = BasicDBManager(this)
        var list = dbManager.getAllEntity()
        if (list.size == 0) {
            dbManager.addEntity(
                Entity(
                    "Wysypisko Sp. z o.o.",
                    "ul. Wyspiańskiego 10/1",
                    "00-001 Warszawa",
                    "0123456789",
                    "+48 111 222 333"
                )
            )
            dbManager.addEntity(
                Entity(
                    "Wściekłe Krowy Sp.K.",
                    "ul. Nowowiejska 99",
                    "99-666 Bytom",
                    "6910293847",
                    "+48 666 777 888"
                )
            )
            dbManager.addEntity(
                Entity(
                    "Rozlewnia BIMBEREK 93%",
                    "ul. Spirytusowa 40",
                    "40-500 Chmieleń",
                    "1020304050",
                    "+48 098 321 567"
                )
            )
            dbManager.addEntity(
                Entity(
                    "Twoja Idealna Trumienka",
                    "ul. Cmentarna",
                    "11-777 Kosteczki",
                    "0368521893",
                    "+48 936 741 154"
                )
            )
        }
    }
}
