package ru.avem.ui.screens.tests

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import org.koin.compose.koinInject
import ru.avem.ui.components.TableCell
import ru.avem.ui.viewmodels.TestScreenViewModel

@Composable
fun VIUScreen() {
    val vm = koinInject<TestScreenViewModel>()
    Column (
        modifier = Modifier
            .fillMaxSize()
    ) {
        Row(Modifier.background(Color.Gray)) {
            TableCell(text = "№")
            TableCell(text = "U, В")
            TableCell(text = "I, мА")
            TableCell(text = "t, сек")
            TableCell(text = "Результат")
        }
        Row() {
            TableCell(text = vm.testItem.name.value)
            TableCell(text = vm.testItem.u_viu.value)
            TableCell(text = vm.testItem.i_viu.value)
            TableCell(text = vm.testItem.t_viu.value)
            TableCell(text = vm.testItem.res_viu.value)
        }
    }
}