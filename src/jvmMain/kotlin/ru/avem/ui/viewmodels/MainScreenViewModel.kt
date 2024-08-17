package ru.avem.ui.viewmodels

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import org.koin.core.component.inject
import org.koin.core.component.KoinComponent
import ru.avem.data.db.DBManager

class MainScreenViewModel() : ViewModel(), KoinComponent {

    val db by inject<DBManager>()

    var factoryNumber by mutableStateOf("")
    var typesTI = db.getAllTI().ifEmpty { listOf("") }
    var selectedTI = mutableStateOf(typesTI.first())

}
