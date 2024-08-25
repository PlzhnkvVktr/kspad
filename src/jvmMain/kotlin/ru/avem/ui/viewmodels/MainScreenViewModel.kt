package ru.avem.ui.viewmodels

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.koin.core.component.inject
import org.koin.core.component.KoinComponent
import ru.avem.data.db.DBManager
import ru.avem.data.enums.Tests
import ru.avem.modules.tests.*
import ru.avem.ui.navigation.NavRoutes
import java.util.*

class MainScreenViewModel() : ViewModel(), KoinComponent {

    val db by inject<DBManager>()

    var factoryNumber by mutableStateOf("")
    var typesTI = db.getAllTI().ifEmpty { listOf("") }
    var selectedTI = mutableStateOf(typesTI.first())

    var testList = mutableListOf<Tests>()
    var testsListIterator = LinkedList(testList).listIterator()

    var startTestButton: MutableState<Boolean> = mutableStateOf(false)
    var allCheckedButton: MutableState<Boolean> = mutableStateOf(false)

//    val testMap: MutableMap<Tests, MutableState<Boolean>> = mutableMapOf(
//        Tests.MGR to mutableStateOf(false),
//        Tests.VIU to mutableStateOf(false),
//        Tests.IKAS to mutableStateOf(false),
//        Tests.HH to mutableStateOf(false),
//        Tests.MV to mutableStateOf(false)
//    )

    fun selectAll () {

            if (!allCheckedButton.value) {
                allCheckedButton.value = true
                testList.clear()
                Tests.values().forEach { item ->
                    item.check.value = true
                    checkTest(item)
                }
            } else {
                allCheckedButton.value = false
                Tests.values().forEach { item -> item.check.value = false }
                testList.clear()
            }
//            testsListIterator = testList.iterator()
//            startTestButton.value = testsLine.value.hasNext()

    }

    fun startTests(onSubmit: () -> Unit) {
//        viewModelScope.launch {
//            CustomController.testObjectName.value = selectedTI.value
//            CustomController.testObject = DBManager.getTI(CustomController.testObjectName.value)
//            navigator.push(testsLine.value.next())
            testsListIterator = LinkedList(Tests.values().asList().filter { it.check.value }).listIterator()
            Tests.values().forEach { item -> item.check.value = false }

            startTestButton.value = false
            allCheckedButton.value = false
        println("---> " + testList)
            onSubmit()
//        }
    }
    private fun checkTest (item: Tests): Boolean {
        return when (item) {
            Tests.MGR -> testList.add(item)
            Tests.VIU -> testList.add(item)
            Tests.IKAS -> testList.add(item)
            Tests.HH -> testList.add(item)
            Tests.MV -> testList.add(item)
        }
    }
    private fun checkTest1 (item: Tests) {
        item.check.value = !item.check.value
    }

    fun clearTestList () {
        testsListIterator = LinkedList(listOf<Tests>()).listIterator()
    }
    fun checkboxClick (
        item: Tests,
        found: Tests?
    ) {
        if (found != null) {
            testList.remove(found)
        } else {
            checkTest(item)
        }

        testsListIterator = LinkedList(Tests.values().asList().filter { it.check.value }).listIterator()

    }


}
