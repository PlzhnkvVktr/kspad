package ru.avem.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun ActionButton (text: String, pic: ImageVector, disabled: Boolean = true, onClick: () -> Unit) {

    Button(
        onClick = { onClick() },
        modifier = Modifier.width(200.dp).height(150.dp).border(2.dp, Color.DarkGray),
        enabled = disabled
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                pic,
                contentDescription = "Общая кнопка",
                modifier = Modifier.size(100.dp)
            )
            Text(
                text = text,
                modifier = Modifier,
                style = MaterialTheme.typography.h5,
                textAlign = TextAlign.Center
            )
        }
    }
}