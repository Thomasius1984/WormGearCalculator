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
                    "Mittensteigungswinkel γ (rad)",
                    "γ = arctan(Z₁ · mₙ / dₘ₁)"
                ),
                FormulaRow(
                    "Mittensteigungswinkel γ (Grad)",
                    "γ° = γ · 180 / π"
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
                    "hₐ₁ = hₐₘ₁ · mₓ"
                ),
                FormulaRow(
                    "Kopfkreisdurchmesser",
                    "dₐ₁ = dₘ₁ + 2·hₐ₁"
                ),
                FormulaRow(
                    "Kopfkreisradius",
                    "rₐ₁ = dₐ₁ / 2"
                ),
                FormulaRow(
                    "Fußhöhe Schnecke",
                    "h_f₁ = mₓ · (h_Ff₁f + c_f₁f)"
                ),
                FormulaRow(
                    "Fußkreisdurchmesser",
                    "d_f₁ = dₘ₁ − 2·h_f₁"
                ),
                FormulaRow(
                    "Fußkreisradius",
                    "r_f₁ = d_f₁ / 2"
                ),
                FormulaRow(
                    "Zahnhöhe Schnecke",
                    "h₁ = hₐ₁ + h_f₁"
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
                    "dₘ₂ = d₂ + 2·xₘ"
                ),
                FormulaRow(
                    "Kopfhöhe Rad",
                    "hₐ₂ = hₐₘ₂ · mₓ"
                ),
                FormulaRow(
                    "Kopfkreisdurchmesser",
                    "dₐ₂ = dₘ₂ + 2·hₐ₂"
                ),
                FormulaRow(
                    "Fußhöhe Rad",
                    "h_f₂ = mₓ · (h_Ff₂f + c_f₂f)"
                ),
                FormulaRow(
                    "Fußkreisdurchmesser",
                    "d_f₂ = d₂ − 2·h_f₂"
                ),
                FormulaRow(
                    "Zahnhöhe Rad",
                    "h₂ = hₐ₂ + h_f₂"
                )
            )
        )

        FormulaSection(
            title = "Faktoren & Näherungen",
            rows = listOf(
                FormulaRow(
                    "Kopfhöhenfaktor Schnecke",
                    "hₐₘ₁ = cos(γ)"
                ),
                FormulaRow(
                    "Kopfhöhenfaktor Schneckenrad (Näherung)",
                    "hₐₘ₂ ≈ (dₐ₂f − dₘ₂) / (2·mₓ)"
                )
            )
        )

        FormulaSection(
            title = "Achsabstand",
            rows = listOf(
                FormulaRow(
                    "Achsabstand",
                    "a = (dₘ₁ + dₘ₂) / 2"
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
            text = "© 2025 Thomas Fritz vom SuperSchleiferFreundeClub :DDD",
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
