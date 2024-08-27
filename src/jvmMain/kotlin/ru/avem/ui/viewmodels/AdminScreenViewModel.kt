package ru.avem.ui.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.avem.data.db.DBManager
import ru.avem.data.db.User

class AdminScreenViewModel: ViewModel(), KoinComponent {

    val db by inject<DBManager>()

    private val scope = CoroutineScope(Dispatchers.Default)
    var userList = mutableStateListOf<User>()
    var textFind = mutableStateOf("")
    val openAlertDialog = mutableStateOf(false)
    var switchOn = mutableStateOf(false)
    var isAdminMode = mutableStateOf(false)
    var currentUser: MutableState<User?> = mutableStateOf(null)

    var login = mutableStateOf("")
    var loginError = mutableStateOf("")

    var password = mutableStateOf("")

    fun clearFields() {
        login.value = ""
        loginError.value = ""
        password.value = ""
        switchOn.value = false
    }
    fun getUsers() {
        scope.launch {
            userList.clear()
            userList.addAll(db.getAllUsers())
        }
    }
    fun performSearch(predicate: String) {
        scope.launch {
            val values = db.findUser(predicate)
            userList.clear()
            userList.addAll(values)
        }
    }

    fun addUser() {
        scope.launch {
            if (db.findUserLogin(login.value)) {
                loginError.value = "Пользователь с таким логином уже существует"
                login.value = ""
            } else {
                db.addUser(login = login.value, password = password.value, isAdmin = switchOn.value)
                getUsers()
                login.value = ""
                password.value = ""
                switchOn.value = false
                openAlertDialog.value = false
            }
        }
    }

    fun deleteUser(user: User) {
        scope.launch {
            db.deleteUser(user)
            userList.remove(user)
        }
    }
    fun updateUser(user: User) {
        scope.launch {
            db.updateUser(user, login.value, password.value, switchOn.value)
            getUsers()
            login.value = ""
            password.value = ""
            switchOn.value = false
        }
    }
}