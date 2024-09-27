package ru.avem.modules.tests

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.avem.data.enums.DeviceID
import ru.avem.data.enums.LogType
import ru.avem.data.models.LogMessage
import ru.avem.kspem.devices.parma.ParmaController
//import ru.avem.common.af
//import ru.avem.common.repos.AppConfig
//import ru.avem.db.TestItem
//import ru.avem.kspem.devices.parma.ParmaController
import ru.avem.modules.devices.CM
import ru.avem.modules.devices.avem.atr.ATR
import ru.avem.modules.devices.avem.avem4.AVEM4
import ru.avem.modules.devices.avem.avem7.AVEM7
import ru.avem.modules.devices.owen.pr.PR
//import ru.avem.modules.tests.utils.ms
//import ru.avem.modules.tests.utils.toDoubleOrDefault
import ru.avem.modules.devices.avem.avem9.AVEM9
import ru.avem.modules.devices.pm130.PM130
//import ru.avem.modules.tests.tl.cosB
//import ru.avem.modules.tests.tl.u_b
//import ru.avem.viewmodels.TestScreenViewModel
import java.text.SimpleDateFormat

open class DeviceManager: KoinComponent, ProtectionManager {
    val CM by inject<CM>()

    override var doorZone by mutableStateOf(false)
    override var doorSCO by mutableStateOf(false)
    override var ikzTI by mutableStateOf(false)
    override var ikzVIU by mutableStateOf(false)
    override var isStartPressed by mutableStateOf(false)
    override var isStopPressed by mutableStateOf(false)

    val pr102 = CM.getDeviceByID<PR>(DeviceID.DD2_1)
    val parma41 = CM.getDeviceByID<PM130>(DeviceID.PAV41)
    val ATR = CM.getDeviceByID<ATR>(DeviceID.GV240)
    val pv24 = CM.getDeviceByID<AVEM4>(DeviceID.PV24)
    val pa62 = CM.getDeviceByID<AVEM7>(DeviceID.PA62)
    val pr66 = CM.getDeviceByID<AVEM9>(DeviceID.PR66)
    val pv61 = CM.getDeviceByID<AVEM4>(DeviceID.PV61)

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

    val logMessages = mutableStateListOf<LogMessage>()
    var isStartButton = mutableStateOf(false)

    var testObjectName = mutableStateOf("")

    fun appendToLog(text: String, type: LogType = LogType.DEBUG) {
        val msg = "${SimpleDateFormat("HH:mm:ss").format(System.currentTimeMillis())} | $text"
        logMessages.add(LogMessage(msg, type))
    }

}