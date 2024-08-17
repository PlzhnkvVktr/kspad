package ru.avem.modules.devices

import ru.avem.kserialpooler.Connection
import ru.avem.kserialpooler.adapters.modbusrtu.ModbusRTUAdapter
import ru.avem.kserialpooler.adapters.serial.SerialAdapter
import ru.avem.kserialpooler.utils.SerialParameters
import ru.avem.modules.devices.avem.atr.ATR
import ru.avem.library.polling.IDeviceController
import ru.avem.library.polling.SimplePollingModel
import ru.avem.modules.devices.avem.avem4.AVEM4
import ru.avem.modules.devices.avem.avem7.AVEM7
import ru.avem.modules.devices.avem.avem9.AVEM9
import ru.avem.modules.devices.avem.ikas10.IKAS10
import ru.avem.modules.devices.bris.m4122.M4122
import ru.avem.modules.devices.owen.pr.PR
import ru.avem.modules.devices.owen.th01.TH01
import ru.avem.modules.devices.owen.trm202.TRM202
import ru.avem.modules.devices.pm130.PM130

object CM : SimplePollingModel() {
    enum class DeviceID(val description: String) {
        DD2_1("ПР102-21.2416.03.1"),
        PV24("АВЭМ-3-04 U ВИУ 2000В"),
        PAV41("PM-130P"),
        PV61("АВЭМ-4-01"),
        PA62("АВЭМ-7-5000"),
        PR66("АВЭМ-9"),
        GV240("АРН-1-24-220")
    }

    private val connectionMain = Connection(
        adapterName = "CP2103 USB to RS-485",
        serialParameters = SerialParameters(8, 0, 1, 38400),
        timeoutRead = 100,
        timeoutWrite = 100
    ).apply { connect() }

    private val modbusAdapter = ModbusRTUAdapter(connectionMain)

    override val deviceControllers: Map<String, IDeviceController> = mapOf(
        DeviceID.DD2_1.name to PR(DeviceID.DD2_1.name, modbusAdapter, 2),
        DeviceID.PV24.name to AVEM4(DeviceID.PV24.name, modbusAdapter, 24),
        DeviceID.PV61.name to AVEM4(DeviceID.PV61.name, modbusAdapter, 61),
        DeviceID.PA62.name to AVEM7(DeviceID.PA62.name, modbusAdapter, 62),
        DeviceID.PR66.name to AVEM9(DeviceID.PR66.name, modbusAdapter, 66),
        DeviceID.PAV41.name to PM130(DeviceID.PAV41.name, modbusAdapter, 41),
        DeviceID.GV240.name to ATR(DeviceID.GV240.name, modbusAdapter, 240.toByte())
    )

    init {
        start()
    }

    @Suppress("UNCHECKED_CAST")
    fun <T : IDeviceController> getDeviceByID(deviceID: DeviceID): T =
        (deviceControllers[deviceID.name] as T?) ?: error("Не определено $deviceID")

    fun reconnect() {
        connectionMain.disconnect()
        connectionMain.connect()
    }
}
