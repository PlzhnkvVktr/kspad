package ru.avem.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Shapes
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.skiko.Cursor

@Composable
fun MyButton (
    text: String,
    onClick: () -> Unit,
    image: ImageVector,
    enabled: Boolean = false
) {
    Button(
        modifier = Modifier
            .wrapContentHeight()
            .height(90.dp)
            .width(280.dp)
            .pointerHoverIcon(PointerIcon(Cursor(12)), true)
            .border(1.dp, Color.LightGray, RoundedCornerShape(70.dp))
            .clip(RoundedCornerShape(70.dp)),
        enabled = enabled,
        onClick = {
            onClick()
        }
    ) {
        Row(
            modifier = Modifier
                .padding(5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = image,
                modifier = Modifier.size(60.dp),
                contentDescription = "",
            )
            Spacer(Modifier.height(10.dp))
            Text(
                modifier = Modifier.padding(start = 10.dp),
                text = text,
                style = TextStyle(fontWeight = FontWeight.Bold),
                textAlign = TextAlign.Center,
                fontSize = 24.sp
            )
        }
    }
}