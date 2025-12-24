package net.fritz.walze.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun InfoScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Text(
            text = "Information",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // About Section
        VStack(spacing = 10.dp) {
            Text(
                text = "Über diese App",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            
            Text(
                text = "Diese App berechnet Parameter für Schneckengetriebe (Worm Gears) basierend auf Eingabewerten wie Normalmodul, Steigungswinkel und Zähnezahlen.",
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Divider(modifier = Modifier.padding(vertical = 16.dp))

        // Formulas Section
        VStack(spacing = 10.dp) {
            Text(
                text = "Wichtige Formeln",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            
            FormulaItem("Axialmodul", "m_x = m_n / cos(γ)")
            FormulaItem("Profilverschiebungsfaktor", "x_f = (a - m_n(Z₁ + Z₂)/2) / m_n")
            FormulaItem("Normale Teilung", "p_n = π × m_n")
            FormulaItem("Axialteilung", "p_x = π × m_x")
        }

        Divider(modifier = Modifier.padding(vertical = 16.dp))

        // Notes Section
        VStack(spacing = 10.dp) {
            Text(
                text = "Hinweise",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            
            Text("• Eingabewerte in der Eingabe-Ansicht ändern", style = MaterialTheme.typography.bodySmall)
            Text("• Ergebnisse werden automatisch berechnet", style = MaterialTheme.typography.bodySmall)
            Text("• Alle Berechnungen basieren auf DIN-Normen für Schneckengetriebe", style = MaterialTheme.typography.bodySmall)
        }

        Divider(modifier = Modifier.padding(vertical = 16.dp))

        // Copyright Section
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "© 2025 Thomas Fritz",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = "Alle Rechte vorbehalten",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
fun FormulaItem(name: String, formula: String, modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(vertical = 8.dp)) {
        Text(
            text = name,
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp),
            color = MaterialTheme.colorScheme.surfaceVariant,
            shape = MaterialTheme.shapes.small
        ) {
            Text(
                text = formula,
                style = MaterialTheme.typography.labelSmall,
                fontFamily = FontFamily.Monospace,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

@Composable
fun VStack(spacing: Dp = 0.dp, content: @Composable ColumnScope.() -> Unit) {
    Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(spacing)) {
        content()
    }
}
