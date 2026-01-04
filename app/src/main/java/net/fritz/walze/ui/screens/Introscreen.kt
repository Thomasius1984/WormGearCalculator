package net.fritz.walze.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import net.fritz.walze.R

@Composable
fun IntroScreen(onContinue: () -> Unit) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1A1112)),
        contentAlignment = Alignment.Center
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(24.dp)
        ) {

            // =========================
            // ÜBERSCHRIFT
            // =========================
            Text(
                text = "Berechnung Zylinderschnecken-Radsatz",
                style = MaterialTheme.typography.headlineSmall,
                color = Color(0xFFE53935),
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(24.dp))

            // =========================
            // BILD
            // =========================
            Image(
                painter = painterResource(id = R.drawable.intro_image),
                contentDescription = null,
                modifier = Modifier.size(320.dp)
            )

            Spacer(Modifier.height(24.dp))

            // =========================
            // BUTTON UNTER DEM BILD
            // =========================
            Button(
                onClick = onContinue,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFE53935)
                )
            ) {
                Text("Drück mich :D", color = Color.White)
            }

            Spacer(Modifier.height(56.dp)) // 👈 weiter nach unten

            // =========================
            // UNTERER TEXT
            // =========================
            Text(
                text = "SUPER-SCHLEIFER-FREUNDECLUB",
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFFCCCCCC),
                textAlign = TextAlign.Center
            )
        }
    }
}

