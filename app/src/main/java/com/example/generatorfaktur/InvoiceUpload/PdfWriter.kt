package com.example.generatorfaktur.InvoiceUpload

import android.content.Context
import android.content.res.AssetManager
import android.os.Environment
import android.print.PdfPrint
import android.print.PrintAttributes
import android.webkit.WebView
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

import android.content.Intent
import android.net.Uri
import android.support.v4.content.FileProvider
import android.util.Log
import com.example.generatorfaktur.R


class PdfWriter (val context: Context, val fileName: String, val webView: WebView) {



    fun writeAsTemporaryFile() {
        context.deleteFile(fileName)
        createWebPrintJob(webView)

    }


    fun uploadFile() {
        val file = File(context.filesDir, fileName)
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "application/pdf"

        val uri = FileProvider.getUriForFile(context,
            context.getString(R.string.file_provider_authority), file)
        intent.putExtra(Intent.EXTRA_STREAM, uri)
        context.startActivity(Intent.createChooser(intent, "Share PDF file"))


    }

    //COPY FILE FROM ASSETS TO EXTERNAL
    fun copyToExternal(name: String) {
        val input = FileInputStream(File(context.filesDir, fileName))
        val output = FileOutputStream(makeOutputFile(name))
        input.copyTo(output)
    }

    //CREATE FILE IN DCIM/InvoicePDF FOLDER
    private fun makeOutputFile(fileName: String) : File {
        val path =  Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM.plus("/InvoicePDF/"))
        if (!path.exists()) {
            path.mkdirs()
        }
        val file = File(path, fileName.plus(".PDF"))
        file.createNewFile()

        return file
    }

    //SAVES TO PDF WHAT WEBVIEW SHOWS
    //THAT IS WHY MUST BE USED WEBVIEW FROM ACTIVITY
    private fun createWebPrintJob(webView: WebView) {
        val jobName = "Invoice Document"
        val attributes = PrintAttributes.Builder()
            .setMediaSize(PrintAttributes.MediaSize.ISO_A4)
            .setResolution(PrintAttributes.Resolution("pdf", "pdf", 600, 600))
            .setMinMargins(PrintAttributes.Margins.NO_MARGINS)
            .build()
        val pdfPrint = PdfPrint(context, attributes)
        //val path = File(URL)
        pdfPrint.write(webView.createPrintDocumentAdapter(jobName), fileName)
    }

}