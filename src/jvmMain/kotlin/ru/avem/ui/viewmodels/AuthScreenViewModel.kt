package ru.avem.ui.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.compose.koinInject
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.avem.data.repos.AppConfig
import ru.avem.data.db.DBManager
import ru.avem.data.db.User

class AuthScreenViewModel : ViewModel(), KoinComponent {
    val db by inject<DBManager>()
    var users: List<User> = db.getAllUsers().ifEmpty { listOf() }
    var selectedUser = mutableStateOf(users.first())
    var password: MutableState<String> = mutableStateOf("")
    var isError: MutableState<Boolean> = mutableStateOf(false)
    val scope = CoroutineScope(Dispatchers.Default)

    var passwordVisible = mutableStateOf(false)

    fun signIn (
        onSubmit: () -> Unit
    ) {
        println(password.value)
        println(selectedUser.value.password)
        scope.launch {
            if (password.value == selectedUser.value.password) {
                AppConfig.update(selectedUser.value)
                onSubmit()
            } else {
                isError.value = true
                password.value = ""
            }
        }
    }
}