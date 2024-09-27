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
fun HHScreen() {
    val vm = koinInject<TestScreenViewModel>()
    Column (
        modifier = Modifier
            .fillMaxSize()
    ) {
        Row(Modifier.background(Color.Gray)) {
            TableCell(text = "№")
            TableCell(text = "U uv, B")
            TableCell(text = "U vw, B")
            TableCell(text = "U wu, B")
            TableCell(text = "cosφ")
            TableCell(text = "I u, A")
            TableCell(text = "I v, A")
            TableCell(text = "I w, A")
        }
        Row() {
            TableCell(text = vm.testItem.name.value)
            TableCell(text = vm.testItem.u_uv_hh.value)
            TableCell(text = vm.testItem.u_vw_hh.value)
            TableCell(text = vm.testItem.u_wu_hh.value)
            TableCell(text = vm.testItem.cos_hh.value)
            TableCell(text = vm.testItem.i_u_hh.value)
            TableCell(text = vm.testItem.i_v_hh.value)
            TableCell(text = vm.testItem.i_w_hh.value)
        }
    }
}