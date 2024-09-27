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
import ru.avem.ui.components.TestWindowTitle
import ru.avem.ui.viewmodels.TestScreenViewModel

@Composable
fun IKASScreen() {
    val vm = koinInject<TestScreenViewModel>()
    Column (
        modifier = Modifier
            .fillMaxSize()
    ) {
        TestWindowTitle("Измеренные значения")
        Row(Modifier.background(Color.Gray)) {
            TableCell(text = "№")
            TableCell(text = "R uv, Ом")
            TableCell(text = "R vw, Ом")
            TableCell(text = "R wu, Ом")
        }
        Row() {
            TableCell(text = vm.testItem.name.value)
            TableCell(text = vm.testItem.r_uv_ikas.value)
            TableCell(text = vm.testItem.r_vw_ikas.value)
            TableCell(text = vm.testItem.r_wu_ikas.value)
        }
        TestWindowTitle("Фазное")
        Row(Modifier.background(Color.Gray)) {
            TableCell(text = "№")
            TableCell(text = "R u, Ом")
            TableCell(text = "R v, Ом")
            TableCell(text = "R w, Ом")
        }
        Row() {
            TableCell(text = vm.testItem.name.value)
            TableCell(text = vm.testItem.calc_u_ikas.value)
            TableCell(text = vm.testItem.calc_v_ikas.value)
            TableCell(text = vm.testItem.calc_v_ikas.value)
        }
    }
}