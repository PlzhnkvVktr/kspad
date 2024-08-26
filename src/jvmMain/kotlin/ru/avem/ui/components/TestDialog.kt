package ru.avem.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun TestDialog(
    text: String = "Нажмите кнопку ПУСК",
    other: Boolean = false,
    action1: () -> Unit = {},
    action2: () -> Unit = {}
) {
    Dialog(onDismissRequest = { }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(getWindowHeight(text))
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.SpaceAround,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = text,
                    modifier = Modifier.padding(30.dp),
                    style = MaterialTheme.typography.h4
                )
                if (other) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                    ) {
                        TextButton(
                            onClick = {
                                action1()
                            },
                            modifier = Modifier.padding(8.dp),
                        ) {
                            Text(
                                "Ок",
                                style = MaterialTheme.typography.h4
                            )
                        }
                        TextButton(
                            onClick = {
                                action2()
                            },
                            modifier = Modifier.padding(8.dp),
                        ) {
                            Text(
                                "Отмена",
                                style = MaterialTheme.typography.h4
                            )
                        }
                    }
                }
            }
        }
    }
}

fun getWindowHeight(text: String): Dp =
    when {
        text.count() > 200 -> 600
        text.count() in 100..200 -> 500
        text.count() < 100 -> 400
        else -> 400
    }.dp
