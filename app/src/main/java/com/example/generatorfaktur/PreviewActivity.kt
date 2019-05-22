package com.example.generatorfaktur

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.beardedhen.androidbootstrap.TypefaceProvider

class PreviewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.preview_activity)
        TypefaceProvider.registerDefaultIconSets()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.my_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_share -> {
            Toast.makeText(this, "Udostępnij", Toast.LENGTH_LONG).show()
            true
        }
        R.id.action_save-> {
            Toast.makeText(this, "Zapisz", Toast.LENGTH_LONG).show()
            true
        }
        R.id.action_print ->{
            Toast.makeText(this, "Drukuj", Toast.LENGTH_SHORT).show()
            true
        }

        else -> {
            // If we got here, the user's action was not recognized.
            // Invoke the superclass to handle it.
            super.onOptionsItemSelected(item)
        }
    }
}