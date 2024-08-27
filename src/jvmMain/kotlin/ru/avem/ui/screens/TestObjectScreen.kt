package ru.avem.ui.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.koin.compose.koinInject
import ru.avem.ui.components.*
import ru.avem.ui.viewmodels.TestObjectScreenViewModel

@Composable
fun TestObjectScreen (
    modifier: Modifier,
    onMainScreen: () -> Unit,
    onProtocolScreen: () -> Unit,
    onAdminScreen: () -> Unit,
) {
    val vm = koinInject<TestObjectScreenViewModel>()

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TabNavigationRow(onMainScreen,  onProtocolScreen,null, onAdminScreen)
        Row(
            modifier = Modifier.fillMaxSize().padding(10.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(0.2f).fillMaxHeight(),
                verticalArrangement = Arrangement.Center
            ) {
                Text("Объект испытания")
                ComboBox(
                    vm.selectedTI,
                    modifier = Modifier.fillMaxWidth(),
                    items = vm.typesTI,
                    onClick = vm::initTestTI
                )
                Row(
                    modifier = Modifier.fillMaxWidth().padding(top = 20.dp),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Button(onClick = {
                        vm.addNewTI.value = !vm.addNewTI.value
                        if (vm.addNewTI.value) vm.clearFields() else vm.initTestTI()
                    }) {
                        Icon(
                            if (!vm.addNewTI.value) Icons.Filled.Add else Icons.Filled.ArrowBack,
                            contentDescription = "Информация о приложении", modifier = Modifier.size(50.dp)
                        )
                    }
                    Button(onClick = {
                        vm.deleteTI()
                        vm.openDialogAndUpdate("Объект испытания удален")
                    }, enabled = vm.db.getAllTI().size != 1) {
                        Icon(
                            imageVector = Icons.Filled.Delete,
                            contentDescription = "Информация о приложении", modifier = Modifier.size(50.dp)
                        )
                    }
                }
            }
            Column(
                modifier = Modifier.fillMaxWidth(0.7f).fillMaxHeight().border(2.dp, Color.DarkGray)
            ){
                CreatorOI(vm)
            }
            Column(
                modifier = Modifier.fillMaxWidth(0.35f).fillMaxHeight(),
                verticalArrangement = Arrangement.Bottom
            ) {
                ActionButton(
                    if (!vm.addNewTI.value) "Сохранить" else "Добавить",
                    Icons.Filled.Check,
                    disabled = vm.paramsList.none { it.isError.value }
                ) {
                    vm.addNewTI()
                    vm.openDialogAndUpdate(if (!vm.addNewTI.value) "Объект испытания сохранен" else "Объект испытания добавлен")
                }
            }
        }
        if (vm.dialogWindow.value) {
            TestDialog(vm.dialogText.value)
        }
    }
}