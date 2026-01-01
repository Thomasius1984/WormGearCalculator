package net.fritz.walze.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import net.fritz.walze.ResultItem
import net.fritz.walze.WormGearCalculator

@Composable
fun ResultsScreen(
    calculator: WormGearCalculator
) {
    // =============================
    // Inputs aus ViewModel
    // =============================

    val m_n by calculator.m_n.collectAsState()
    val z1 by calculator.z1.collectAsState()
    val z2 by calculator.z2.collectAsState()
    val a by calculator.a.collectAsState()
    val d_m1 by calculator.d_m1.collectAsState()
    val cf1f by calculator.cf1f.collectAsState()
    val cf2f by calculator.cf2f.collectAsState()
    val hFf1f by calculator.hFf1f.collectAsState()
    val hFf2f by calculator.hFf2f.collectAsState()
    val gammaDeg by calculator.gamma_degrees.collectAsState()
    val d_a2f by calculator.d_a2f.collectAsState()

    // =============================
    // Berechnung
    // =============================

    val results = calculator.calculateResults(
        m_n = m_n,
        z1 = z1,
        z2 = z2,
        a = a,
        d_m1 = d_m1,
        cf1f = cf1f,
        cf2f = cf2f,
        hFf1f = hFf1f,
        hFf2f = hFf2f,
        gammaDeg = gammaDeg,
        d_a2f = d_a2f
    )

    // =============================
    // Gruppierung
    // =============================

    val schneckenwelle = results.filter {
        it.name.contains("Schnecke")
    }

    val schneckenrad = results.filter {
        it.name.contains("Rad")
    }

    val allgemein = results.filter {
        it.name.contains("Axialmodul") ||
                it.name.contains("Teilung") ||
                it.name.contains("Schraubparameter") ||
                it.name.contains("Mittensteigungswinkel") ||
                it.name.contains("Achsabstand") ||
                it.name.contains("Zähnezahl") ||
                it.name.contains("Kopfspiel")
    }

    // =============================
    // UI
    // =============================

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
            .verticalScroll(rememberScrollState())
    ) {
        FormulaSection(
            title = "Schneckenwelle",
            rows = schneckenwelle
        )

        FormulaSection(
            title = "Schneckenrad",
            rows = schneckenrad
        )

        FormulaSection(
            title = "Allgemein",
            rows = allgemein
        )
    }
}

// =======================================================
// SECTION (umrandeter Bereich)
// =======================================================

@Composable
fun FormulaSection(
    title: String,
    rows: List<ResultItem>
) {
    if (rows.isEmpty()) return

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium
            )


            Divider(
                modifier = Modifier.padding(vertical = 8.dp)
            )

            rows.forEach { item ->
                FormulaRow(item)
            }
        }
    }
}

// =======================================================
// EINZELNE ZEILE
// =======================================================

@Composable
fun FormulaRow(item: ResultItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = item.name,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(1f)
        )

        Text(
            text = "${item.value} ${item.unit}",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}
