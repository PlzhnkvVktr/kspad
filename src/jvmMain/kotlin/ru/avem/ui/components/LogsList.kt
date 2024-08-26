package ru.avem.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.compose.koinInject
import ru.avem.data.enums.LogType
import ru.avem.data.models.LogMessage
import ru.avem.modules.tests.CustomController
import ru.avem.ui.viewmodels.MainScreenViewModel


@Composable
fun LogsList (testController: CustomController) {
    val logScrollState = rememberLazyListState()
    val scope = CoroutineScope(Dispatchers.Main)

    Row (
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        ScrollableLazyColumn(
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .height(200.dp)
                .border(2.dp, Color.DarkGray, RoundedCornerShape(10.dp))
                .clip(RoundedCornerShape(10.dp)),
            logScrollState
        ) {
            items(testController.logMessages.size) { index ->

                Text(
                    text = testController.logMessages[index].text,
                    modifier = Modifier.padding(3.dp).fillMaxWidth(),
                    style = MaterialTheme.typography.h5,
                    color = when (testController.logMessages[index].type) {
                        LogType.ERROR -> MaterialTheme.colors.error
                        LogType.MESSAGE -> MaterialTheme.colors.primaryVariant
                        LogType.DEBUG -> MaterialTheme.colors.secondary
                    }
                )
            }
            scope.launch {
                logScrollState.scrollToItem(testController.logMessages.size)
            }
        }
    }
}