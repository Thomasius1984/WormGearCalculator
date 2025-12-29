package net.fritz.walze.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import net.fritz.walze.WormGearCalculator
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

    // neue Faktoren
    val hFf1f by calculator.hFf1f.collectAsState()
    val hFf2f by calculator.hFf2f.collectAsState()
    val cf1f by calculator.cf1f.collectAsState()
    val cf2f by calculator.cf2f.collectAsState()
    val d_a2f by calculator.d_a2f.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {

        Text(
            text = "Schneckengetriebe – Eingaben",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        SectionHeader("Grundparameter")

        GreenInputField("Normalmodul mₙ", m_n, calculator::setM_n, "")
        GreenInputField("Mittensteigungswinkel γ", gamma_degrees, calculator::setGamma_degrees, "°")
        GreenInputField("Gangzahl Schnecke Z₁", z1, calculator::setZ1, "")
        GreenInputField("Zähnezahl Rad Z₂", z2, calculator::setZ2, "")
        GreenInputField("Achsabstand a", a, calculator::setA, "mm")

        Spacer(Modifier.height(16.dp))
        SectionHeader("Durchmesser & Winkel")

        GreenInputField("Mittenkreisdurchmesser dₘ₁", d_m1, calculator::setD_m1, "mm")
        GreenInputField("Normaleingriffswinkel αₙz", alf_nz, calculator::setAlf_nz, "°")

        Spacer(Modifier.height(16.dp))
        SectionHeader("Fuß- und Kopffaktoren")

        GreenInputField(
            "hFf1f Fuß-Formhöhenfaktor Schnecke",
            hFf1f,
            calculator::setHFf1f,
            ""
        )

        GreenInputField(
            "hFf2f Fuß-Formhöhenfaktor Rad",
            hFf2f,
            calculator::setHFf2f,
            ""
        )

        GreenInputField(
            "cf1f Fuß-Freiheitsfaktor Schnecke",
            cf1f,
            calculator::setCf1f,
            ""
        )

        GreenInputField(
            "cf2f Fuß-Freiheitsfaktor Rad",
            cf2f,
            calculator::setCf2f,
            ""
        )

        GreenInputField(
            "Kopfkreisdurchmesser Schneckenrad dₐ2f (für hₐm₂f)",
            d_a2f,
            calculator::setDA2f,
            "mm"
        )

        Spacer(Modifier.height(32.dp))
    }
}
@Composable
fun GreenInputField(
    label: String,
    value: Double,
    onValueChange: (Double) -> Unit,
    unit: String,
    modifier: Modifier = Modifier
) {
    var textValue by remember(value) {
        mutableStateOf(
            if (value == 0.0) "" else value.toString().replace('.', ',')
        )
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
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(Modifier.width(8.dp))

        TextField(
            value = textValue,
            onValueChange = { newValue ->
                textValue = newValue
                newValue
                    .replace(',', '.')
                    .toDoubleOrNull()
                    ?.let(onValueChange)
            },
            modifier = Modifier.width(120.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0xFFDFF5E3),
                unfocusedContainerColor = Color(0xFFDFF5E3)
            )
        )

        if (unit.isNotEmpty()) {
            Spacer(Modifier.width(8.dp))
            Text(unit, style = MaterialTheme.typography.labelSmall)
        }
    }
}
@Composable
fun SectionHeader(title: String, modifier: Modifier = Modifier) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleSmall,
        color = MaterialTheme.colorScheme.primary,
        modifier = modifier.padding(vertical = 12.dp)
    )
    Divider()
}
