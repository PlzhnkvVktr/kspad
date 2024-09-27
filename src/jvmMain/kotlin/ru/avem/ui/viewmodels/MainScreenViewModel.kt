package ru.avem.ui.viewmodels

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.koin.core.component.inject
import org.koin.core.component.KoinComponent
import ru.avem.data.db.DBManager
import ru.avem.data.enums.Tests
import ru.avem.data.models.TestObject
import ru.avem.modules.tests.*
import ru.avem.ui.navigation.NavRoutes
import java.util.*

class MainScreenViewModel() : ViewModel(), KoinComponent {

    val db by inject<DBManager>()
    var typesTI = db.getAllTI().ifEmpty { listOf("") }

    var selectedTI = mutableStateOf(typesTI.first())
//    var selectedTI2 = mutableStateOf(typesTI.first())
//    var selectedTI3 = mutableStateOf(typesTI.first())

    var factoryNumber by mutableStateOf("")

//    var testList = mutableListOf<Tests>()
    var testsListIterator = LinkedList(listOf<Tests>()).listIterator()

//    var listTestItems = mutableListOf<TestObject>()

    var startTestButton: MutableState<Boolean> = mutableStateOf(false)
    var allCheckedButton: MutableState<Boolean> = mutableStateOf(false)


    var testItem = TestObject()

    fun selectAll () {

        viewModelScope.launch {
            if (!allCheckedButton.value) {
                allCheckedButton.value = true
//                testList.clear()
                Tests.values().forEach { item ->
                    item.check.value = true
//                    checkTest(item)
                }
            } else {
                allCheckedButton.value = false
                Tests.values().forEach {
                    item -> item.check.value = false
                }
//                testList.clear()
            }
        }

//        testsListIterator = LinkedList(Tests.values().asList().filter { it.check.value }).listIterator()
        testsListIterator = LinkedList(Tests.values().asList().filter { it.check.value }).listIterator()
    }

    fun startTests(onSubmit: () -> Unit) {
//        viewModelScope.launch {
//            CustomController.testObjectName.value = selectedTI.value
//            CustomController.testObject = DBManager.getTI(CustomController.testObjectName.value)
//            navigator.push(testsLine.value.next())
        viewModelScope.launch {
            testItem.selectedTI = db.getTI(selectedTI.value)
            testsListIterator = LinkedList(Tests.values().asList().filter { it.check.value }).listIterator()
//            Tests.values().forEach { item -> item.check.value = false }

            startTestButton.value = false
            allCheckedButton.value = false
            onSubmit()
        }

    }

    fun checkboxClick (
        item: Tests,
        found: Tests?
    ) {
        if (found != null) {
            item.check.value = false
//            testList.remove(found)
        } else {
            item.check.value = true
//            checkTest(item)
        }

        testsListIterator = LinkedList(Tests.values().asList().filter { it.check.value }).listIterator()
    }

    fun clearTestList() {
        testsListIterator = LinkedList(listOf<Tests>()).listIterator()
    }

}
