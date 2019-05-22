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

    var HTML : String? = null
    var ID : String? = null
    lateinit var pdfWriter : PdfWriter
    lateinit var printingManager : PrintingManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.preview_activity)

        webView.setInitialScale(1)

        webView.settings.builtInZoomControls = true
        webView.settings.setSupportZoom(true)
        webView.settings.builtInZoomControls = true
        webView.settings.displayZoomControls = false
        webView.settings.loadWithOverviewMode = true
        webView.settings.useWideViewPort = true
        //webView.scrollBarStyle = WebView.SCROLLBARS_OUTSIDE_OVERLAY
        //webView.isScrollbarFadingEnabled = true

        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                pdfWriter.writeAsTemporaryFile()
            }
        }

        if (intent != null) {
            HTML = intent.getStringExtra("HTML")
            ID = intent.getStringExtra("ID")
        }

        if(HTML == null) {
            Toast.makeText(this, "Nie udalo sie wygenerowac faktury", Toast.LENGTH_LONG).show()
            finish()
        }

        webView.loadData(HTML, "text/html", "UTF-8")

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
            pdfWriter.uploadFile()
            //Toast.makeText(this, "Udostępnij", Toast.LENGTH_LONG).show()
            true
        }
        R.id.action_save-> {
            pdfWriter.copyToExternal("Invoice $ID")
            Toast.makeText(this, "Zapisz", Toast.LENGTH_LONG).show()
            true
        }
        R.id.action_print ->{
            val html = HTML
            if(html != null) {
                printingManager.doWebViewPrint(html)
            }
            //Toast.makeText(this, "Drukuj", Toast.LENGTH_SHORT).show()
            true
        }

        else -> {
            // If we got here, the user's action was not recognized.
            // Invoke the superclass to handle it.
            super.onOptionsItemSelected(item)
        }
    }
}