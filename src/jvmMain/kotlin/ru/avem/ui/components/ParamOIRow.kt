package ru.avem.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ru.avem.data.models.ParamOI

@Composable
fun ParamOIRow(
    item: ParamOI
) {
    fun validateParam(param: MutableState<String>) =
        param.value.toDoubleOrNull() != null && param.value.toDouble() > item.max.toDouble()
                || param.value.toDoubleOrNull() != null && param.value.toDouble() < item.min.toDouble()
                || param.value == ""
                || param.value.toList().last().toString() == "."

    item.isError.value = if (!item.isString) validateParam(item.param) else item.param.value.isEmpty()

    Row(
        modifier = Modifier.fillMaxWidth().height(60.dp).padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 5.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(0.6f)
        ) {
            Text(text = item.title, style = MaterialTheme.typography.h5)
        }
        Column(
            modifier = Modifier.fillMaxWidth(0.8f)
        ) {
            OutlinedTextField(
                value = item.param.value,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .border(2.dp, if (item.isError.value) Color.Red else Color.Green),
                textStyle = MaterialTheme.typography.body1,
                placeholder = {
                    Text(
                        text = if (!item.isString) "min: ${item.min}, max: ${item.max}" else "Введите значение",
                        style = MaterialTheme.typography.caption,
                    )
                },
                isError = validateParam(item.param),
                onValueChange = {
                    if (item.isString) {
                        item.param.value = it
                    } else {
                        if (it.isEmpty()) item.param.value = it
                        it.toDoubleOrNull()?.let { value ->
                            item.isError.value = value in (item.min.toDouble()..item.max.toDouble())
                            item.param.value = it
                        }
                    }

                },
            )
        }
    }
}

// рабочая регулярка, доступен ввод только Double и Int
fun isValidText(text: String): Boolean {
    return text.matches(Regex(pattern = "^-?\\d*[.]?\\d*\$"))
}


