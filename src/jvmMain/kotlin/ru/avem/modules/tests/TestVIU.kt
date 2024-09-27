package ru.avem.modules.tests

import kotlinx.coroutines.delay
import ru.avem.common.af
import ru.avem.data.enums.DeviceID
import ru.avem.data.enums.LogType
import ru.avem.modules.devices.avem.avem4.AVEM4Model
import ru.avem.modules.devices.pm130.PM130Model
import kotlin.math.abs

class TestVIU : Test() {
    override suspend fun start() {
        initDevices()
        ktrAmperage = 1.0 / 5.0
        var setI = vm.testItem.i_viu.value.toDouble()
        var i_viu = 0.0
        setVoltage = vm.testItem.u_viu.value.toDouble()
        val setTime = vm.testItem.t_viu.value.toDouble()
        var v_viu_res = ""
        var i_viu_res = ""

        if (isRun) {
            var timer = 300
            while (vm.isDialog && timer > 0) {
                delay(100)
                timer -= 1
            }
            if (isRun) parma41.checkResponsibility()
            if (parma41.isResponding) {
                CM.startPoll(DeviceID.PAV41.name, PM130Model.I_A_REGISTER) { it1 ->
                    i_viu = it1.toDouble() * 1 / 5 * 1000.0
                    vm.testItem.i_viu.value = String.format("%.0f", i_viu)
                    if (i_viu > setI && isRun) {
                        appendToLog("Превышение заданного тока", LogType.ERROR)
                        isRun = false
                    }
                }
            } else {
                appendToLog("Парма не отвечает", LogType.ERROR)
                isRun = false
            }
        }

        if (isRun) {
            appendToLog("Инициализация АВЭМ-3...", LogType.MESSAGE)
            pv24.checkResponsibility()
            delay(1000)
            if (pv24.isResponding) {
                CM.startPoll(DeviceID.PV24.name, AVEM4Model.RMS_VOLTAGE) { value ->
                    voltage = value.toDouble()
                    vm.testItem.u_viu.value = voltage.af()
                }
            } else {
                appendToLog("АВЭМ-3 не отвечает", LogType.ERROR)
                isRun = false
            }
        }

        if (vm.isDialog) isRun = false
        if (isRun) initARN()
        if (isRun) ATR.resetLATR()
        if (isRun) pr102.arn(true)
        if (isRun) checkLatrZero()
        if (isRun) pr102.viu(true)
        if (isRun) {
            val devTimer = System.currentTimeMillis()
            appendToLog("Подъём напряжения", LogType.MESSAGE)
            while ((voltage < setVoltage - 200) && isRun) {
                ATR.startUpLATRPulse(250f, 50f)
                if (latrEndsState == 1) {
                    appendToLog("Достигнуто максимальное напряжение АРН", LogType.ERROR)
                    isRun = false
                    break
                }
                if (voltOnATR > 10) {
                    if (System.currentTimeMillis() - devTimer > 4000 && (voltOnATR * 4000.0 / 220.0) !in voltage * 0.6..voltage * 1.6) {
                        appendToLog(
                            "Несоответствие напряжение расчетного и измеренного напряжения ВИУ",
                            LogType.ERROR
                        )
                        isRun = false
                    }
                }
                if (System.currentTimeMillis() - devTimer > 15000 && latrEndsState == 2) {
                    appendToLog("Застревание АРН", LogType.ERROR)
                    isRun = false
                }
                if (System.currentTimeMillis() - devTimer > 10000 && voltage < 15) {
                    appendToLog("не появилось напряжение", LogType.ERROR)
                    isRun = false
                }
                if (!isRun) {
                    vm.testItem.res_viu.value = "Испытание прервано"
                }
                delay(100)
            }
            while ((voltage < setVoltage - 15) && isRun) {
                ATR.startUpLATRPulse(250f, 20f)
                delay(100)
                if (latrEndsState == 1) {
                    appendToLog("Максимальное напряжение АРН", LogType.ERROR)
                    isRun = false
                    break
                }
                if (voltOnATR > 10) {
                    if ((voltOnATR * 4000.0 / 220.0) !in voltage * 0.7..voltage * 1.6) {
                        appendToLog(
                            "Несоответствие напряжение расчетного и измеренного напряжения ВИУ",
                            LogType.ERROR
                        )
                        isRun = false
                    }
                }
            }
            ATR.stopLATR()
            if (!isRun) {
                vm.testItem.res_viu.value = "Испытание прервано"
            }
        }

        if (isRun) {
            delay(1000)
            appendToLog("Выдержка напряжения", LogType.MESSAGE)
            var timer = setTime
            while (isRun && timer > 0) {
                delay(100)
                timer -= 0.1
                vm.testItem.t_viu.value = abs(timer).af()
            }
            if (isRun) {
                vm.testItem.res_viu.value = "Успешно"
            } else {
                vm.testItem.res_viu.value = "Прервано"
            }
            v_viu_res= voltage.af()
            i_viu_res = String.format("%.0f", i_viu)

            vm.testItem.t_viu.value = setTime.toString()
            vm.testItem.u_viu.value = voltage.af()
//                addReportVIU()


        }
        ATR.resetLATR()
        if (isRun) {
//               sleepWhileRun(1.0)
            delay(1000)
        } else {
            pr102.arn(false)
        }
        while (voltage > 100) {
            delay(100)
        }
        vm.testItem.u_viu.value = v_viu_res
        vm.testItem.i_viu.value = i_viu_res
    }
}