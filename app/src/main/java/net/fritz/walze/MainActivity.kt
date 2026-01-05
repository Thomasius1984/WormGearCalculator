package net.fritz.walze

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import net.fritz.walze.ui.screens.IntroScreen
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.outlined.BarChart
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import net.fritz.walze.ui.screens.InfoScreen
import net.fritz.walze.ui.screens.InputScreen
import net.fritz.walze.ui.screens.ResultsScreen
import net.fritz.walze.ui.theme.WalzeTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            WalzeTheme {

                var showIntro by rememberSaveable { mutableStateOf(true) }

                if (showIntro) {
                    IntroScreen(
                        onContinue = { showIntro = false }
                    )
                } else {
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun MainScreen() {

    val calculator: WormGearCalculator = viewModel()

    var selectedTab by rememberSaveable { mutableIntStateOf(0) }

    Scaffold(
        bottomBar = {
            NavigationBar {

                NavigationBarItem(
                    icon = { Icon(Icons.Default.Edit, null) },
                    label = { Text("Eingabe") },
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 }
                )

                NavigationBarItem(
                    icon = { Icon(Icons.Outlined.BarChart, null) },
                    label = { Text("Ergebnisse") },
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 }
                )

                NavigationBarItem(
                    icon = { Icon(Icons.Default.Info, null) },
                    label = { Text("Info") },
                    selected = selectedTab == 2,
                    onClick = { selectedTab = 2 }
                )
            }
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            when (selectedTab) {
                0 -> InputScreen(calculator)
                1 -> ResultsScreen(calculator)
                2 -> InfoScreen()
            }
        }
    }
}
