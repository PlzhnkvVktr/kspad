package ru.avem.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import composables.ui.ComboBox
import org.koin.compose.koinInject
import ru.avem.components.TestObjectCard
import ru.avem.ui.components.ActionButton
import ru.avem.ui.components.TabNavigationRow
import ru.avem.ui.components.TestListContainer
import ru.avem.ui.viewmodels.MainScreenViewModel

@Composable
fun MainScreen (
    modifier: Modifier = Modifier,
    onTestScreen: () -> Unit,
    onProtocolScreen: () -> Unit,
    onTestObjectScreen: () -> Unit,
    onAdminScreen: () -> Unit,
) {

    val vm = koinInject<MainScreenViewModel>()

    LaunchedEffect(key1 = "Main_Screen") {
        vm.testList.clear()
        vm.clearTestList()
        vm.listTestItems.clear()
    }

    println(vm.testList)
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TabNavigationRow(null, onProtocolScreen, onTestObjectScreen, onAdminScreen)
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.35f),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TestObjectCard(vm, vm.factoryNumber1, vm.selectedTI1, vm.card1)
            TestObjectCard(vm, vm.factoryNumber2, vm.selectedTI2, vm.card2)
            TestObjectCard(vm, vm.factoryNumber3, vm.selectedTI3, vm.card3)
        }
//        Row (
//            modifier = Modifier.fillMaxWidth().padding(top = 20.dp),
//            horizontalArrangement = Arrangement.Center
//        ) {
//            Text("Заполните все поля",  style = MaterialTheme.typography.h3)
//        }
//        Row (
//            modifier = Modifier.fillMaxWidth().padding(top = 10.dp).height(100.dp),
//            horizontalArrangement = Arrangement.SpaceAround,
//            Alignment.CenterVertically
//        ) {
//            Text(
//                modifier = Modifier.width(200.dp),
//                text = "Заводской номер",
//                style = MaterialTheme.typography.h5,
//            )
//            OutlinedTextField(
//                value = vm.factoryNumber,
//                modifier = Modifier
//                    .fillMaxWidth(0.8f),
//                textStyle = MaterialTheme.typography.h5,
//                placeholder =  {
//                    Text(
//                        text = "Введите серийный номер",
//                        style = MaterialTheme.typography.h5,
//                        modifier = Modifier.padding(top = 6.dp),
//                        color = Color.Red
//                    ) },
//                onValueChange = {vm.factoryNumber = it}
//            )
//        }
//        Row (
//            modifier = Modifier.fillMaxWidth().padding(top = 10.dp).height(100.dp),
//            horizontalArrangement = Arrangement.SpaceAround,
//            Alignment.CenterVertically
//        ) {
//            Text(
//                modifier = Modifier.width(200.dp),
//                text = "Тип",
//                style = MaterialTheme.typography.h5
//            )
//            ComboBox(vm.selectedTI, modifier = Modifier.fillMaxWidth(0.8f), items = vm.typesTI)
//        }
        Row (
            modifier = Modifier.fillMaxWidth().padding(top = 10.dp).fillMaxHeight(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column (
                modifier = Modifier
                    .width(200.dp)
                    .fillMaxHeight(.6f),
                verticalArrangement = Arrangement.SpaceBetween
            ){
                ActionButton(
                    text = if (!vm.allCheckedButton.value) "Выбрать все" else "Отменить все",
                    pic = if (!vm.allCheckedButton.value) Icons.Filled.Check else Icons.Filled.Close,
                    onClick = vm::selectAll
                )
                ActionButton(
                    text = "Старт",
                    pic = Icons.Filled.PlayArrow,
//                    disabled = (
//                            vm.testList.isNotEmpty()
////                            vm.startTestButton.value
////                                    && vm.factoryNumber.isNotEmpty()
//                            ),
                    onClick = { if (vm.testsListIterator.hasNext()) vm.startTests(onTestScreen) }
                )
            }
            TestListContainer()
        }
    }
}

