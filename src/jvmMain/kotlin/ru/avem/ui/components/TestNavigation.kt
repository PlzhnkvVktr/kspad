package ru.avem.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.avem.ui.viewmodels.TestScreenViewModel

@Composable
fun TestNavigation(
    vm: TestScreenViewModel,
    onMainScreen: () -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        MyButton(text = "Завершить все",
            onClick = {
                if (vm.currentTest.controller.isRun) {
                    vm.currentTest.controller.stop()
                }
                onMainScreen()
            },
            image = Icons.Default.Close,
            enabled = vm.isButtonDisabled
        )
        MyButton(text = "Предыдущий",
            onClick = {
                if (vm.currentTest.controller.isRun) {
                    vm.currentTest.controller.stop()
                }
                vm.prev()
            },
            image = Icons.Default.FirstPage,
            enabled = !vm.currentTest.controller.isRun && vm.isButtonDisabled
        )
        MyButton(text = "Стоп",
            onClick = {
                vm.viewModelScope.launch {
                    vm.currentTest.controller.stop()
                    vm.disableButton()
                }
            },
            image = Icons.Default.Stop, enabled = vm.currentTest.controller.isRun && vm.isButtonDisabled
        )
        MyButton(text = "Старт",
            onClick = {
                vm.viewModelScope.launch {
                    vm.currentTest.controller.start()
                    vm.disableButton()
                }
            },
            image = Icons.Default.PlayArrow,
            enabled = !vm.currentTest.controller.isRun &&  vm.isButtonDisabled
        )
        MyButton(
            text = "Следующий",
            onClick = {
                if (vm.currentTest.controller.isRun) {
                    vm.currentTest.controller.stop()
                }
                if (!vm.mainVM.testsListIterator.hasNext()) {
                    onMainScreen()
                } else {
                    vm.next()
                }
            },
            image = Icons.Default.LastPage,
            enabled = !vm.currentTest.controller.isRun && vm.isButtonDisabled
        )
    }
}