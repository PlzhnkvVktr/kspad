package ru.avem.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun TabNavigationRow(
    onMainScreen: (() -> Unit)?,
    onProtocolScreen: (() -> Unit)?,
    onAdminScreen: (() -> Unit)?,
    ) {
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
    ) {
        OutlinedButton(
            onClick = {
                if (onMainScreen != null) {
                    onMainScreen()
                }
            },
            modifier = Modifier
                .border(2.dp, if(onMainScreen == null) Color.Black else Color.White)
                .width(260.dp),
            enabled = onMainScreen != null
        ) {
            Text(
                text = "Главный",
                style = MaterialTheme.typography.h5,
                color = Color.Black,
                fontWeight = FontWeight.W700
            )
        }
        OutlinedButton(
            onClick = {
                if (onProtocolScreen != null) {
                    onProtocolScreen()
                }
            },
            modifier = Modifier
                .border(2.dp, if(onProtocolScreen == null) Color.Black else Color.White)
                .width(260.dp),
            enabled = onProtocolScreen != null
        ) {
            Text(
                text = "Протоколы",
                style = MaterialTheme.typography.h5,
                color = Color.Black,
                fontWeight = FontWeight.W700
            )
        }
        OutlinedButton(
            onClick = {
                if (onAdminScreen != null) {
                    onAdminScreen()
                }
            },
            modifier = Modifier
                .border(2.dp, if(onAdminScreen == null) Color.Black else Color.White)
                .width(260.dp),
            enabled = onAdminScreen != null
        ) {
            Text(
                text = "Учетные записи",
                style = MaterialTheme.typography.h5,
                color = Color.Black,
                fontWeight = FontWeight.W700
            )
        }
//        Button(
//            onClick = {
//                if (onMainScreen != null) {
//                    onMainScreen()
//                }
//            }
//        ) {
//            Text("Главный")
//        }
//        Button(
//            onClick = {
//                if (onProtocolScreen != null) {
//                    onProtocolScreen()
//                }
//            }
//        ) {
//            Text("Протоколы")
//        }
//        Button(
//            onClick = {
//                if (onAdminScreen != null) {
//                    onAdminScreen()
//                }
//            }
//        ) {
//            Text("Учетные записи")
//        }
    }
}