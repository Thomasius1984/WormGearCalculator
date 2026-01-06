package net.fritz.walze.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import net.fritz.walze.ResultItem
import net.fritz.walze.WormGearCalculator
import net.fritz.walze.print.ResultsPrintHelper

@Composable
fun ResultsScreen(
    calculator: WormGearCalculator
) {
    val context = LocalContext.current

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
    val alf_nz by calculator.alf_nz.collectAsState()

    val results = calculator.calculateResults(
        m_n, z1, z2, a, d_m1,
        cf1f, cf2f, hFf1f, hFf2f,
        gammaDeg, d_a2f, alf_nz
    )

    val allgemein = results.filter {
        it.name.contains("Axialmodul") ||
                it.name.contains("Mittensteigungswinkel in Grad") ||
                it.name.contains("Axialteilung") ||
                it.name.contains("Schraub") ||
                it.name.contains("Normalteilung") ||
                it.name.contains("Mittenkreisdurchmesser Rad") ||
                it.name.contains("Mittenkreisdurchmesser Schnecke") ||
                it.name.contains("Achsabstand") ||
                it.name.contains("Eingriffswinkel") ||
                it.name.contains("Formzahl q") ||
                it.name.contains("Normalzahndicke") ||
                it.name.contains("Zähnezahlverhältnis")
    }

    val schneckenwelle = results.filter { it.name.contains("Schnecke") }
    val schneckenrad = results.filter { it.name.contains("Rad") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1A1112))
            .padding(8.dp)
            .verticalScroll(rememberScrollState())
    ) {

        Button(
            onClick = { ResultsPrintHelper.print(context, results) },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFE53935),
                contentColor = Color.White
            )
        ) {
            Text("PDF / Druckansicht")
        }

        Spacer(Modifier.height(12.dp))

        FormulaSection("Allgemein", allgemein)
        FormulaSection("Schneckenwelle", schneckenwelle)
        FormulaSection("Schneckenrad", schneckenrad)
    }
}

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
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF24191A)
        )
    ) {
        Column(Modifier.padding(12.dp)) {

            Text(
                text = title,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleMedium,
                color = Color(0xFFE53935)
            )

            Divider(
                modifier = Modifier.padding(vertical = 8.dp),
                color = Color.DarkGray
            )

            rows.forEach {
                FormulaRow(it)
            }
        }
    }
}

@Composable
fun FormulaRow(item: ResultItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 3.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = item.name,
            modifier = Modifier.weight(1f),
            color = Color.White,
            style = MaterialTheme.typography.bodyMedium
        )

        Text(
            text = "${item.value} ${item.unit}",
            color = Color.LightGray,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}
