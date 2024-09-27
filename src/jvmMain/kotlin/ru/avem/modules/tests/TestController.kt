package ru.avem.modules.tests

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.*
import org.koin.core.component.KoinComponent
import ru.avem.common.af
import ru.avem.common.toDoubleOrDefault
import ru.avem.data.enums.DeviceID
import ru.avem.data.enums.LogType
import ru.avem.data.repos.AppConfig
import ru.avem.modules.devices.avem.atr.ATRModel
import ru.avem.modules.devices.avem.avem4.AVEM4Model
import ru.avem.modules.devices.avem.avem7.AVEM7Model
import ru.avem.modules.devices.pm130.PM130Model
import ru.avem.ui.viewmodels.TestScreenViewModel
import kotlin.experimental.and
import kotlin.math.abs

open class TestController(): DeviceManager() {


    var isRun by mutableStateOf(false)
    val currentOperator = mutableStateOf(AppConfig.params.login)
    private fun initWatchDogDD2() {
        with(pr102) {
            initWithoutProtections()
            offAllKMs()
            CM.addWritingRegister(name, pr102.model.CMD, 1.toShort())
            CM.startPoll(name, pr102.model.STATE) { }
        }
    }
    suspend fun initButtonPost() {

        appendToLog("Нажмите Пуск", LogType.ERROR)
        isStartButton.value = true
        while (isRun && pr102.isResponding) {
            if (isStartPressed) {
                isStartButton.value = false
                break
            }
            if (isStopPressed) {
                isStartButton.value = false
                isRun = false
                appendToLog("Испытание остановлено", LogType.ERROR)
            }
            delay(100)
        }
        warning()
    }
    private suspend fun warning() {
        pr102.signal(true)
        pr102.light(true)
        delay(3000)
        pr102.signal(false)
        pr102.light(false)
    }

    suspend fun checkLatrZero() {
        var latrTimer = 300
        ATR.resetLATR()
        while (voltOnATR > 10 && isRun) {
            delay(100)
            latrTimer--
            if (latrTimer<0) {
                appendToLog("Напряжение АРН > 10 В", LogType.ERROR)
                isRun = false
            }
        }
    }

    suspend fun initPR() {
        pr102.checkResponsibility()
//        val scope = CoroutineScope(Dispatchers.Default)
        appendToLog("Инициализация БСУ...", LogType.MESSAGE)
        isStartPressed = false
        isStopPressed = false
        var stateLock = false
        var count = 0
        if (!pr102.isResponding) {
            appendToLog("ПР102 не отвечает", LogType.ERROR)
            isRun = false
        } else {
            pr102.offAllKMs()
            initWatchDogDD2()
            delay(1000)
            CM.startPoll(DeviceID.DD2_1.name, pr102.model.DI_01_16_RAW) { value ->
                isStartPressed = value.toShort() and 1 > 0  // 1  // TODO инвертировать обратно
                isStopPressed = value.toShort() and 2 > 1   // 2
                doorZone = value.toShort() and 4 < 1   // 3
//                             = value.toShort() and 8 > 0   // 4
                doorSCO = value.toShort() and 16 < 1  // 5
                ikzTI = value.toShort() and 32 < 1 || stateLock // 6
                ikzVIU = value.toShort() and 64 < 1  // 7
//                             = value.toShort() and 128 > 0 // 8
                izmState = value.toShort() and 1024 > 0
                indState = value.toShort() and 2048 > 0

            }
            delay(1000)
            withContext(Dispatchers.Default) {
                while (isRun) {
                    delay(100)
                    if (!pr102.isResponding) {
                        appendToLog("ПР102 не отвечает", LogType.ERROR)
                        isRun = false
                    }
                    if (isStopPressed) {
                        appendToLog("Нажата кнопка <СТОП>", LogType.ERROR)
                        isRun = false
                    }
                    if (doorZone) {
                        appendToLog("Открыты двери зоны", LogType.ERROR)
                        isRun = false
                    }
                    if (doorSCO) {
                        appendToLog("Открыты двери ШСО", LogType.ERROR)
                        isRun = false
                    }
                    if (ikzTI) {
                        count++
                        if (count >= 8) {
                            appendToLog("Превышение тока ОИ", LogType.ERROR)
                            stateLock = true
                            isRun = false
                        }
                    } else {
                        count = 0
                    }
                    if (ikzVIU) {
                        appendToLog("Превышение тока ОИ", LogType.ERROR)
                        isRun = false
                    }
                    if (!isRun) {
                        appendToLog("Измерение завершено", LogType.ERROR)
                    }
                }
                stopTestRunning()
            }
        }
    }
    fun stopTestRunning() {
        isRun = false
        CM.clearPollingRegisters()
        isStartButton.value = false
        pr102.offAllKMs()
        CM.clearWritingRegisters()
        voltOnATR = 1.0
        ktrVoltage = 1.0
        voltAverage = 0.0
        ktrAmperage = 1.0
    }

    fun initAVEM4 (vm: TestScreenViewModel) {
        with(pv61) {
            checkResponsibility()
            if (!isResponding) {
                appendToLog("АВЭМ-4 не отвечает", LogType.ERROR)
                isRun = false
//                    appendMessageToLog("PV21 не отвечает", LogType.ERROR)
            } else {
                ru.avem.modules.devices.CM.startPoll(DeviceID.PV61.name, AVEM4Model.RMS_VOLTAGE) { value ->
                    vm.testItem.ikas_v.value = (value.toDouble()).af()
                }
            }
        }
    }

    fun initAVEM7 (vm: TestScreenViewModel) {
        with(pa62) {
            checkResponsibility()
            if (!isResponding) {
                appendToLog("АВЕМ-7 не отвечает", LogType.ERROR)
                isRun = false
            } else {
                ru.avem.modules.devices.CM.startPoll(DeviceID.PA62.name, AVEM7Model.AMPERAGE) { value ->
                    vm.testItem.ikas_i.value = (value.toDouble()).af()
                    if (!pa62.isResponding) {
                        appendToLog("PV21 не отвечает", LogType.ERROR)
                    }
                }
            }
        }
    }

    suspend fun initPM130(vm: TestScreenViewModel) {
        appendToLog("Инициализация PM130...", LogType.MESSAGE)
        parma41.checkResponsibility()

        if (isRun) delay(1000)
        if (!parma41.isResponding) {
            appendToLog("PM130 не отвечает", LogType.ERROR)
            isRun = false
        }
        CM.startPoll(DeviceID.PAV41.name, PM130Model.U_AB_REGISTER) { value ->
            vm.u_a.value = (value.toDouble() * ktrVoltage).af()
            if (!parma41.isResponding && isRun) {
                appendToLog("PM130 не отвечает", LogType.ERROR)
                isRun = false
            }
        }
        CM.startPoll(DeviceID.PAV41.name, PM130Model.U_BC_REGISTER) { value ->
            vm.u_uv.value = (value.toDouble() * ktrVoltage).af()
            if (value.toDouble() * ktrVoltage > vm.testItem.selectedTI!!.u_linear.toInt() * 1.1) {
                appendToLog(("Overvoltage"), LogType.ERROR)
                isRun = false
            }
            if (!parma41.isResponding && isRun) {
                appendToLog("PM130 не отвечает", LogType.ERROR)
                isRun = false
            }
        }
        CM.startPoll(DeviceID.PAV41.name, PM130Model.U_CA_REGISTER) { value ->
            vm.u_vw.value = (value.toDouble() * ktrVoltage).af()
            if (value.toDouble() * ktrVoltage > vm.testItem.selectedTI!!.u_linear.toInt() * 1.1) {
                appendToLog(("Overvoltage"), LogType.ERROR)
                isRun = false
            }
        }
        CM.startPoll(DeviceID.PAV41.name, PM130Model.U_CA_REGISTER) { value ->
            vm.u_wu.value = (value.toDouble() * ktrVoltage).af()
            voltAverage =
                (vm.u_uv.value.toDoubleOrDefault(0.0)
                        + vm.u_vw.value.toDoubleOrDefault(0.0)
                        + vm.u_wu.value.toDoubleOrDefault(0.0)) / 3 * ktrVoltage
            if (value.toDouble() * ktrVoltage > vm.testItem.selectedTI!!.u_linear.toInt() * 1.1) {
                appendToLog(("Overvoltage"), LogType.ERROR)
                isRun = false
            }
        }
        CM.startPoll(DeviceID.PAV41.name, PM130Model.I_A_REGISTER) {
            vm.i_u.value = (it.toDouble() * ktrAmperage).af()
            if (isRun) {
                if (it.toDouble() > 6.0) {
                    appendToLog("Превышено допустимое значение тока")
                    isRun = false
                }
            }
        }
        CM.startPoll(DeviceID.PAV41.name, PM130Model.I_B_REGISTER) {
            vm.i_v.value = (it.toDouble() * ktrAmperage).af()
            if (isRun) {
                if (it.toDouble() > 6.0) {
                    appendToLog("Превышено допустимое значение тока")
                    isRun = false
                }
            }
        }
        CM.startPoll(DeviceID.PAV41.name, PM130Model.I_C_REGISTER) {
            vm.i_w.value = (it.toDouble() * ktrAmperage).af()
            if (isRun) {
                if (it.toDouble() > 6.0) {
                    appendToLog("Превышено допустимое значение тока")
                    isRun = false
                }
            }
        }
        CM.startPoll(DeviceID.PAV41.name, PM130Model.COS_REGISTER) {
            vm.cos.value = abs(it.toDouble()).af()
        }

//        ru.avem.modules.devices.CM.startPoll(DeviceID.PAV41.name, parma41.model.U_A_REGISTER) {
//            u_b = it.toDouble()
//        }
//        ru.avem.modules.devices.CM.startPoll(DeviceID.PAV41.name, PM130Model.P_A_REGISTER) {
//            vm.pA.value = abs(it.toDouble() * ktrAmperage * ktrVoltage).af()
//        }
    }

    suspend fun initAVEM9 () {
        appendToLog("Инициализация АВЭМ-9", LogType.MESSAGE)
        var timer = 10
        while (!pr66.isResponding) {
            --timer
            delay(1000)
            pr66.checkResponsibility()
            if (timer < 0) {
                isRun = false
                appendToLog("АВЭМ-9 не отвечает", LogType.ERROR)
                break
            }
        }

        if (isRun) pr66.pollVoltageAKB()
        if (isRun && pr66.lowBattery.value) {
            appendToLog("Низкий заряд АВЭМ-9. Подождите немного и повторите запуск", LogType.ERROR)
            isRun = false
        }
    }

    suspend fun initARN() {
        appendToLog("Инициализация ATP...", LogType.MESSAGE)
        ATR.checkResponsibility()
        delay(1000)

        if (ATR.isResponding) {
            CM.startPoll(DeviceID.GV240.name, ATRModel.U_RMS_REGISTER) { value ->
                voltOnATR = value.toDouble()
                if (!ATR.isResponding) {
                    appendToLog("АРН не отвечает", LogType.ERROR)
                    isRun = false
                }
            }
            CM.startPoll(DeviceID.GV240.name, ATRModel.ENDS_STATUS_REGISTER) { value ->
                latrEndsState = value.toInt()
            }
        } else {
            appendToLog("АРН не отвечает", LogType.ERROR)
            isRun = false
        }
    }
}