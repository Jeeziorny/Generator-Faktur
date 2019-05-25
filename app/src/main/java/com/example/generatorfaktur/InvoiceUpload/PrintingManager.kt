package com.example.generatorfaktur.InvoiceUpload

import android.content.ContentValues.TAG
import android.content.Context
import android.print.PrintAttributes
import android.print.PrintManager
import android.util.Log
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient


/**
 * Assistent which let print invoice.
 * Use own WebView.
 *
 * @param context Context needed to create WebView
 */
class PrintingManager (private val context: Context){
    private val webView: WebView

    init {
        //Create a WebView object for printing
        webView = WebView(context)
        webView.webViewClient = object : WebViewClient() {

            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?) = false

            //RUNS ALWAYS WHEN WEBVIEW FINISHED LOAD HTML
            //THATS WHY NEED OWN WEBVIEW
            override fun onPageFinished(view: WebView?, url: String?) {
                //PRINT
                createWebPrintJob(view!!)
            }
        }
    }

    /**
     * Loads new HTML String to WebView
     * Runs onPageFinished
     *
     * @param html Invoice saved as HTML
     */
    fun doWebViewPrint(html: String) {
        webView.loadData(html, "text/html", "UTF-8")
    }

    /**
     * Create PrintManager from PRINT_SERVICE
     * Runs new Activity
     *
     */
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