package net.fritz.walze.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.*
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.decode.GifDecoder
import coil.request.ImageRequest
import androidx.compose.ui.platform.LocalContext
import net.fritz.walze.R

/* ---------- Datenmodell ---------- */

data class FormulaRow(
    val name: String,
    val formula: AnnotatedString
)

/* ---------- Subscript-Helfer ---------- */

fun sub(text: String): AnnotatedString =
    buildAnnotatedString {
        withStyle(
            SpanStyle(
                fontSize = 12.sp,
                baselineShift = BaselineShift.Subscript
            )
        ) {
            append(text)
        }
    }

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
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineSmall,
            color = Color(0xFFE53935)
        )

        /* ================= Allgemein ================= */

        InfoFormulaSection(
            title = "Allgemein",
            rows = listOf(

                FormulaRow(
                    "Mittensteigungswinkel γ (Grad)",
                    buildAnnotatedString {
                        append("γ = arctan(Z"); append(sub("1"))
                        append(" · m"); append(sub("x"))
                        append(" / dm"); append(sub("1"))
                        append(")")
                    }
                ),

                FormulaRow(
                    "Axialmodul",
                    buildAnnotatedString {
                        append("m"); append(sub("x"))
                        append(" = m"); append(sub("n"))
                        append(" / cos(γ)")
                    }
                ),

                FormulaRow(
                    "Normalteilung",
                    buildAnnotatedString {
                        append("p"); append(sub("n"))
                        append(" = π · m"); append(sub("n"))
                    }
                ),

                FormulaRow(
                    "Axialteilung",
                    buildAnnotatedString {
                        append("p"); append(sub("x"))
                        append(" = π · m"); append(sub("x"))
                    }
                ),

                FormulaRow(
                    "Schneckenganghöhe",
                    buildAnnotatedString {
                        append("p"); append(sub("z"))
                        append(" = p"); append(sub("x"))
                        append(" · Z"); append(sub("1"))
                    }
                ),

                FormulaRow(
                    "Eingriffswinkel",
                    buildAnnotatedString {
                        append("α"); append(sub("x"))
                        append(" = arctan(tan(α"); append(sub("n"))
                        append(") / cos(γ))")
                    }
                ),

                FormulaRow(
                    "Achsabstand",
                    buildAnnotatedString {
                        append("a = (d"); append(sub("m1"))
                        append(" + d"); append(sub("m2"))
                        append(") / 2")
                    }
                ),

                FormulaRow(
                    "Zähnezahlverhältnis",
                    buildAnnotatedString {
                        append("u = Z"); append(sub("2"))
                        append(" / Z"); append(sub("1"))
                    }
                ),

                FormulaRow(
                    "Formzahl",
                    buildAnnotatedString {
                        append("q = Z"); append(sub("1"))
                        append(" / tan(γ)")
                    }
                ),

                FormulaRow(
                    "Normalzahndicke",
                    buildAnnotatedString {
                        append("s"); append(sub("mn"))
                        append(" = (m"); append(sub("x"))
                        append(" · π · cos(γ)) / 2")
                    }
                )
            )
        )

        /* ================= Faktoren ================= */

        InfoFormulaSection(
            title = "Faktoren & Näherungen",
            rows = listOf(
                FormulaRow(
                    "Kopfhöhenfaktor Schnecke",
                    buildAnnotatedString {
                        append("hamf"); append(sub("1"))
                        append(" = cos(γ)")
                    }
                ),
                FormulaRow(
                    "Kopfhöhenfaktor Schneckenrad (Näherung)",
                    buildAnnotatedString {
                        append("hamf"); append(sub("2"))
                        append(" ≈ (d"); append(sub("a2f"))
                        append(" − d"); append(sub("m2"))
                        append(") / (2 · m"); append(sub("x"))
                        append(") − xf"); append(sub("2"))
                    }
                )
            )
        )

        /* ================= Schnecke ================= */

        InfoFormulaSection(
            title = "Schnecke",
            rows = listOf(

                FormulaRow(
                    "Mittenkreisdurchmesser",
                    buildAnnotatedString {
                        append("dm"); append(sub("1"))
                        append(" = (m"); append(sub("n"))
                        append(" · Z"); append(sub("1"))
                        append(") / sin(γ)")
                    }
                ),

                FormulaRow(
                    "Kopfhöhe",
                    buildAnnotatedString {
                        append("ha"); append(sub("1"))
                        append(" = hamf"); append(sub("1"))
                        append(" · m"); append(sub("x"))
                    }
                ),

                FormulaRow(
                    "Kopfkreisdurchmesser",
                    buildAnnotatedString {
                        append("da"); append(sub("1"))
                        append(" = dm"); append(sub("1"))
                        append(" + 2 · ha"); append(sub("1"))
                    }
                ),

                FormulaRow(
                    "Kopfkreisradius",
                    buildAnnotatedString {
                        append("ra"); append(sub("1"))
                        append(" = da"); append(sub("1"))
                        append(" / 2")
                    }
                ),

                FormulaRow(
                    "Fußhöhe",
                    buildAnnotatedString {
                        append("hf"); append(sub("1"))
                        append(" = m"); append(sub("x"))
                        append(" · (hFf"); append(sub("1f"))
                        append(" + cf"); append(sub("1f"))
                        append(")")
                    }
                ),

                FormulaRow(
                    "Fußkreisdurchmesser",
                    buildAnnotatedString {
                        append("df"); append(sub("1"))
                        append(" = dm"); append(sub("1"))
                        append(" − 2 · hf"); append(sub("1"))
                    }
                ),

                FormulaRow(
                    "Fußkreisradius",
                    buildAnnotatedString {
                        append("rf"); append(sub("1"))
                        append(" = df"); append(sub("1"))
                        append(" / 2")
                    }
                ),

                FormulaRow(
                    "Zahnhöhe",
                    buildAnnotatedString {
                        append("h"); append(sub("1"))
                        append(" = ha"); append(sub("1"))
                        append(" + hf"); append(sub("1"))
                    }
                ),

                FormulaRow(
                    "Kopfspiel",
                    buildAnnotatedString {
                        append("c"); append(sub("1"))
                        append(" = a − 0,5 · (da"); append(sub("1"))
                        append(" + df"); append(sub("2"))
                        append(")")
                    }
                )
            )
        )
        /* ================= SCHNECKENRAD ================= */

        InfoFormulaSection(
            title = "Schneckenrad",
            rows = listOf(

                FormulaRow(
                    "Teilkreisdurchmesser",
                    buildAnnotatedString {
                        append("d"); append(sub("2"))
                        append(" = Z"); append(sub("2"))
                        append(" · m"); append(sub("x"))
                    }
                ),

                FormulaRow(
                    "Profilverschiebungsfaktor",
                    buildAnnotatedString {
                        append("xf"); append(sub("2"))
                        append(" = (2a − dm"); append(sub("1"))
                        append(" − m"); append(sub("x"))
                        append(" · Z"); append(sub("2"))
                        append(") / (2 · m"); append(sub("x"))
                        append(")")
                    }
                ),

                FormulaRow(
                    "Profilverschiebung",
                    buildAnnotatedString {
                        append("x"); append(sub("m"))
                        append(" = xf"); append(sub("2"))
                        append(" · m"); append(sub("x"))
                    }
                ),

                FormulaRow(
                    "Mittenkreisdurchmesser",
                    buildAnnotatedString {
                        append("dm"); append(sub("2"))
                        append(" = d"); append(sub("2"))
                        append(" + 2 · x"); append(sub("m"))
                    }
                ),

                FormulaRow(
                    "Kopfhöhe",
                    buildAnnotatedString {
                        append("ha"); append(sub("2"))
                        append(" = hamf"); append(sub("2"))
                        append(" · m"); append(sub("x"))
                    }
                ),

                FormulaRow(
                    "Kopfkreisdurchmesser",
                    buildAnnotatedString {
                        append("da"); append(sub("2"))
                        append(" = dm"); append(sub("2"))
                        append(" + 2 · ha"); append(sub("2"))
                    }
                ),

                FormulaRow(
                    "Fußhöhe",
                    buildAnnotatedString {
                        append("hf"); append(sub("2"))
                        append(" = m"); append(sub("x"))
                        append(" · (hFf"); append(sub("2f"))
                        append(" + cf"); append(sub("2f"))
                        append(")")
                    }
                ),

                FormulaRow(
                    "Fußkreisdurchmesser",
                    buildAnnotatedString {
                        append("df"); append(sub("2"))
                        append(" = d"); append(sub("2"))
                        append(" − 2 · hf"); append(sub("2"))
                    }
                ),

                FormulaRow(
                    "Zahnhöhe",
                    buildAnnotatedString {
                        append("h"); append(sub("2"))
                        append(" = ha"); append(sub("2"))
                        append(" + hf"); append(sub("2"))
                    }
                ),

                FormulaRow(
                    "Kopfspiel",
                    buildAnnotatedString {
                        append("c"); append(sub("2"))
                        append(" = a − 0,5 · (da"); append(sub("2"))
                        append(" + df"); append(sub("1"))
                        append(")")
                    }
                )
            )
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Hinweis:\n" +
                    "Diese App dient der Überprüfung eines Zylinderschnecken-Radsatzes auf geometrische Geschlossenheit.\n" +
                    "Sie ersetzt keine normgerechte Konstruktion. ",
            style = MaterialTheme.typography.bodySmall,
            color = Color(0xFFCCCCCC)
    )
        Spacer(modifier = Modifier.height(16.dp))

        /* ================= Footer ================= */

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(R.drawable.superschleifer)
                    .decoderFactory(GifDecoder.Factory())
                    .build(),
                contentDescription = "SuperSchleifer",
                modifier = Modifier
                    .size(48.dp)
                    .padding(end = 8.dp)
            )

            Text(
                text = "© 2025 Thomas Fritz vom SuperSchleiferFreundeClub",
                style = MaterialTheme.typography.labelSmall,
                color = Color(0xFF888888)
            )
        }
    }
}

/* ---------- UI ---------- */

@Composable
fun InfoFormulaSection(
    title: String,
    rows: List<FormulaRow>
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF24191A))
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
                Text(it.name, color = Color.White)
                Spacer(Modifier.height(4.dp))
                Text(it.formula, color = Color.LightGray)
                Spacer(Modifier.height(8.dp))
                HorizontalDivider(color = Color(0xFF3A2A2B))
            }
        }
    }
}


