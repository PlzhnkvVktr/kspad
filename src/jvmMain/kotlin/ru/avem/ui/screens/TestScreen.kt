package ru.avem.ui.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.koin.compose.koinInject
import ru.avem.ui.components.*
import ru.avem.ui.viewmodels.TestScreenViewModel

@Composable
fun TestScreen(
    modifier: Modifier,
    onMainScreen: () -> Unit
) {

    val vm = koinInject<TestScreenViewModel>()

    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        TestWindowTitle(vm.currentTest.testName)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(.63f)
        ) {
            Column(
                modifier = Modifier
                    .weight(11f)
                    .padding(10.dp)
            ) {
                SpecifiedParamsList(vm.listTestItems[vm.order])
            }
            Column(
                modifier = Modifier
                    .weight(89f)
                    .padding(10.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .border(1.dp, Color.Black, RoundedCornerShape(10.dp))
                        .clip(RoundedCornerShape(10.dp))
                ) {
                    vm.currentTest.view.invoke()
                }
            }
        }
        TestNavigation(vm, onMainScreen)
        LogsList(vm.currentTest.controller)
        ProtectionBar()

        if (vm.isDialog) {
            TestDialog(vm.currentTest.warning,
                true,
                { vm.isDialog = false },
                {
                    vm.currentTest.controller.isRun = false
                    vm.isDialog = false
                }
            )
        }
        if (vm.currentTest.controller.isStartButton.value) {
            TestDialog()
        }
    }
}