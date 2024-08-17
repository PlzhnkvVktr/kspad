package ru.avem.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import org.koin.compose.koinInject
import ru.avem.enums.Tests
import ru.avem.ui.screens.tests.*
import ru.avem.ui.viewmodels.TestScreenViewModel

@Composable
fun TestScreen(modifier: Modifier) {

    val vm = koinInject<TestScreenViewModel>()
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        when (vm.currentTest) {
            Tests.MGR -> MGRScreen()
            Tests.VIU -> VIUScreen()
            Tests.IKAS -> IKASScreen()
            Tests.HH -> HHScreen()
            Tests.MV -> MVScreen()
        }
        Row {
            Button(
                onClick = { vm.prev() },
                enabled = vm.testsListIterator.hasPrevious()
            ) {
                Text("Prev")
            }
            Button(
                onClick = { vm.next() },
                enabled = vm.testsListIterator.hasNext()
            ) {
                Text("Next")
            }
        }
    }
}