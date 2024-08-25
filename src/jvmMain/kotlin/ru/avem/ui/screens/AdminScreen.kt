package ru.avem.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.avem.ui.components.TabNavigationRow

@Composable
fun AdminScreen (modifier: Modifier, onMainScreen: () -> Unit, onProtocolScreen: () -> Unit) {

    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        TabNavigationRow(onMainScreen, onProtocolScreen, null)
        Text("ADMIN")
    }
}