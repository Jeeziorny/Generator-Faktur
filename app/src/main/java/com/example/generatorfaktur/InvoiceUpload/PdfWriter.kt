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


/**
 * Create, save and share PDF invoice file
 *
 * @param context Activity or context where is used WebView
 * @param fileName name of PDF file saved in external storage
 * @param webView WebView which shows invoice HTML
 */

class PdfWriter (val context: Context, private val fileName: String, private val webView: WebView) {


    /**
     * Creates temporary PDF invoice file in internal storage
     * Needed to use other methods
     */
    fun writeAsTemporaryFile() {
        context.deleteFile(fileName)
        createWebPrintJob(webView)

    }


    /**
     * Send file to intent to share by other apps
     * Use fileProvider which secures path of shared files
     */
    fun uploadFile() {
        val file = File(context.filesDir, fileName)
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "application/pdf"

        //GET URI USING FILEPROVIDER
        val uri = FileProvider.getUriForFile(context,
            context.getString(R.string.file_provider_authority), file)
        intent.putExtra(Intent.EXTRA_STREAM, uri)
        context.startActivity(Intent.createChooser(intent, "Share PDF file"))


    }

    /**
     * Save PDF file in external storage using fileName from constructor
     */
    fun copyToExternal(name: String) {
        val input = FileInputStream(File(context.filesDir, fileName))
        val output = FileOutputStream(makeOutputFile(name))
        input.copyTo(output)
    }

    /**
     * CREATE FILE IN Documents/Faktury FOLDER
     * Also creates Faktury folder if not exists
     *
     * @param fileName Name of created file
     */
    private fun makeOutputFile(fileName: String) : File {
        val path =  Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS.plus("/Faktury/"))
        if (!path.exists()) {
            path.mkdirs()
        }
        val file = File(path, fileName.plus(".PDF"))
        Log.d("path", file.absolutePath)
        file.createNewFile()

        return file
    }

    /**
     * Creates PDF file from bytemap shown by webView
     * Use PdfPrint
     * @param webView WebView which shows invoice
     */
    private fun createWebPrintJob(webView: WebView) {
        val jobName = "Invoice Document"
        //ATRIBUTES OF CREATED FILE
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