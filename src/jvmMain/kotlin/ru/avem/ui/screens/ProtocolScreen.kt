package ru.avem.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.koin.compose.koinInject
import ru.avem.ui.components.ProtocolListItem
import ru.avem.ui.components.ScrollableLazyColumn
import ru.avem.ui.components.TabNavigationRow
import ru.avem.ui.viewmodels.ProtocolScreenViewModel
import ru.avem.ui.viewmodels.TestScreenViewModel

@Composable
fun ProtocolScreen (
    modifier: Modifier,
    onMainScreen: () -> Unit,
    onAdminScreen: () -> Unit,
    onTestObjectScreen: () -> Unit
) {

    val vm = koinInject<ProtocolScreenViewModel>()

    Column {
        TabNavigationRow(onMainScreen, null, onTestObjectScreen, onAdminScreen)
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = vm.textFind.value,
                modifier = Modifier.fillMaxWidth(.5f).height(80.dp).padding(10.dp),
                textStyle = MaterialTheme.typography.h5,
                placeholder = {
                    Text(
                        text = "Введите название",
                        modifier = Modifier.fillMaxSize(),
                        style = MaterialTheme.typography.h5,
                    )
                },
                onValueChange = {
                    vm.textFind.value = it
                    vm.performSearch(it)
                }
            )
        }
        if (vm.protocolList.isNotEmpty()) {
            ScrollableLazyColumn {
                items(vm.protocolList.size) {
                    ProtocolListItem(vm.protocolList[it],
//                                    {protocol -> viewModel.savePDF(protocol)},
                        {protocol -> vm.saveExcel(protocol)}
                    ) {protocol ->
                        vm.deleteProtocol(vm.protocolList[it])
                    }
                }
            }
        } else {
            Row (Modifier.fillMaxSize().padding(top = 50.dp), horizontalArrangement = Arrangement.Center){
                Text("Протоколы не найдены", style = MaterialTheme.typography.h3)
            }

        }
    }
}