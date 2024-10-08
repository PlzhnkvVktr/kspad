package ru.avem.modules.tests

import kotlinx.coroutines.delay
import ru.avem.common.af
import ru.avem.data.enums.DeviceID
import ru.avem.data.enums.LogType
import ru.avem.modules.devices.avem.avem9.AVEM9Model

class TestMGR : Test() {
    override suspend fun start() {
        initDevices()
        if (isRun) pr102.toggleSwitchMgr()
        if (isRun) initAVEM9()
        if (isRun) {
//               vm.listTestItems[it].specifiedMgrU.value = testObject.u_mgr
            var timer = 300
            while (vm.isDialog && timer > 0) {
                delay(100)
                timer -= 1
            }
            if (vm.isDialog) isRun = false
        }
        if (isRun) {
            CM.startPoll(DeviceID.PR66.name, pr66.model.STATUS) { value ->
                statusMGR = value.toInt()
            }
            CM.startPoll(DeviceID.PR66.name, pr66.model.VOLTAGE) { value ->
                vm.testItem.mgrU.value = value.af()
            }
            CM.startPoll(DeviceID.PR66.name, pr66.model.R15_MEAS) { value ->
                vm.testItem.r15.value = value.af()
            }
            CM.startPoll(DeviceID.PR66.name, pr66.model.R60_MEAS) { value ->
                vm.testItem.r60.value = value.af()
            }
            CM.startPoll(DeviceID.PR66.name, pr66.model.ABSORPTION) { value ->
                vm.testItem.kABS.value = value.af()
            }
        }
        if (isRun) {
            pr102.mgr(true)
            delay(1000)
        }
        if (isRun) {
            pr66.startMeasurement(
                AVEM9Model.MeasurementMode.AbsRatio, when {
                    vm.testItem.selectedTI!!.u_mgr.toInt() == 2500 -> {
                        AVEM9Model.SpecifiedVoltage.V2500
                    }

                    vm.testItem.selectedTI!!.u_mgr.toInt() == 1000 -> {
                        AVEM9Model.SpecifiedVoltage.V1000
                    }

                    vm.testItem.selectedTI!!.u_mgr.toInt() == 500 -> {
                        AVEM9Model.SpecifiedVoltage.V500
                    }

                    else -> {
                        AVEM9Model.SpecifiedVoltage.V500
                    }
                }
            )
            appendToLog("Идет измерение...", LogType.ERROR)
        }
        if (isRun) delay(3000)
        var time = 65
        while (isRun && statusMGR != 4 && time-- > 0) delay(1000)
        appendToLog("Измерение завершено успешно", LogType.MESSAGE)
        vm.testItem.res_mgr.value = "Измерение завершено"
    }
}