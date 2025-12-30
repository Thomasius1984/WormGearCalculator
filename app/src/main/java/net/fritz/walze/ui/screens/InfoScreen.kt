package net.fritz.walze.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp

/* ---------- Datenmodell ---------- */

data class FormulaRow(
    val variable: String,
    val formula: String
)

/* ---------- Info Screen ---------- */

@Composable
fun InfoScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {

        Text(
            text = "Formelsammlung Schneckengetriebe",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        FormulaSection(
            title = "Grundlagen",
            rows = listOf(
                FormulaRow(
                    "Mittensteigungswinkel γ (Grad)",
                    "γ = arctan(Z₁ · m_x / dm₁)"
                ),
                FormulaRow(
                    "Axialmodul",
                    "mₓ = mₙ / cos(γ)"
                ),
                FormulaRow(
                    "Profilverschiebungsfaktor",
                    "x_f = (2a − dₘ₁ − mₓ·Z₂) / (2·mₓ)"
                ),
                FormulaRow(
                    "Profilverschiebung",
                    "xₘ = x_f · mₓ"
                ),
                FormulaRow(
                    "Normale Teilung",
                    "pₙ = π · mₙ"
                ),
                FormulaRow(
                    "Axialteilung",
                    "pₓ = π · mₓ"
                ),
                FormulaRow(
                    "Schneckenganghöhe",
                    "p_z = pₓ · Z₁"
                ),
                FormulaRow(
                    "Normaleingriffswinkel (rad)",
                    "αₙ = αₙ(z) · π / 180"
                )
            )
        )

        FormulaSection(
            title = "Schnecke",
            rows = listOf(
                FormulaRow(
                    "Kopfhöhe Schnecke",
                    "ha₁ = ham₁ · mₓ"
                ),
                FormulaRow(
                    "Kopfkreisdurchmesser",
                    "da₁ = dm₁ + 2·ha₁"
                ),
                FormulaRow(
                    "Kopfkreisradius",
                    "ra₁ = da₁ / 2"
                ),
                FormulaRow(
                    "Fußhöhe Schnecke",
                    "h_f₁ = mₓ · (hFf₁f + cf₁f)"
                ),
                FormulaRow(
                    "Fußkreisdurchmesser",
                    "d_f₁ = dm₁ − 2·hf₁"
                ),
                FormulaRow(
                    "Fußkreisradius",
                    "r_f₁ = df₁ / 2"
                ),
                FormulaRow(
                    "Zahnhöhe Schnecke",
                    "h₁ = ha₁ + hf₁"
                )
            )
        )

        FormulaSection(
            title = "Schneckenrad",
            rows = listOf(
                FormulaRow(
                    "Teilkreisdurchmesser",
                    "d₂ = Z₂ · mₓ"
                ),
                FormulaRow(
                    "Profilverschiebung",
                    "xₘ = x_f · mₓ"
                ),
                FormulaRow(
                    "Mittenkreisdurchmesser",
                    "dm₂ = d₂ + 2·xₘ"
                ),
                FormulaRow(
                    "Kopfhöhe Rad",
                    "ha₂ = ham₂ · mₓ"
                ),
                FormulaRow(
                    "Kopfkreisdurchmesser",
                    "da₂ = dm₂ + 2·hₐ₂"
                ),
                FormulaRow(
                    "Fußhöhe Rad",
                    "hf₂ = mₓ · (hFf₂f + cf₂f)"
                ),
                FormulaRow(
                    "Fußkreisdurchmesser",
                    "df₂ = d₂ − 2·hf₂"
                ),
                FormulaRow(
                    "Zahnhöhe Rad",
                    "h₂ = ha₂ + hf₂"
                )
            )
        )

        FormulaSection(
            title = "Faktoren & Näherungen",
            rows = listOf(
                FormulaRow(
                    "Kopfhöhenfaktor Schnecke",
                    "hamf₁ = cos(γ)"
                ),
                FormulaRow(
                    "Kopfhöhenfaktor Schneckenrad (Näherung)",
                    "hamf₂ ≈ (da₂f − dm₂) / (2·mₓ) - x_f"
                )
            )
        )

        FormulaSection(
            title = "Achsabstand",
            rows = listOf(
                FormulaRow(
                    "Achsabstand",
                    "a = (dm₁ + dm₂) / 2"
                )
            )
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Hinweis: Die Formeln dienen der ingenieurmäßigen Vorauslegung " +
                    "und ersetzen keine normgerechte Auslegung nach DIN.",
            style = MaterialTheme.typography.bodySmall
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "© 2025 Thomas Fritz vom SuperSchleiferFreundeClub",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

/* ---------- UI Bausteine ---------- */

@Composable
fun FormulaSection(
    title: String,
    rows: List<FormulaRow>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp)
    ) {

        Surface(
            color = MaterialTheme.colorScheme.surfaceVariant,
            shape = MaterialTheme.shapes.small
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier.padding(8.dp)
            )
        }

        rows.forEach { row ->
            FormulaRowItem(row)
            Divider()
        }
    }
}

@Composable
fun FormulaRowItem(row: FormulaRow) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = row.variable,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.weight(1.2f)
        )
        Text(
            text = row.formula,
            style = MaterialTheme.typography.bodySmall,
            fontFamily = FontFamily.Monospace,
            modifier = Modifier.weight(1f)
        )
    }
}
