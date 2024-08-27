package ru.avem.ui.viewmodels

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.delay
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.avem.data.db.DBManager
import ru.avem.data.db.TestItem
import ru.avem.data.enums.Tests
import ru.avem.data.models.TestObject

class TestScreenViewModel() : ViewModel(), KoinComponent {

    val mainVM by inject<MainScreenViewModel>()

    var isButtonDisabled by mutableStateOf(true)
    var isDialog by mutableStateOf(false)
    var waiting by mutableStateOf(true)
    var order by mutableStateOf(0)

    var currentTest: Tests by mutableStateOf(mainVM.testsListIterator.next())

    fun next() {
        if (!mainVM.testsListIterator.hasNext() && mainVM.testsListIterator.next() != currentTest) return
        currentTest = mainVM.testsListIterator.next()
    }
    fun prev() {
        if (!mainVM.testsListIterator.hasPrevious() && mainVM.testsListIterator.previous() != currentTest) return
        currentTest = mainVM.testsListIterator.previous()
    }
    suspend fun disableButton () {
        isButtonDisabled = false
        delay(1000)
        isButtonDisabled = true
    }

//    var testItem = TestObject()

    val listTestItems = mainVM.listTestItems

    var u_a: MutableState<String> = mutableStateOf("")
    var u_uv: MutableState<String> = mutableStateOf("")
    var u_vw: MutableState<String> = mutableStateOf("")
    var u_wu: MutableState<String> = mutableStateOf("")
    var i_u: MutableState<String> = mutableStateOf("")
    var i_v: MutableState<String> = mutableStateOf("")
    var i_w: MutableState<String> = mutableStateOf("")
    var cos: MutableState<String> = mutableStateOf("")
    var pA: MutableState<String> = mutableStateOf("")

}
