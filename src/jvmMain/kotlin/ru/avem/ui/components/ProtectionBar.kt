package ru.avem.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ru.avem.modules.tests.Test

@Composable
fun ProtectionBar(deviceManager: Test) {

    Row (
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(5.dp),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        ProtectionBarItem("Превышение тока ОИ", deviceManager.ikzTI)
        ProtectionBarItem("Токовая ВИУ", deviceManager.ikzVIU)
        ProtectionBarItem("Двери зоны", deviceManager.doorZone)
        ProtectionBarItem("Двери ШСО", deviceManager.doorSCO)
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
        Box(
            modifier =
            if (param) {
                Modifier
                    .background(Color.Green, CircleShape)
            } else {
                Modifier
                    .background(Color.Red, CircleShape)
            }.size(30.dp)
             .border(
                width = 2.dp,
                color = Color.Black,
                shape = CircleShape
            )
        )
        Text(
            text = title,
            style = MaterialTheme.typography.h4
        )
    }

}