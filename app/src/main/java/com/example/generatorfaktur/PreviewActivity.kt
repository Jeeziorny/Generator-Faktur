package com.example.generatorfaktur

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import com.beardedhen.androidbootstrap.TypefaceProvider
import com.example.generatorfaktur.InvoiceUpload.PdfWriter
import com.example.generatorfaktur.InvoiceUpload.PrintingManager
import kotlinx.android.synthetic.main.preview_activity.*

class PreviewActivity : AppCompatActivity() {

    val URL = "file:///android_asset/FakturaVAT.htm"
    lateinit var Url1 : String
    lateinit var pdfWriter : PdfWriter
    lateinit var printingManager : PrintingManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.preview_activity)

        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                pdfWriter.writeAsTemporaryFile()
            }
        }

        Url1 = applicationContext.filesDir.absolutePath.plus("/myFile.html")
        webView.loadUrl(Url1)



        printingManager = PrintingManager(this)
        pdfWriter = PdfWriter( this, "invoice.PDF", webView)

        TypefaceProvider.registerDefaultIconSets()
//        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.my_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_share -> {
            Log.d("running", "lolo")
            printingManager.doWebViewPrint(Url1)
            Toast.makeText(this, "Udostępnij", Toast.LENGTH_LONG).show()
            true
        }
        R.id.action_save-> {
            pdfWriter.copyToExternal("Invoice ${System.currentTimeMillis()}")
            Toast.makeText(this, "Zapisz", Toast.LENGTH_LONG).show()
            true
        }

        else -> {
            // If we got here, the user's action was not recognized.
            // Invoke the superclass to handle it.
            super.onOptionsItemSelected(item)
        }
    }
}