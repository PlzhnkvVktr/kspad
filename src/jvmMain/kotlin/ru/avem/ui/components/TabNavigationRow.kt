package ru.avem.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.system.exitProcess

@Composable
fun TabNavigationRow(
    onMainScreen: (() -> Unit)?,
    onProtocolScreen: (() -> Unit)?,
    onTestObjectScreen: (() -> Unit)?,
    onAdminScreen: (() -> Unit)?,
    ) {
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
//            .border(2.dp, Color.DarkGray),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(.8f)
                .fillMaxHeight()
        ) {
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
            ){
                OutlinedButton(
                    onClick = {
                        if (onMainScreen != null) {
                            onMainScreen()
                        }
                    },
                    modifier = Modifier
//                        .border(1.dp, if(onMainScreen == null)
//                            Color.Black else Color.White
//                        )
                        .width(260.dp),
                    enabled = onMainScreen != null
                ) {
                    Icon(
                        imageVector = Icons.Default.ViewList,
                        modifier = Modifier.size(50.dp),
                        contentDescription = ""
                    )
                    Spacer(Modifier.height(10.dp))
                    Text(
                        modifier = Modifier.padding(start = 10.dp),
                        text = "Испытания",
                        style = TextStyle(fontWeight = FontWeight.Bold),
                        textAlign = TextAlign.Center,
                        fontSize = 24.sp
                    )
                }
                OutlinedButton(
                    onClick = {
                        if (onTestObjectScreen != null) {
                            onTestObjectScreen()
                        }
                    },
                    modifier = Modifier
//                        .border(1.dp, if(onMainScreen == null)
//                            Color.Black else Color.White
//                        )
                        .width(260.dp),
                    enabled = onTestObjectScreen != null
                ) {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        modifier = Modifier.size(50.dp),
                        contentDescription = ""
                    )
                    Spacer(Modifier.height(10.dp))
                    Text(
                        modifier = Modifier.padding(start = 10.dp),
                        text = "Объекты",
                        style = TextStyle(fontWeight = FontWeight.Bold),
                        textAlign = TextAlign.Center,
                        fontSize = 24.sp
                    )
                }
                OutlinedButton(
                    onClick = {
                        if (onProtocolScreen != null) {
                            onProtocolScreen()
                        }
                    },
                    modifier = Modifier
//                        .border(1.dp, if(onProtocolScreen == null) Color.Black else Color.White)
                        .width(260.dp),
                    enabled = onProtocolScreen != null
                ) {
                    Icon(
                        imageVector = Icons.Default.ListAlt,
                        modifier = Modifier.size(50.dp),
                        contentDescription = ""
                    )
                    Spacer(Modifier.height(10.dp))
                    Text(
                        modifier = Modifier.padding(start = 10.dp),
                        text = "Протоколы",
                        style = TextStyle(fontWeight = FontWeight.Bold),
                        textAlign = TextAlign.Center,
                        fontSize = 24.sp
                    )
                }
                OutlinedButton(
                    onClick = {
                        if (onAdminScreen != null) {
                            onAdminScreen()
                        }
                    },
                    modifier = Modifier
//                        .border(1.dp, if(onAdminScreen == null) Color.Black else Color.White)
                        .width(320.dp),
                    enabled = onAdminScreen != null
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.VerifiedUser,
                            modifier = Modifier.size(50.dp),
                            contentDescription = ""
                        )
                        Spacer(Modifier.height(10.dp))
                        Text(
                            modifier = Modifier.padding(start = 10.dp),
                            text = "Учетные записи",
                            style = TextStyle(fontWeight = FontWeight.Bold),
                            textAlign = TextAlign.Center,
                            fontSize = 24.sp
                        )
                    }
                }

            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            horizontalAlignment = Alignment.End
        ) {
            OutlinedButton(
                onClick = {
                    exitProcess(0)
                },
                modifier = Modifier
                    .fillMaxHeight()
                    .border(1.dp, Color.Red)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.ExitToApp,
                        modifier = Modifier.size(50.dp),
                        contentDescription = "",
                        tint = Color.Red
                    )
                    Spacer(Modifier.height(10.dp))
                    Text(
                        modifier = Modifier.padding(start = 10.dp),
                        text = "Выйти",
                        style = TextStyle(fontWeight = FontWeight.Bold, color = Color.Red),
                        textAlign = TextAlign.Center,
                        fontSize = 24.sp
                    )
                }
            }
        }
    }
}