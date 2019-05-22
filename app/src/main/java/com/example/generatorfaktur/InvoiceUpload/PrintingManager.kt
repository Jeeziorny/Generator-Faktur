package com.example.generatorfaktur.InvoiceUpload

import android.content.ContentValues.TAG
import android.content.Context
import android.print.PrintAttributes
import android.print.PrintManager
import android.util.Log
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient

class PrintingManager (private val context: Context){
    private val webView: WebView

    init {
        //Create a WebView object for printing
        webView = WebView(context)
        webView.webViewClient = object : WebViewClient() {

            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?) = false

            //RUNS WHEN NEW PAGE IS OPENED
            override fun onPageFinished(view: WebView?, url: String?) {
                Log.i(TAG, "page finished loading $url")
                createWebPrintJob(view!!)
            }
        }
    }

    fun doWebViewPrint(html: String) {
        webView.loadData(html, "text/html", "UTF-8")
    }

    private fun createWebPrintJob(webView: WebView) {

        //Get a PrintManager instance
        (context.getSystemService(Context.PRINT_SERVICE) as? PrintManager).let { printManager ->

            //Name of printing document
            val jobName = "Faktura Document"

            //Get a print adapter instance
            val printAdapter = webView.createPrintDocumentAdapter (jobName)

            //Create a print job with name and adapter instance
            printManager?.print(
                jobName,
                printAdapter,
                PrintAttributes.Builder().build()
            )

        }
    }
}