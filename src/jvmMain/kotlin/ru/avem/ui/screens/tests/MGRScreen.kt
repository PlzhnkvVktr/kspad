package ru.avem.ui.screens.tests

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import org.koin.compose.koinInject
import ru.avem.ui.components.TableCell
import ru.avem.ui.viewmodels.TestScreenViewModel

@Composable
fun MGRScreen() {

    val vm = koinInject<TestScreenViewModel>()

    Column (
        modifier = Modifier
            .fillMaxSize()
    ) {
        Row(Modifier.background(Color.Gray)) {
            TableCell(text = "№")
            TableCell(text = "U, В")
            TableCell(text = "R15, MОм")
            TableCell(text = "R60, MОм")
            TableCell(text = "kабс")
            TableCell(text = "Результат")
        }
        repeat(3) {
            Row() {
                TableCell(text = (it + 1).toString())
                TableCell(text = vm.testItem.mgrU.value)
                TableCell(text = vm.testItem.r15.value)
                TableCell(text = vm.testItem.r60.value)
                TableCell(text = vm.testItem.kABS.value)
                TableCell(text = vm.testItem.res_mgr.value)
            }
        }
    }
}