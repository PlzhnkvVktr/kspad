package ru.avem.modules.tests

import kotlinx.coroutines.delay
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.avem.ui.viewmodels.TestScreenViewModel

open class Test(): CustomController(), KoinComponent {
    val vm by inject<TestScreenViewModel>()
    open suspend fun start() {
        println("Hello from abstract test")
    }

    suspend fun initDevices() {
        logMessages.clear()
        vm.waiting = false
        isRun = true
        vm.isDialog = true
        while (vm.isDialog) {
            delay(200)
        }
        if (isRun) initPR()
        if (isRun) initButtonPost()
        vm.isDialog = false
    }
    fun stop() {
        stopTestRunning()
    }
}