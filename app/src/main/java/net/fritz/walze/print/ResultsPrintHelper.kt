package net.fritz.walze.print

import android.content.Context
import android.print.PrintAttributes
import android.print.PrintManager
import net.fritz.walze.ResultItem

object ResultsPrintHelper {

    fun print(context: Context, results: List<ResultItem>) {
        val printManager = context.getSystemService(Context.PRINT_SERVICE) as PrintManager

        val adapter = ResultsPrintAdapter(results)

        printManager.print(
            "Fertigungswerte",
            adapter,
            PrintAttributes.Builder()
                .setMediaSize(PrintAttributes.MediaSize.ISO_A4)
                .setResolution(PrintAttributes.Resolution("pdf", "pdf", 300, 300))
                .setMinMargins(PrintAttributes.Margins.NO_MARGINS)
                .build()
        )
    }
}
