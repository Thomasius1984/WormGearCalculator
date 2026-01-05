package net.fritz.walze.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

/* ---------- Datenmodell ---------- */

data class FormulaRow(
    val name: String,
    val formula: String
)

/* ---------- Info Screen ---------- */

@Composable
fun InfoScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1A1112))
            .padding(8.dp)
            .verticalScroll(rememberScrollState())
    ) {

        Text(
            text = "Formelsammlung",
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineSmall,
            color = Color(0xFFE53935)
        )

        InfoFormulaSection(
            title = "Allgemein",
            rows = listOf(
                FormulaRow("Mittensteigungswinkel γ (Grad)", "γ = arctan(Z₁ · mₓ / dm₁)"),
                FormulaRow("Axialmodul", "mₓ = mₙ / cos(γ)"),
                FormulaRow("Profilverschiebung", "xₘ = xf₂ · mₓ"),
                FormulaRow("Normale Teilung", "pₙ = π · mₙ"),
                FormulaRow("Axialteilung", "pₓ = π · mₓ"),
                FormulaRow("Schneckenganghöhe", "p_z = pₓ · Z₁"),
                FormulaRow("Eingriffswinkel", "αₓ = arctan(tan(αₙ) / cos(γ))"),
                FormulaRow("Achsabstand", "a = (dm₁ + dm₂) / 2"),
                FormulaRow("Zähnezahlverhältnis", "u = Z₂ / Z₁"),
                FormulaRow("Formzahl", "q = Z₁ / tan(γ)"),
                FormulaRow("Mittenkreisdurchmesser Schnecke", "dm₁ = (mₙ · Z₁) / sin(γ)")
            )
        )

        InfoFormulaSection(
            title = "Faktoren & Näherungen",
            rows = listOf(
                FormulaRow("Kopfhöhenfaktor Schnecke", "hamf₁ = cos(γ)"),
                FormulaRow(
                    "Kopfhöhenfaktor Schneckenrad (Näherung)",
                    "hamf₂ ≈ (da₂f − dm₂) / (2·mₓ) − xf₂"
                )
            )
        )

        InfoFormulaSection(
            title = "Schnecke",
            rows = listOf(
                FormulaRow("Kopfhöhe Schnecke", "ha₁ = hamf₁ · mₓ"),
                FormulaRow("Kopfkreisdurchmesser", "da₁ = dm₁ + 2·ha₁"),
                FormulaRow("Kopfkreisradius", "ra₁ = da₁ / 2"),
                FormulaRow("Fußhöhe Schnecke", "hf₁ = mₓ · (hFf₁f + cf₁f)"),
                FormulaRow("Fußkreisdurchmesser", "df₁ = dm₁ − 2·hf₁"),
                FormulaRow("Fußkreisradius", "rf₁ = df₁ / 2"),
                FormulaRow("Zahnhöhe Schnecke", "h₁ = ha₁ + hf₁"),
                FormulaRow("Kopfspiel", "c₁ = a − 0,5·(da₁ + df₂)")
            )
        )

        InfoFormulaSection(
            title = "Schneckenrad",
            rows = listOf(
                FormulaRow("Teilkreisdurchmesser", "d₂ = Z₂ · mₓ"),
                FormulaRow(
                    "Profilverschiebungsfaktor",
                    "xf₂ = (2a − dₘ₁ − mₓ·Z₂) / (2·mₓ)"
                ),
                FormulaRow("Profilverschiebung", "xₘ = xf₂ · mₓ"),
                FormulaRow("Mittenkreisdurchmesser", "dm₂ = d₂ + 2·xₘ"),
                FormulaRow("Kopfhöhe Rad", "ha₂ = hamf₂ · mₓ"),
                FormulaRow("Kopfkreisdurchmesser", "da₂ = dm₂ + 2·ha₂"),
                FormulaRow("Fußhöhe Rad", "hf₂ = mₓ · (hFf₂f + cf₂f)"),
                FormulaRow("Fußkreisdurchmesser", "df₂ = d₂ − 2·hf₂"),
                FormulaRow("Zahnhöhe Rad", "h₂ = ha₂ + hf₂"),
                FormulaRow("Kopfspiel", "c₂ = a − 0,5 · (da₂ + df₁)")
            )
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Hinweis: Die Formeln dienen der ingenieurmäßigen Vorauslegung " +
                    "und ersetzen keine normgerechte Auslegung nach DIN.",
            style = MaterialTheme.typography.bodySmall,
            color = Color(0xFFCCCCCC)
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "© 2025 Thomas Fritz vom SuperSchleiferFreundeClub",
            style = MaterialTheme.typography.labelSmall,
            color = Color(0xFF888888)
        )
    }
}

/* ---------- UI Bausteine ---------- */

@Composable
fun InfoFormulaSection(
    title: String,
    rows: List<FormulaRow>
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
        Column(modifier = Modifier.padding(12.dp)) {

            Text(
                text = title,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleMedium,
                color = Color(0xFFE53935)
            )

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 8.dp),
                color = Color.DarkGray
            )

            rows.forEach {
                InfoFormulaRow(it)
            }
        }
    }
}

@Composable
fun InfoFormulaRow(item: FormulaRow) {
    Column(modifier = Modifier.fillMaxWidth()) {

        // Bezeichnung
        Text(
            text = item.name,
            color = Color.White,
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(6.dp))

        // Formel (weiter links, unter der Bezeichnung)
        Text(
            text = item.formula,
            color = Color.LightGray,
            style = MaterialTheme.typography.bodyMedium,
            fontFamily = FontFamily.Monospace
        )

        Spacer(modifier = Modifier.height(8.dp))

        HorizontalDivider(
            color = Color(0xFF3A2A2B),
            thickness = 0.8.dp
        )
    }
}
