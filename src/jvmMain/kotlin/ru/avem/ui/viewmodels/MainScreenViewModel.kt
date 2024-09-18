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

    var selectedTI1 = mutableStateOf(typesTI.first())
    var selectedTI2 = mutableStateOf(typesTI.first())
    var selectedTI3 = mutableStateOf(typesTI.first())


    var factoryNumber1 = mutableStateOf("")
    var factoryNumber2 = mutableStateOf("")
    var factoryNumber3 = mutableStateOf("")

    var card1 = mutableStateOf(true)
    var card2 = mutableStateOf(false)
    var card3 = mutableStateOf(false)


    var testList = mutableListOf<Tests>()
    var testsListIterator = LinkedList(testList).listIterator()

    var listTestItems = mutableListOf<TestObject>()

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

        viewModelScope.launch {
            if (!allCheckedButton.value) {
                allCheckedButton.value = true
//                testList.clear()
                Tests.values().forEach { item ->
                    item.check.value = true
                    checkTest(item)
                }
            } else {
                allCheckedButton.value = false
                Tests.values().forEach {
                        item -> item.check.value = false
                }
                testList.clear()
            }
        }
        testsListIterator = LinkedList(testList).listIterator()
    }

    fun startTests(onSubmit: () -> Unit) {
//        viewModelScope.launch {
//            CustomController.testObjectName.value = selectedTI.value
//            CustomController.testObject = DBManager.getTI(CustomController.testObjectName.value)
//            navigator.push(testsLine.value.next())
    viewModelScope.launch {
        if (card1.value && factoryNumber1.value.isNotEmpty()) {
            listTestItems.add(TestObject(name = factoryNumber1, selectedTI = db.getTI(selectedTI1.value)))
        }
        if (card2.value && factoryNumber2.value.isNotEmpty()) {
            listTestItems.add(TestObject(name = factoryNumber2, selectedTI = db.getTI(selectedTI2.value)))
        }
        if (card3.value && factoryNumber3.value.isNotEmpty()) {
            listTestItems.add(TestObject(name = factoryNumber3, selectedTI = db.getTI(selectedTI3.value)))
        }
        testsListIterator = LinkedList(Tests.values().asList().filter { it.check.value }).listIterator()
        Tests.values().forEach { item -> item.check.value = false }

        startTestButton.value = false
        allCheckedButton.value = false
        onSubmit()
    }

//        }
    }
    private fun checkTest (item: Tests) {
        when (item) {
            Tests.MGR -> testList.add(item)
            Tests.VIU -> testList.add(item)
            Tests.IKAS -> testList.add(item)
            Tests.HH -> testList.add(item)
            Tests.MV -> testList.add(item)
        }
        println(testList)
    }
    private fun checkTest1 (item: Tests) {
        item.check.value = !item.check.value
    }

    fun clearTestList() {
        testsListIterator = LinkedList(listOf<Tests>()).listIterator()
    }
    fun checkboxClick (
        item: Tests,
        found: Tests?
    ) {
        if (found != null) {
//            item.check.value = false
            testList.remove(found)
        } else {
//            item.check.value = true
            checkTest(item)
            testsListIterator = LinkedList(Tests.values().asList().filter { it.check.value }).listIterator()
        }



    }


}
