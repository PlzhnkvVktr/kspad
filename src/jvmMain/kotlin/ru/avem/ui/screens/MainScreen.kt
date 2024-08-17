package ru.avem.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import composables.ui.ComboBox
import org.koin.compose.koinInject
import org.koin.java.KoinJavaComponent.getKoin
import org.koin.java.KoinJavaComponent.inject
import ru.avem.enums.Tests
import ru.avem.ui.components.ActionButton
import ru.avem.ui.screens.tests.*
import ru.avem.ui.viewmodels.MainScreenViewModel
import ru.avem.ui.viewmodels.TestScreenViewModel

@Composable
fun MainScreen (modifier: Modifier = Modifier) {

    val vm = koinInject<MainScreenViewModel>()

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row (
            modifier = Modifier.fillMaxWidth().padding(top = 20.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Text("Заполните все поля",  style = MaterialTheme.typography.h3)
        }
        Row (
            modifier = Modifier.fillMaxWidth().padding(top = 10.dp).height(100.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.width(200.dp),
                text = "Заводской номер",
                style = MaterialTheme.typography.h5,
            )
            OutlinedTextField(
                value = vm.factoryNumber,
                modifier = Modifier
                    .fillMaxWidth(0.8f),
                textStyle = MaterialTheme.typography.h5,
                placeholder =  {
                    Text(
                        text = "Введите серийный номер",
                        style = MaterialTheme.typography.h5,
                        modifier = Modifier.padding(top = 6.dp),
                        color = Color.Red
                    ) },
                onValueChange = {vm.factoryNumber = it}
            )
        }
        Row (
            modifier = Modifier.fillMaxWidth().padding(top = 10.dp).height(100.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.width(200.dp),
                text = "Тип",
                style = MaterialTheme.typography.h5
            )
            ComboBox(vm.selectedTI, modifier = Modifier.fillMaxWidth(0.8f), items = vm.typesTI)
        }

    }
}

