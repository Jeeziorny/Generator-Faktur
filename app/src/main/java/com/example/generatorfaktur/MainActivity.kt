package com.example.generatorfaktur

import android.arch.persistence.room.Room
import android.content.Intent
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.beardedhen.androidbootstrap.TypefaceProvider
import com.example.generatorfaktur.DBManager.AppDatabase
import com.example.generatorfaktur.invoiceProperties.Entity
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    private lateinit var database: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        TypefaceProvider.registerDefaultIconSets()
        supportActionBar!!.hide()
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

}
