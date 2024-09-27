package ru.avem.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import org.koin.compose.koinInject
import ru.avem.ui.components.ActionButton
import ru.avem.ui.components.ScrollableLazyColumn
import ru.avem.ui.components.TabNavigationRow
import ru.avem.ui.components.UserListItem
import ru.avem.ui.viewmodels.AdminScreenViewModel
import ru.avem.ui.viewmodels.AuthScreenViewModel

@Composable
fun AdminScreen (
    modifier: Modifier,
    onMainScreen: () -> Unit,
    onProtocolScreen: () -> Unit,
    onTestObjectScreen: () -> Unit
) {

    val vm = koinInject<AdminScreenViewModel>()

//    var list1 = vm.list.collectAsState(initial = emptyList(), context = Dispatchers.Default)

    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        TabNavigationRow(onMainScreen, onProtocolScreen, onTestObjectScreen, null)
        Column {
            Row(
                modifier = Modifier.height(80.dp).fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = vm.textFind.value,
                    modifier = Modifier
                        .fillMaxWidth(.5f)
                        .height(80.dp)
                        .padding(10.dp),
                    textStyle = MaterialTheme.typography.h5,
                    placeholder = {
                        Text(
                            text = "Введите логин",
                            modifier = Modifier.fillMaxSize(),
                            style = MaterialTheme.typography.h5,
                        )
                    },
                    onValueChange = {
                        vm.textFind.value = it
                        vm.performSearch(it)
                    }
                )
                ActionButton("Сохранить", Icons.Default.Add){
                    vm.openAlertDialog.value = true
                }
            }
//            if (list1.value.isNotEmpty()) {
                ScrollableLazyColumn {
                    items(vm.activeUsers.value.size) {
                        UserListItem(vm.activeUsers.value[it],
                            {
                                    user -> vm.currentUser.value = vm.list.value[it]
                                vm.isAdminMode.value = true
                            }
                        ) {user ->
                            vm.deleteUser(vm.activeUsers.value[it])
                        }
                    }
                }
//            } else {
//                Row (Modifier.fillMaxSize().padding(top = 50.dp), horizontalArrangement = Arrangement.Center){
//                    Text("Пользователь не найдет", style = MaterialTheme.typography.h3)
//                }
//
//            }
        }
        if (vm.openAlertDialog.value) {
            Dialog(vm)
        }
        if (vm.isAdminMode.value) {
            UpdateUserDialog(vm)
        }
    }
}

@Composable
fun Dialog(
    viewModel: AdminScreenViewModel,
) {
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    androidx.compose.ui.window.Dialog(onDismissRequest = { }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
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
                    text = "Добавьте нового пользователя",
                    modifier = Modifier.padding(10.dp),
                    style = MaterialTheme.typography.h4
                )
                OutlinedTextField(
                    value = viewModel.login.value,
                    modifier = Modifier
                        .fillMaxWidth(.9f),
                    textStyle = MaterialTheme.typography.h5,
                    label = { Text("Логин") },
                    placeholder = {
                        Text(
                            text = "Введите логин",
                            style = MaterialTheme.typography.subtitle1,
                            modifier = Modifier.padding(top = 6.dp)
                        )
                    },
                    onValueChange = { viewModel.login.value = it }
                )
                if (viewModel.loginError.value != "") {
                    Text(viewModel.loginError.value, color = Color.Red)
                }
                OutlinedTextField(
                    value = viewModel.password.value,
                    modifier = Modifier
                        .fillMaxWidth(.9f),
                    onValueChange = { viewModel.password.value = it },
                    textStyle = MaterialTheme.typography.h5,
                    label = { Text("Пароль") },
                    singleLine = true,
                    placeholder = {
                        Text(
                            text = "Введите логин",
                            style = MaterialTheme.typography.subtitle1,
                            modifier = Modifier.padding(top = 6.dp)
                        )
                    },
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        val image = if (passwordVisible)
                            Icons.Filled.Visibility
                        else Icons.Filled.VisibilityOff
                        val description = if (passwordVisible) "Показать" else "Спрятать"

                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(imageVector = image, description)
                        }
                    }
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    TextButton(
                        onClick = {
                            viewModel.addUser()
//                            viewModel.clearFields()
                        },
                        modifier = Modifier.padding(8.dp),
                    ) {
                        Text(
                            "Сохранить",
                            style = MaterialTheme.typography.h4
                        )
                    }
                    TextButton(
                        onClick = {
                            viewModel.openAlertDialog.value = false
                            viewModel.clearFields()
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

@Composable
fun UpdateUserDialog(
    viewModel: AdminScreenViewModel,
) {
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    viewModel.login.value = viewModel.currentUser.value!!.login
    viewModel.password.value = viewModel.currentUser.value!!.password
    viewModel.switchOn.value = viewModel.currentUser.value!!.isAdmin
    androidx.compose.ui.window.Dialog(onDismissRequest = { }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
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
                    text = "Редактировать",
                    modifier = Modifier.padding(10.dp),
                    style = MaterialTheme.typography.h4
                )
                OutlinedTextField(
                    value = viewModel.login.value,
                    modifier = Modifier
                        .fillMaxWidth(.9f),
                    textStyle = MaterialTheme.typography.h5,
                    label = { Text("Логин") },
                    placeholder = {
                        Text(
                            text = "Введите логин",
                            style = MaterialTheme.typography.subtitle1,
                            modifier = Modifier.padding(top = 6.dp)
                        )
                    },
                    onValueChange = { viewModel.login.value = it }
                )
                OutlinedTextField(
                    value = viewModel.password.value,
                    modifier = Modifier
                        .fillMaxWidth(.9f),
                    onValueChange = { viewModel.password.value = it },
                    textStyle = MaterialTheme.typography.h5,
                    label = { Text("Пароль") },
                    singleLine = true,
                    placeholder = {
                        Text(
                            text = "Введите пароль",
                            style = MaterialTheme.typography.subtitle1,
                            modifier = Modifier.padding(top = 6.dp)
                        )
                    },
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        val image = if (passwordVisible)
                            Icons.Filled.Visibility
                        else Icons.Filled.VisibilityOff
                        val description = if (passwordVisible) "Показать" else "Спрятать"

                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(imageVector = image, description)
                        }
                    }
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    TextButton(
                        onClick = {
                            viewModel.updateUser(viewModel.currentUser.value!!)
                            viewModel.isAdminMode.value = false
                        },
                        modifier = Modifier.padding(8.dp),
                    ) {
                        Text(
                            "Сохранить",
                            style = MaterialTheme.typography.h4
                        )
                    }
                    TextButton(
                        onClick = {
                            viewModel.isAdminMode.value = false
                            viewModel.clearFields()
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