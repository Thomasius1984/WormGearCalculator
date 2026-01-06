package net.fritz.walze.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import net.fritz.walze.WormGearCalculator
import java.util.Locale

@Composable
fun InputScreen(
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

    val hFf1f by calculator.hFf1f.collectAsState()
    val hFf2f by calculator.hFf2f.collectAsState()
    val cf1f by calculator.cf1f.collectAsState()
    val cf2f by calculator.cf2f.collectAsState()
    val d_a2f by calculator.d_a2f.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF1A1112))
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {

        Text(
            text = "Schneckengetriebe – Eingaben",
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineSmall,
            color = Color(0xFFE53935)
        )

        SectionHeader("Grundparameter")

        GreenInputField("Normalmodul mₙ", m_n, calculator::setM_n, "mm")
        GreenInputField("Mittenkreisdurchmesser Schnecke dm₁", d_m1, calculator::setD_m1, "mm")
        GreenInputField("Mittensteigungswinkel γ", gamma_degrees, calculator::setGamma_degrees, "°")
        GreenInputField("Kopfkreisdurchmesser Rad da₂", d_a2f, calculator::setDA2f, "mm")

        GreenInputField("Gangzahl Schnecke Z₁", z1, calculator::setZ1, "", isInteger = true)
        GreenInputField("Zähnezahl Rad Z₂", z2, calculator::setZ2, "", isInteger = true)

        GreenInputField("Achsabstand a", a, calculator::setA, "mm")

        Spacer(Modifier.height(16.dp))
        SectionHeader("Winkel")

        GreenInputField("Normaleingriffswinkel αₙz", alf_nz, calculator::setAlf_nz, "°")

        Spacer(Modifier.height(16.dp))
        SectionHeader("Fußfaktoren")

        GreenInputField("hFf₁f Fußformfaktor Schnecke", hFf1f, calculator::setHFf1f, "")
        GreenInputField("hFf₂f Fußformfaktor Rad", hFf2f, calculator::setHFf2f, "")
        GreenInputField("cf₁f Fußfreiheitfaktor Schnecke", cf1f, calculator::setCf1f, "")
        GreenInputField("cf₂f Fußfreiheitfaktor Rad", cf2f, calculator::setCf2f, "")

        Spacer(Modifier.height(32.dp))
    }
}

@Composable
fun GreenInputField(
    label: String,
    value: Double,
    onValueChange: (Double) -> Unit,
    unit: String,
    modifier: Modifier = Modifier,
    isInteger: Boolean = false
) {
    val displayUnit = unit.ifEmpty { "dim" }
    var text by remember { mutableStateOf("") }
    var hasFocus by remember { mutableStateOf(false) }

    // Initial- / externes Update
    LaunchedEffect(value) {
        if (!hasFocus) {
            text = if (value == 0.0) "" else {
                if (isInteger) {
                    value.toInt().toString()
                } else {
                    String.format(Locale.GERMANY, "%.4f", value)
                }
            }
        }
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = label,
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.bodyMedium,
            color = Color.White
        )

        TextField(
            value = text,
            onValueChange = { input ->
                val filtered = input
                    .replace('.', ',')
                    .filterIndexed { index, c ->
                        c.isDigit() || (c == ',' && input.indexOf(',') == index)
                    }

                text = filtered

                filtered.replace(',', '.').toDoubleOrNull()?.let {
                    onValueChange(it)
                }
            },
            modifier = Modifier
                .width(120.dp)
                .height(48.dp),
            keyboardOptions = KeyboardOptions(
                keyboardType = if (isInteger) KeyboardType.Number else KeyboardType.Decimal
            ),
            singleLine = true,
            textStyle = TextStyle(
                fontSize = 16.sp,
                color = Color.Black
            ),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0xFFDFF5E3),
                unfocusedContainerColor = Color(0xFFDFF5E3),
                cursorColor = Color.Black
            )
        )

        Spacer(Modifier.width(8.dp))

        Text(
            text = displayUnit,
            modifier = Modifier.width(32.dp),
            style = MaterialTheme.typography.labelSmall,
            color = Color.LightGray
        )
    }
}

@Composable
fun SectionHeader(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleSmall,
        color = Color(0xFFE53935),
        modifier = Modifier.padding(vertical = 12.dp)
    )
    Divider(color = Color.DarkGray)
}
