package ru.avem.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.sharp.Logout
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import composables.ui.EntryField
import org.koin.compose.koinInject
import ru.avem.ui.components.ActionButton
import ru.avem.ui.components.ComboBox
import ru.avem.ui.viewmodels.AuthScreenViewModel

@Composable
fun AuthScreen (
    modifier: Modifier = Modifier,
    onSubmit: () -> Unit
) {
    val vm = koinInject<AuthScreenViewModel>()

    Scaffold(
        content = {
            Column (
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Card (
                    modifier = Modifier.fillMaxSize(0.5f),
                    elevation = 10.dp
                ) {
                    Column (
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceAround
                    ) {
                        Row (
                            modifier = Modifier.fillMaxWidth(.7f),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column (
                                modifier = Modifier.fillMaxHeight(.4f),
                                verticalArrangement = Arrangement.SpaceAround
                            ) {
                                Text("Оператор", style = MaterialTheme.typography.h3)
                                Text(
                                    modifier = Modifier.width(200.dp),
                                    text = "Пароль",
                                    style = MaterialTheme.typography.h3,
                                )
                            }
                            Column (
                                modifier = Modifier.fillMaxHeight(.4f),
                                verticalArrangement = Arrangement.SpaceAround
                            ) {
                                ComboBox(
                                    vm.selectedUser,
                                    modifier = Modifier.fillMaxWidth(.9f),
                                    items = vm.users
                                )
                                OutlinedTextField(
                                    value = vm.password.value,
                                    modifier = Modifier.fillMaxWidth(.9f),
                                    onValueChange = { vm.password.value = it },
                                    textStyle = MaterialTheme.typography.h5,
                                    label = { Text("Пароль") },
                                    singleLine = true,
                                    placeholder =  {
                                        Text(
                                            text = "Введите пароль",
                                            style = MaterialTheme.typography.subtitle1,
                                            modifier = Modifier.padding(top = 6.dp)
                                        ) },
                                    visualTransformation = if (vm.passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
                                    trailingIcon = {
                                        val image = if (vm.passwordVisible.value)
                                            Icons.Filled.Visibility
                                        else Icons.Filled.VisibilityOff

                                        val description = if (vm.passwordVisible.value) "Hide password" else "Show password"

                                        IconButton(onClick = {vm.passwordVisible.value = !vm.passwordVisible.value}){
                                            Icon(imageVector  = image, description)
                                        }
                                    }
                                )
                            }
                        }
                        Row (
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            ActionButton(
                                text = "Вход",
                                pic = Icons.Sharp.Logout,
                                disabled = vm.password.value != ""
                            ){
                                vm.signIn(onSubmit)
                            }
                        }
                        Row (
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(if (vm.isError.value) "Неправильный логин или пароль" else "", color = Color.Red, style = MaterialTheme.typography.h4)
                        }
                    }
                }
            }
        },
    )

}