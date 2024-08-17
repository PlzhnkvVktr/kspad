package ru.avem.ui.viewmodels

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import org.koin.core.Koin
import org.koin.core.component.KoinComponent
import ru.avem.enums.Tests
import java.util.*
import javax.naming.Context

class TestScreenViewModel() : ViewModel(), KoinComponent {
    val testsListIterator = LinkedList(Tests.values().asList()).listIterator()
    var currentTest by mutableStateOf(testsListIterator.next())
    val a = mutableStateOf("ssdfsdfsdffds")

    fun next() {
        if (!testsListIterator.hasNext() && testsListIterator.next() != currentTest) return
        currentTest = testsListIterator.next()
    }

    fun prev() {
        if (!testsListIterator.hasPrevious() && testsListIterator.previous() != currentTest) return
        currentTest = testsListIterator.previous()
    }

}
