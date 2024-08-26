package ru.avem.modules.tests

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.compose.koinInject
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.avem.data.db.TestItem
import ru.avem.data.enums.DeviceID
import ru.avem.data.enums.LogType
import ru.avem.data.models.LogMessage
import ru.avem.data.repos.AppConfig
//import ru.avem.common.af
//import ru.avem.common.repos.AppConfig
//import ru.avem.db.TestItem
//import ru.avem.kspem.devices.parma.ParmaController
import ru.avem.modules.devices.CM
import ru.avem.modules.devices.avem.atr.ATR
import ru.avem.modules.devices.avem.atr.ATRModel
import ru.avem.modules.devices.avem.avem4.AVEM4
import ru.avem.modules.devices.avem.avem7.AVEM7
import ru.avem.modules.devices.owen.pr.PR
//import ru.avem.modules.tests.utils.ms
//import ru.avem.modules.tests.utils.toDoubleOrDefault
import ru.avem.modules.devices.avem.avem9.AVEM9
//import ru.avem.modules.tests.tl.cosB
//import ru.avem.modules.tests.tl.u_b
//import ru.avem.viewmodels.TestScreenViewModel
import java.text.SimpleDateFormat
import kotlin.experimental.and

open class CustomController: KoinComponent {
    val CM by inject<CM>()
    val pr102 = CM.getDeviceByID<PR>(DeviceID.DD2_1)
//    val parma41 = CM.getDeviceByID<ParmaController>(CM.DeviceID.PAV41)
    val ATR = CM.getDeviceByID<ATR>(DeviceID.GV240)
    val pv24 = CM.getDeviceByID<AVEM4>(DeviceID.PV24)
    val pa62 = CM.getDeviceByID<AVEM7>(DeviceID.PA62)
    val pr66 = CM.getDeviceByID<AVEM9>(DeviceID.PR66)
    val pv61 = CM.getDeviceByID<AVEM4>(DeviceID.PV61)

    var isRun by mutableStateOf(false)

    val currentOperator = mutableStateOf(AppConfig.params.login)

    var isStartPressed by mutableStateOf(false)
    var isStopPressed by mutableStateOf(false)
    var doorZone by mutableStateOf(false)
    var doorSCO by mutableStateOf(false)
    var ikzTI by mutableStateOf(false)
    var ikzVIU by mutableStateOf(false)

    var statusMGR: Int = 0

    var voltOnATR = 1.0
    var ktrVoltage = 1.0
    var voltAverage = 0.0
    var ktrAmperage = 1.0

    var voltage = 0.0
    var setVoltage = 0.0
    var latrEndsState = 0

    var izmState = false
    var indState = false

    val logMessages = mutableStateListOf<LogMessage>(LogMessage("sss", LogType.ERROR))
    var isStartButton = mutableStateOf(false)

    var testObjectName = mutableStateOf("")
    lateinit var testObject: TestItem

    fun appendMessageToLog(text: String, type: LogType = LogType.DEBUG) {
        println("===> " + logMessages.size)
        val msg = "${SimpleDateFormat("HH:mm:ss").format(System.currentTimeMillis())} | $text"
        logMessages.add(LogMessage(msg, type))
        println("===> " + logMessages.size)
    }

    fun initWatchDogDD2() {
        with(pr102) {
            initWithoutProtections()
            offAllKMs()
            CM.addWritingRegister(name, pr102.model.CMD, 1.toShort())
            CM.startPoll(name, pr102.model.STATE) { }
        }
    }

    suspend fun initButtonPost() {

        appendMessageToLog("Нажмите Пуск", LogType.ERROR)
        isStartButton.value = true
        while (isRun && pr102.isResponding) {
            if (isStartPressed) {
                isStartButton.value = false
                break
            }
            if (isStopPressed) {
                isStartButton.value = false
                isRun = false
                appendMessageToLog("Испытание остановлено", LogType.ERROR)
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
                appendMessageToLog("Напряжение АРН > 10 В", LogType.ERROR)
                isRun = false
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

    suspend fun initPR() {
        pr102.checkResponsibility()
        val scope = CoroutineScope(Dispatchers.Default)
        appendMessageToLog("Инициализация БСУ...", LogType.MESSAGE)
        isStartPressed = false
        isStopPressed = false
        var stateLock = false
        var count = 0
        if (!pr102.isResponding) {
            appendMessageToLog("ПР102 не отвечает", LogType.ERROR)
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
            scope.launch {
                while (isRun) {
                    delay(100)
                    if (!pr102.isResponding) {
                        appendMessageToLog("ПР102 не отвечает", LogType.ERROR)
                        isRun = false
                    }
                    if (isStopPressed) {
                        appendMessageToLog("Нажата кнопка <СТОП>", LogType.ERROR)
                        isRun = false
                    }
                    if (doorZone) {
                        appendMessageToLog("Открыты двери зоны", LogType.ERROR)
                        isRun = false
                    }
                    if (doorSCO) {
                        appendMessageToLog("Открыты двери ШСО", LogType.ERROR)
                        isRun = false
                    }
                    if (ikzTI) {
                        count++
                        if (count >= 8) {
                            appendMessageToLog("Превышение тока ОИ", LogType.ERROR)
                            stateLock = true
                            isRun = false
                        }
                    } else {
                        count = 0
                    }
                    if (ikzVIU) {
                        appendMessageToLog("Превышение тока ОИ", LogType.ERROR)
                        isRun = false
                    }
                    if (!isRun) {
                        appendMessageToLog("Измерение завершено", LogType.ERROR)
                    }
                }
                stopTestRunning()
            }
        }
    }

//    fun initAVEM4 (vm: TestScreenViewModel) {
//        with(pv61) {
//            checkResponsibility()
//            if (!isResponding) {
//                appendMessageToLog("АВЭМ-4 не отвечает", LogType.ERROR)
//                isTestRunning.value = false
////                    appendMessageToLog("PV21 не отвечает", LogType.ERROR)
//            } else {
//                CM.startPoll(CM.DeviceID.PV61.name, AVEM4Model.RMS_VOLTAGE) { value ->
//                    vm.testItem.ikas_v.value = (value.toDouble()).af()
//                }
//            }
//        }
//    }
//    fun initAVEM7 (vm: TestScreenViewModel) {
//        with(pa62) {
//            checkResponsibility()
//            if (!isResponding) {
//                appendMessageToLog("АВЕМ-7 не отвечает", LogType.ERROR)
//                isTestRunning.value = false
//            } else {
//                CM.startPoll(CM.DeviceID.PA62.name, AVEM7Model.AMPERAGE) { value ->
//                    vm.testItem.ikas_i.value = (value.toDouble()).af()
//                    if (!pa62.isResponding) {
//                        appendMessageToLog("PV21 не отвечает", LogType.ERROR)
//                    }
//                }
//            }
//        }
//    }
    suspend fun initAVEM9 () {
        appendMessageToLog("Инициализация АВЭМ-9", LogType.MESSAGE)
        var timer = 10
        while (!pr66.isResponding) {
            --timer
            delay(1000)
            pr66.checkResponsibility()
            if (timer < 0) {
                isRun = false
                appendMessageToLog("АВЭМ-9 не отвечает", LogType.ERROR)
                break
            }
        }

        if (isRun) pr66.pollVoltageAKB()
        if (isRun && pr66.lowBattery.value) {
            appendMessageToLog("Низкий заряд АВЭМ-9. Подождите немного и повторите запуск", LogType.ERROR)
            isRun = false
        }
    }

//    fun initVibro(pol: MutableState<String>, rab: MutableState<String>) {
//        CM.startPoll(CM.DeviceID.DD2_1.name, pr102.model.VIBRO_POL) { rab.value = it.af() }
//        CM.startPoll(CM.DeviceID.DD2_1.name, pr102.model.VIBRO_RAB) { pol.value = it.af() }
//    }

//    fun checkRPM(n: MutableState<String>) {
//        if (n.value.toDouble() > testObject.specifiedRPM.toDouble() / 2) {
//            appendMessageToLog(("Waiting for engine to stop"), LogType.ERROR)
//            while (isTestRunning.value && n.value.toDouble() > testObject.specifiedRPM.toDouble() / 2) {
//                sleep(1000)
//            }
//        }
//    }

    suspend fun initARN() {
        appendMessageToLog("Инициализация ATP...", LogType.MESSAGE)
        ATR.checkResponsibility()
        delay(1000)

        if (ATR.isResponding) {
            CM.startPoll(DeviceID.GV240.name, ATRModel.U_RMS_REGISTER) { value ->
                voltOnATR = value.toDouble()
                if (!ATR.isResponding) {
                    appendMessageToLog("АРН не отвечает", LogType.ERROR)
                    isRun = false
                }
            }
            CM.startPoll(DeviceID.GV240.name, ATRModel.ENDS_STATUS_REGISTER) { value ->
                latrEndsState = value.toInt()
            }
        } else {
            appendMessageToLog("АРН не отвечает", LogType.ERROR)
            isRun = false
        }
    }

//    suspend fun initPM130(vm: TestScreenViewModel) {
//        appendMessageToLog("Инициализация PM130...", LogType.MESSAGE)
//        parma41.checkResponsibility()
//
//        if (isTestRunning.value) delay(1000)
//        if (!parma41.isResponding) {
//            appendMessageToLog("PM130 не отвечает", LogType.ERROR)
//            isTestRunning.value = false
//        }
//        CM.startPoll(CM.DeviceID.PAV41.name, parma41.model.U_B_REGISTER) { value ->
//            vm.u_a.value = (value.toDouble() * ktrVoltage).af()
//            if (!parma41.isResponding && isTestRunning.value) {
//                appendMessageToLog("PM130 не отвечает", LogType.ERROR)
//                isTestRunning.value = false
//            }
//        }
//        CM.startPoll(CM.DeviceID.PAV41.name, parma41.model.U_AB_REGISTER) { value ->
//            vm.u_uv.value = (value.toDouble() * ktrVoltage).af()
//            if (value.toDouble() * ktrVoltage > testObject.u_linear.toInt() * 1.1) {
//                appendMessageToLog(("Overvoltage"), LogType.ERROR)
//                isTestRunning.value = false
//            }
//            if (!parma41.isResponding && isTestRunning.value) {
//                appendMessageToLog("PM130 не отвечает", LogType.ERROR)
//                isTestRunning.value = false
//            }
//        }
//        CM.startPoll(CM.DeviceID.PAV41.name, parma41.model.U_BC_REGISTER) { value ->
//            vm.u_vw.value = (value.toDouble() * ktrVoltage).af()
//            if (value.toDouble() * ktrVoltage > testObject.u_linear.toInt() * 1.1) {
//                appendMessageToLog(("Overvoltage"), LogType.ERROR)
//                isTestRunning.value = false
//            }
//        }
//        CM.startPoll(CM.DeviceID.PAV41.name, parma41.model.U_CA_REGISTER) { value ->
//            vm.u_wu.value = (value.toDouble() * ktrVoltage).af()
//            voltAverage =
//                (vm.u_uv.value.toDoubleOrDefault(0.0)
//                        + vm.u_vw.value.toDoubleOrDefault(0.0)
//                        + vm.u_wu.value.toDoubleOrDefault(0.0)) / 3 * ktrVoltage
//            if (value.toDouble() * ktrVoltage > testObject.u_linear.toInt() * 1.1) {
//                appendMessageToLog(("Overvoltage"), LogType.ERROR)
//                isTestRunning.value = false
//            }
//        }
//        CM.startPoll(CM.DeviceID.PAV41.name, parma41.model.I_A_REGISTER) {
//            vm.i_u.value = (it.toDouble() * ktrAmperage).af()
//            if (isTestRunning.value) {
//                if (it.toDouble() > 6.0) {
//                    appendMessageToLog("Превышено допустимое значение тока")
//                    isTestRunning.value = false
//                }
//            }
//        }
//        CM.startPoll(CM.DeviceID.PAV41.name, parma41.model.I_B_REGISTER) {
//            vm.i_v.value = (it.toDouble() * ktrAmperage).af()
//            if (isTestRunning.value) {
//                if (it.toDouble() > 6.0) {
//                    appendMessageToLog("Превышено допустимое значение тока")
//                    isTestRunning.value = false
//                }
//            }
//        }
//        CM.startPoll(CM.DeviceID.PAV41.name, parma41.model.I_C_REGISTER) {
//            vm.i_w.value = (it.toDouble() * ktrAmperage).af()
//            if (isTestRunning.value) {
//                if (it.toDouble() > 6.0) {
//                    appendMessageToLog("Превышено допустимое значение тока")
//                    isTestRunning.value = false
//                }
//            }
//        }
//        CM.startPoll(CM.DeviceID.PAV41.name, parma41.model.COS_REGISTER) {
//            vm.cos.value = abs(it.toDouble()).af()
//        }
//        CM.startPoll(CM.DeviceID.PAV41.name, parma41.model.COS_A_REGISTER) {
//            cosB = it.toDouble()
//        }
//        CM.startPoll(CM.DeviceID.PAV41.name, parma41.model.U_A_REGISTER) {
//            u_b = it.toDouble()
//        }
//        CM.startPoll(CM.DeviceID.PAV41.name, parma41.model.P_A_REGISTER) {
//            vm.pA.value = abs(it.toDouble() * ktrAmperage * ktrVoltage).af()
//        }
//    }
//        if (P1 != null) {
//            CM.startPoll(CM.DeviceID.PAV41.name, PM130Model.P_REGISTER) {
//                P1.value = abs(it.toDouble() * ktrAmperage * ktrVoltage).af()
//            }
//        }
//        if (cos != null) {
//            CM.startPoll(CM.DeviceID.PAV41.name, PM130Model.COS_REGISTER) {
//                cos.value = abs(it.toDouble()).af()
//            }
//        }
//        if (F != null) {
//            CM.startPoll(CM.DeviceID.PAV41.name, PM130Model.F_REGISTER) { F.value = it.af() }
//        }


//    fun chooseCurrentStage(
//        iu: MutableState<String>,
//        iv: MutableState<String>,
//        iw: MutableState<String>
//    ) {
//        appendMessageToLog("Подбор токовой ступени", LogType.MESSAGE)
//        var Iu = iu.value.toDoubleOrDefault(100.0)
//        var Iv = iv.value.toDoubleOrDefault(100.0)
//        var Iw = iw.value.toDoubleOrDefault(100.0)
//
//        appendMessageToLog("Проверка <80А: $Iu А | $Iv А | $Iw А", LogType.MESSAGE)
//        if (Iu < 80.0 && Iv < 80.0 && Iw < 80.0) {
//            pr102.i80(true)
//            pr102.iMax250(false)
//            ktrAmperage = 80.0 / 5.0
//        }
//        if (isTestRunning.value) {
//            var timer = 5.0
//            while (isTestRunning.value && timer > 0) {
//                sleep(100)
//                timer -= 0.1
//            }
//        }
//        Iu = iu.value.toDoubleOrDefault(100.0)
//        Iv = iv.value.toDoubleOrDefault(100.0)
//        Iw = iw.value.toDoubleOrDefault(100.0)
//        appendMessageToLog("Проверка <20А: $Iu А | $Iv А | $Iw А", LogType.MESSAGE)
//        if (Iu < 20.0 && Iv < 20.0 && Iw < 20.0) {
//            pr102.i20(true)
//            pr102.i80(false)
//            ktrAmperage = 20.0 / 5.0
//        }
//        if (isTestRunning.value) {
//            var timer = 5.0
//            while (isTestRunning.value && timer > 0) {
//                Thread.sleep(100)
//                timer -= 0.1
//            }
//        }
//        Iu = iu.value.toDoubleOrDefault(100.0)
//        Iv = iv.value.toDoubleOrDefault(100.0)
//        Iw = iw.value.toDoubleOrDefault(100.0)
//        appendMessageToLog("Проверка <5А: $Iu А | $Iv А | $Iw А", LogType.MESSAGE)
//        if (Iu < 5.0 && Iv < 5.0 && Iw < 5.0) {
//            pr102.iMin(true)
//            pr102.i20(false)
//            ktrAmperage = 5.0 / 5.0
//        }
//
//    }
}