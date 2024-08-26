package ru.avem.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ru.avem.ui.components.ComboBox
import ru.avem.ui.viewmodels.MainScreenViewModel

@Composable
fun TestObjectCard (
    vm: MainScreenViewModel,
    factoryNumber: MutableState<String>,
    selectedTI: MutableState<String>,
    isVisible: MutableState<Boolean> = mutableStateOf(true)
) {


    Card (
        modifier = Modifier.width(650.dp).height(250.dp).padding(15.dp),
        border = BorderStroke(2.dp, Color.Gray)
    ) {
        if (isVisible.value) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(0.88f).fillMaxHeight(),
                    verticalArrangement = Arrangement.SpaceAround
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth().height(70.dp),
                        horizontalArrangement = Arrangement.SpaceAround,
                        Alignment.CenterVertically
                    ) {
                        Text(
                            modifier = Modifier.width(120.dp),
                            text = "Заводской номер",
                            style = MaterialTheme.typography.h5,)
                        OutlinedTextField(
                            value = factoryNumber.value,
                            modifier = Modifier.fillMaxWidth(0.8f).height(56.dp),
                            textStyle = MaterialTheme.typography.h5,
                            placeholder = {
                                Text(
                                    text = "Введите серийный номер",
                                    style = MaterialTheme.typography.subtitle1
                                ) },
                            onValueChange = { factoryNumber.value = it }
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth().height(70.dp),
                        horizontalArrangement = Arrangement.SpaceAround,
                        Alignment.CenterVertically
                    ) {
                        Text(
                            modifier = Modifier.width(120.dp),
                            text = "Тип",
                            style = MaterialTheme.typography.h5
                        )
                        ComboBox(selectedTI, modifier = Modifier.fillMaxWidth(0.8f), items = vm.typesTI)
                    }
                }
                Column(
                    modifier = Modifier.fillMaxWidth().fillMaxHeight(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(
                        onClick = {
                            isVisible.value = !isVisible.value
                        }
                    ) {
                        Icon(
                            Icons.Filled.Delete,
                            contentDescription = "Удалить",
                            modifier = Modifier.size(40.dp)
                        )
                    }
                }
            }
        } else {
            Button(
                onClick = { isVisible.value = !isVisible.value }
            ) {
                Icon(
                    Icons.Filled.Add,
                    contentDescription = "Добавить",
                    modifier = Modifier.size(50.dp)
                )
            }
        }
    }
}