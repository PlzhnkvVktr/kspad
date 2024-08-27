package ru.avem.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.koin.compose.koinInject
import ru.avem.data.db.DBManager
import ru.avem.data.db.User

@Composable
fun UserListItem(
    user: User,
    onUpdateCallback: (User) -> Unit,
    onDeleteCallback: (User) -> Unit,
) {

    if (!user.isAdmin) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .border(BorderStroke(2.dp, Color.LightGray))
                .padding(5.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = user.login,
                modifier = Modifier.padding(top = 10.dp, start = 20.dp),
                style = MaterialTheme.typography.h4
            )
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Button(
                    onClick = {
                        onUpdateCallback(user)
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.Green,
                        contentColor = Color.White
                    )
                ) {
                    Text("Обновить", fontSize = 20.sp)
                }
                Button(
                    onClick = {
                        onDeleteCallback(user)
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.Red,
                        contentColor = Color.White
                    ),
                    enabled = DBManager.getAllUsers().size != 1
                ) {
                    Text("Удалить", fontSize = 20.sp)
                }
            }
        }
    }
}