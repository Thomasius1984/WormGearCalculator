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
        var y = 60f

        // ===== Titel =====
        paint.textSize = 18f
        paint.textAlign = Paint.Align.CENTER
        paint.typeface = Typeface.DEFAULT_BOLD
        canvas.drawText("Fertigungswerte", 297.5f, y, paint)

        // ===== Datum (oben rechts, gleiche Höhe) =====
        paint.textSize = 10f
        paint.textAlign = Paint.Align.RIGHT
        paint.typeface = Typeface.DEFAULT
        val date = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(Date())
        canvas.drawText(date, rightMargin, y, paint)

        y += 40f

        // ===== Tabellenkopf =====
        paint.textAlign = Paint.Align.LEFT
        paint.textSize = 11f
        paint.typeface = Typeface.DEFAULT_BOLD

        canvas.drawText("Ergebnis", leftMargin, y, paint)
        canvas.drawText("Fertigung", 380f, y, paint)

        y += 8f
        canvas.drawLine(leftMargin, y, rightMargin, y, paint)
        y += 18f

        paint.typeface = Typeface.DEFAULT

        // ===== Gruppen =====
        y = drawSection(canvas, "Allgemein", y) { it.isAllgemein() }
        y = drawSection(canvas, "Schneckenwelle", y) { it.name.contains("Schnecke") }
        y = drawSection(canvas, "Schneckenrad", y) { it.name.contains("Rad") }

        // ===== Unterschrift =====
        y = 760f
        canvas.drawLine(300f, y, rightMargin, y, paint)
        y += 14f
        paint.textSize = 10f
        canvas.drawText("Unterschrift / Fertigung", 300f, y, paint)
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

        // Überschrift
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
