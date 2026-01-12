package net.fritz.walze.print

import android.graphics.*
import android.graphics.pdf.PdfDocument
import android.print.PrintAttributes
import android.print.PrintDocumentAdapter
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
                .setPageCount(1)
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

    // =====================================================
    // ZEICHNEN
    // =====================================================

    private fun drawPage(canvas: Canvas) {
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.color = Color.BLACK

        val leftMargin = 40f
        val rightMargin = 555f
        val centerX = 297.5f
        var y = 50f

        // =================================================
        // KOPFZEILE: TITEL | UNTERSCHRIFT | DATUM/UHRZEIT
        // =================================================

        // Titel links
        paint.textSize = 14f
        paint.typeface = Typeface.DEFAULT_BOLD
        paint.textAlign = Paint.Align.LEFT
        canvas.drawText("Fertigungswerte", leftMargin, y, paint)

        // Unterschriftenlinie mittig
        val signLineWidth = 140f
        canvas.drawLine(
            centerX - signLineWidth / 2,
            y,
            centerX + signLineWidth / 2,
            y,
            paint
        )

        paint.textSize = 9f
        paint.typeface = Typeface.DEFAULT
        paint.textAlign = Paint.Align.CENTER
        canvas.drawText("Unterschrift", centerX, y + 12f, paint)

        // Datum + Uhrzeit rechts
        paint.textAlign = Paint.Align.RIGHT
        paint.textSize = 10f

        val dateTime = SimpleDateFormat(
            "dd.MM.yyyy HH:mm",
            Locale.getDefault()
        ).format(Date())

        canvas.drawText(dateTime, rightMargin, y, paint)

        // Trennlinie
        y += 22f
        canvas.drawLine(leftMargin, y, rightMargin, y, paint)
        y += 20f

        // =================================================
        // SPALTENÜBERSCHRIFT
        // =================================================
        paint.textAlign = Paint.Align.LEFT
        paint.textSize = 11f
        paint.typeface = Typeface.DEFAULT_BOLD

        canvas.drawText("Ergebnis", leftMargin, y, paint)
        canvas.drawText("Fertigung", 380f, y, paint)

        y += 8f
        canvas.drawLine(leftMargin, y, rightMargin, y, paint)
        y += 18f

        paint.typeface = Typeface.DEFAULT

        // =================================================
        // GRUPPEN
        // =================================================
        y = drawSection(canvas, "Allgemein", y) { it.isAllgemein() }
        y = drawSection(canvas, "Schneckenwelle", y) { it.name.contains("Schnecke") }
        y = drawSection(canvas, "Schneckenrad", y) { it.name.contains("Rad") }
    }

    // =====================================================
    // SECTION
    // =====================================================

    private fun drawSection(
        canvas: Canvas,
        title: String,
        startY: Float,
        filter: (ResultItem) -> Boolean
    ): Float {

        var y = startY
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.color = Color.BLACK

        val sectionItems = results.filter(filter)
        if (sectionItems.isEmpty()) return y

        paint.textSize = 12f
        paint.typeface = Typeface.DEFAULT_BOLD
        canvas.drawText(title, 40f, y, paint)

        y += 10f
        canvas.drawLine(40f, y, 555f, y, paint)
        y += 16f

        paint.textSize = 11f
        paint.typeface = Typeface.DEFAULT

        sectionItems.forEach {
            canvas.drawText(
                "${it.name}: ${it.value} ${it.unit}",
                40f,
                y,
                paint
            )

            canvas.drawRect(
                370f,
                y - 12f,
                555f,
                y + 4f,
                Paint().apply {
                    style = Paint.Style.STROKE
                    strokeWidth = 1f
                }
            )

            y += 18f
        }

        y += 10f
        return y
    }

    // =====================================================
    // KATEGORIE-HELPER
    // =====================================================

    private fun ResultItem.isAllgemein(): Boolean {
        return name.contains("Axial") ||
                name.contains("Normal") ||
                name.contains("Mittensteigungswinkel in Grad") ||
                name.contains("Mittenkreisdurchmesser") ||
                name.contains("Achsabstand") ||
                name.contains("Eingriffswinkel") ||
                name.contains("Zähnezahl") ||
                name.contains("Schraub")
    }
}

