package net.fritz.walze.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

enum class Screen {
    Intro,
    Info,
    Result
}

class NavigationViewModel : ViewModel() {

    var currentScreen by mutableStateOf(Screen.Intro)
        private set

    fun navigateTo(screen: Screen) {
        currentScreen = screen
    }
}
