package net.fritz.walze.print

import android.graphics.*
import android.graphics.pdf.PdfDocument
import android.print.PrintDocumentAdapter
import android.print.PrintAttributes
import android.print.PrintDocumentInfo
import net.fritz.walze.ResultItem
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

class ResultsPrintAdapter(
    private val results: List<ResultItem>
) : PrintDocumentAdapter() {

    private var pdfDocument: PdfDocument? = null

    override fun onLayout(
        oldAttributes: PrintAttributes?,
        newAttributes: PrintAttributes,
        cancellationSignal: android.os.CancellationSignal,
        callback: LayoutResultCallback,
        extras: android.os.Bundle?
    ) {
        pdfDocument = PdfDocument()
        callback.onLayoutFinished(
            PrintDocumentInfo.Builder("Fertigungswerte.pdf")
                .setContentType(PrintDocumentInfo.CONTENT_TYPE_DOCUMENT)
                .build(),
            true
        )
    }

    override fun onWrite(
        pages: Array<out android.print.PageRange>,
        destination: android.os.ParcelFileDescriptor,
        cancellationSignal: android.os.CancellationSignal,
        callback: WriteResultCallback
    ) {
        val pageInfo = PdfDocument.PageInfo.Builder(595, 842, 1).create()
        val page = pdfDocument!!.startPage(pageInfo)

        drawPage(page.canvas)

        pdfDocument!!.finishPage(page)

        val outputStream = FileOutputStream(destination.fileDescriptor)
        pdfDocument!!.writeTo(outputStream)
        outputStream.close()

        pdfDocument!!.close()
        callback.onWriteFinished(arrayOf(android.print.PageRange.ALL_PAGES))
    }

    private fun drawPage(canvas: Canvas) {
        val paint = Paint()
        paint.color = Color.BLACK

        var y = 60

        paint.textSize = 18f
        paint.textAlign = Paint.Align.CENTER
        canvas.drawText("Fertigungswerte", 300f, y.toFloat(), paint)

        y += 25
        paint.textSize = 10f
        val date = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(Date())
        canvas.drawText(date, 300f, y.toFloat(), paint)

        y += 30
        paint.textAlign = Paint.Align.LEFT
        paint.textSize = 11f

        results.forEach {
            canvas.drawText(
                "${it.name}: ${it.value} ${it.unit}",
                40f,
                y.toFloat(),
                paint
            )
            y += 16
        }
    }
}
