package net.fritz.walze.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import net.fritz.walze.ResultItem
import net.fritz.walze.WormGearCalculator

@Composable
fun ResultsScreen(
    calculator: WormGearCalculator,
    modifier: Modifier = Modifier
) {
    val m_n by calculator.m_n.collectAsState()
    val gamma_degrees by calculator.gamma_degrees.collectAsState()
    val z1 by calculator.z1.collectAsState()
    val z2 by calculator.z2.collectAsState()
    val a by calculator.a.collectAsState()
    val d_m1 by calculator.d_m1.collectAsState()
    val alf_nz by calculator.alf_nz.collectAsState()
    val ha_sternP by calculator.ha_sternP.collectAsState()
    val c_sternP by calculator.c_sternP.collectAsState()

    val results = remember(m_n, gamma_degrees, z1, z2, a, d_m1, alf_nz, ha_sternP, c_sternP) {
        calculator.calculateResults(m_n, gamma_degrees, z1, z2, a, d_m1, alf_nz, ha_sternP, c_sternP)
    }

    Column(modifier = modifier.fillMaxSize()) {
        Text(
            text = "Berechnungsergebnisse",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.CenterHorizontally)
        )

        if (results.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center)
            ) {
                Text(
                    text = "Ungültige Eingabewerte",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.error
                )
            }
        } else {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(results) { result ->
                    ResultItemRow(result)
                    Divider(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant
                    )
                }
            }
        }
    }
}

@Composable
fun ResultItemRow(result: ResultItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = result.name,
                style = MaterialTheme.typography.bodyMedium
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.wrapContentWidth()
        ) {
            Text(
                text = result.value,
                style = MaterialTheme.typography.bodyMedium,
                fontFamily = FontFamily.Monospace,
                color = MaterialTheme.colorScheme.primary
            )
            if (result.unit.isNotEmpty()) {
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = result.unit,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
