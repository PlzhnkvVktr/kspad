package ru.avem.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.koin.compose.koinInject
import org.koin.core.component.inject
import org.koin.java.KoinJavaComponent.inject
import ru.avem.data.db.DBManager
import ru.avem.modules.tests.CustomController
import ru.avem.ui.viewmodels.MainScreenViewModel

@Composable
fun ProtectionBar () {
    val controller = koinInject<CustomController>()

    Row (
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(5.dp),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        ProtectionBarItem("Превышение тока ОИ", controller.ikzTI)
        ProtectionBarItem("Токовая ВИУ", controller.ikzVIU)
        ProtectionBarItem("Двери зоны", controller.doorZone)
        ProtectionBarItem("Двери ШСО", controller.doorSCO)
    }
}
@Composable
fun ProtectionBarItem (
    title: String,
    param: Boolean
) {
    Row (
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = title,
            style = MaterialTheme.typography.h4
        )
    }

}