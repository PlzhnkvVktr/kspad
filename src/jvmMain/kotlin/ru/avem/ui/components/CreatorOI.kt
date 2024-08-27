package ru.avem.ui.components

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ru.avem.data.models.ParamOI
import ru.avem.ui.viewmodels.TestObjectScreenViewModel

@Composable
fun CreatorOI (vm: TestObjectScreenViewModel) {
//    val lazyListState: LazyListState = rememberLazyListState()
    Column(
        modifier = Modifier
            .fillMaxSize()
//            .verticalScroll(rememberScrollState())
    ){
        Text(text = "Значения", style = MaterialTheme.typography.h4, modifier = Modifier.padding(20.dp))
        ParamOIRow(item = ParamOI(
            title = "Тип",
            param = vm.name,
            min = 1,
            max = 200,
            isString = true
        ))

        Row(
            modifier = Modifier.fillMaxWidth().height(60.dp).padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 5.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(0.6f)
            ) {
                Text(text = "Схема", style = MaterialTheme.typography.h5)
            }
            Column(
                modifier = Modifier.fillMaxWidth(0.8f)
            ) {
                Row {
                    Text("∆", style = MaterialTheme.typography.h4, modifier = Modifier.padding(top = 5.dp, start = 10.dp))
                    Switch(
                        checked = vm.scheme.value,
                        onCheckedChange = {
                            vm.scheme.value = it
                        }
                    )
                    Text("λ", style = MaterialTheme.typography.h4, modifier = Modifier.padding(top = 5.dp))
                }
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth().height(60.dp).padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 5.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(0.6f)
            ) {
                Text(text = "Тип изоляции пластин", style = MaterialTheme.typography.h5)
            }
            Column(
                modifier = Modifier.fillMaxWidth(0.8f)
            ) {
                Row {
                    Text("Оксидная", style = MaterialTheme.typography.h4, modifier = Modifier.padding(top = 5.dp, start = 10.dp))
                    Switch(
                        checked = vm.isolation.value,
                        onCheckedChange = {
                            vm.isolation.value = it
                        }
                    )
                    Text("Лак", style = MaterialTheme.typography.h4, modifier = Modifier.padding(top = 5.dp))
                }
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth().height(60.dp).padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 5.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(0.6f)
            ) {
                Text(text = "Материал корпуса", style = MaterialTheme.typography.h5)
            }
            Column(
                modifier = Modifier.fillMaxWidth(0.8f)
            ) {
                Row {
                    Text("Сталь", style = MaterialTheme.typography.h4, modifier = Modifier.padding(top = 5.dp, start = 10.dp))
                    Switch(
                        checked = vm.material.value,
                        onCheckedChange = {
                            vm.material.value = it
                        }
                    )
                    Text("Алюминий", style = MaterialTheme.typography.h4, modifier = Modifier.padding(top = 5.dp))
                }
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth().height(60.dp).padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 5.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(0.6f)
            ) {
                Text(text = "Марка стали", style = MaterialTheme.typography.h5)
            }
//            Column(
//                modifier = Modifier.fillMaxWidth(0.8f)
//            ) {
//                ComboBox(
//                    vm.selectedSteel,
//                    modifier = Modifier.fillMaxWidth(),
//                    items = vm.typesSteel
//                )
//            }
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .border(2.dp, Color.Black)
        ) {

            ScrollableLazyColumn {
                items(vm.paramsList.size) {
                    ParamOIRow(vm.paramsList[it])
                }
            }
        }
//        viewModel.paramsList.forEach { it->
//            ParamOIRow(it)
//        }
    }
}