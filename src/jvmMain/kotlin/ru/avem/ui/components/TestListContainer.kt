package ru.avem.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.koin.compose.koinInject
import ru.avem.data.enums.Tests
import ru.avem.ui.viewmodels.MainScreenViewModel

@Composable
fun TestListContainer() {

    val vm = koinInject<MainScreenViewModel>()

    Column(
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .padding(10.dp)
            .border(3.dp, Color.DarkGray, RoundedCornerShape(10.dp))
            .clip(RoundedCornerShape(10.dp)),
    ) {
        Tests.values().forEach { item ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .padding(5.dp)
                    .clickable {
                        item.check.value = !item.check.value
                        val found = vm.testList.find { it.testName == item.testName }
                        vm.checkboxClick(item, found)
                },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = item.check.value,
                    onCheckedChange = { it ->
                        item.check.value = it
                        val found = vm.testList.find { it.testName == item.testName }
                        vm.checkboxClick(item, found)
                    }
                )
                    Text(item.testName, fontSize = 26.sp)
            }
        }
    }
}
