package net.fritz.walze.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.background
import androidx.compose.ui.graphics.Color
import net.fritz.walze.R

@Composable
fun IntroScreen(onContinue: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1A1112))   // 👈 kompletter Hintergrund
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Image(
                painter = painterResource(id = R.drawable.intro_image),
                contentDescription = null,
                modifier = Modifier.size(300.dp)
            )

            Spacer(Modifier.height(5.dp))

            Text(
                text = "Berechnung Zylinderschnecken-Radsatz",
                style = MaterialTheme.typography.headlineSmall,
                color = Color(0xFFE53935)
            )

            Spacer(Modifier.height(8.dp))

            Text(
                text = "SUPER-SCHLEIFER-FREUNDECLUB\n geprüfte Qualität",
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFFCCCCCC),
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(32.dp))

            Button(
                onClick = onContinue,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFE53935)
                )
            ) {
                Text("Drück mich :D", color = Color.White)
            }
        }
    }
}

