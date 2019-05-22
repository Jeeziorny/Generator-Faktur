// CLASS MUST BE LOCATED IN android.print PACKAGE
// BECAUSE IT USE PrintDocumentAdapter.LayoutResultCallback CLASS
package android.print

import android.content.Context
import android.os.CancellationSignal
import android.os.ParcelFileDescriptor
import java.io.File



class PdfPrint (private val context: Context, private val printAttributes: PrintAttributes) {



    //COPY BYTEMAP FROM PrintDocumentAdapter CREATED BY WEBVIEW TO FILE
    fun write (printAdapter: PrintDocumentAdapter, fileName: String) {
        printAdapter.onLayout(null, printAttributes, null,
            object : PrintDocumentAdapter.LayoutResultCallback() {
                override fun onLayoutFinished(info: PrintDocumentInfo?, changed: Boolean) {
                    printAdapter.onWrite(arrayOf(PageRange.ALL_PAGES),
                        getOutputFile(fileName),
                        CancellationSignal(),
                        object : PrintDocumentAdapter.WriteResultCallback() {
                            override fun onWriteCancelled() {
                                super.onWriteCancelled()
                            }
                        })
                }
            }, null)
    }

    private fun getOutputFile(fileName: String) : ParcelFileDescriptor {

        //CREATE PATH FOLDER IF NOT EXISTS




        //CREATE EMPTY PDF FILE
        val file = File(context.filesDir, fileName)
        file.createNewFile()

        //RETURN DESCRIPTOR OF EMPTY PDF FILE
        return ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_WRITE)


    }

}