package ru.avem.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.koin.compose.koinInject
import ru.avem.data.models.TestObject
import ru.avem.ui.viewmodels.TestScreenViewModel

@Composable
fun SpecifiedParamsList() {

    val vm = koinInject<TestScreenViewModel>()

    Column (
        modifier = Modifier
            .fillMaxSize()
            .border(1.dp, Color.Black, RoundedCornerShape(10.dp))
            .padding(10.dp)
            .verticalScroll(rememberScrollState())
            .clip(RoundedCornerShape(10.dp)),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Номинальные параметры", style = MaterialTheme.typography.h5, textAlign = TextAlign.Center)

        SpecifiedParamsItem("Наименование", vm.testItem.selectedTI?.name.toString())
        SpecifiedParamsItem("Схема", vm.testItem.selectedTI?.scheme.toString())
        SpecifiedParamsItem("Ток, A", vm.testItem.selectedTI?.i.toString())
        SpecifiedParamsItem("Напряжение, U", vm.testItem.selectedTI?.u_linear.toString())
        SpecifiedParamsItem("Мощность, P", vm.testItem.selectedTI?.power.toString())

    }
}

@Composable
fun SpecifiedParamsItem (text: String, paramValue: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .border(1.dp, Color.Black, RoundedCornerShape(10.dp))
            .clip(RoundedCornerShape(10.dp))
    ) {
        Column(
            modifier = Modifier
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.subtitle1,
                modifier = Modifier.fillMaxWidth().background(Color.LightGray),
                textAlign = TextAlign.Center
            )
            Text(
                text = when (paramValue) {
                    "true" -> "λ"
                    "false" -> "∆"
                    else -> paramValue
                },
                style = MaterialTheme.typography.subtitle1,
                modifier = Modifier.fillMaxWidth().padding(top = 7.dp),
                textAlign = TextAlign.Center
            )
        }
    }
}