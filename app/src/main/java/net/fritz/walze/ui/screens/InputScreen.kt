package net.fritz.walze.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import net.fritz.walze.WormGearCalculator
import kotlin.math.round

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
    val ha_sternP by calculator.ha_sternP.collectAsState()
    val c_sternP by calculator.c_sternP.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Text(
            text = "Schneckengetriebe",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Grundparameter Section
        SectionHeader("Grundparameter")
        
        InputField(
            label = "Normalmodul m_n",
            value = m_n,
            onValueChange = { calculator.setM_n(it) },
            unit = "mm"
        )
        
        InputField(
            label = "Mittensteigungswinkel γ",
            value = gamma_degrees,
            onValueChange = { calculator.setGamma_degrees(it) },
            unit = "°"
        )
        
        InputField(
            label = "Gangzahl Schnecke Z₁",
            value = z1,
            onValueChange = { calculator.setZ1(it) },
            unit = ""
        )
        
        InputField(
            label = "Zähnezahl Rad Z₂",
            value = z2,
            onValueChange = { calculator.setZ2(it) },
            unit = ""
        )
        
        InputField(
            label = "Achsabstand a",
            value = a,
            onValueChange = { calculator.setA(it) },
            unit = "mm"
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Durchmesser & Winkel Section
        SectionHeader("Durchmesser & Winkel")
        
        InputField(
            label = "Mittenkreisdurchmesser d_m1",
            value = d_m1,
            onValueChange = { calculator.setD_m1(it) },
            unit = "mm"
        )
        
        InputField(
            label = "Normaleingriffswinkel α_nz",
            value = alf_nz,
            onValueChange = { calculator.setAlf_nz(it) },
            unit = "°"
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Faktoren Section
        SectionHeader("Faktoren")
        
        InputField(
            label = "Kopfhöhenfaktor ha*",
            value = ha_sternP,
            onValueChange = { calculator.setHa_sternP(it) },
            unit = ""
        )
        
        InputField(
            label = "Fußfreiheitsfaktor c*",
            value = c_sternP,
            onValueChange = { calculator.setC_sternP(it) },
            unit = ""
        )

        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
fun InputField(
    label: String,
    value: Double,
    onValueChange: (Double) -> Unit,
    unit: String,
    modifier: Modifier = Modifier
) {
    var textValue by remember(value) { 
        mutableStateOf(if (value == 0.0) "" else value.toString()) 
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.width(8.dp))
        TextField(
            value = textValue,
            onValueChange = { newValue ->
                textValue = newValue
                val doubleValue = newValue.toDoubleOrNull() ?: 0.0
                onValueChange(doubleValue)
            },
            modifier = Modifier.width(100.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            singleLine = true,
            textStyle = MaterialTheme.typography.bodySmall
        )
        if (unit.isNotEmpty()) {
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = unit,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun SectionHeader(title: String, modifier: Modifier = Modifier) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleSmall,
        color = MaterialTheme.colorScheme.primary,
        modifier = modifier
            .padding(vertical = 12.dp, horizontal = 0.dp)
    )
    Divider(modifier = Modifier.padding(bottom = 8.dp))
}
