package ru.avem.modules.devices.avem.avem4

import ru.avem.library.polling.DeviceRegister
import ru.avem.library.polling.IDeviceModel


class AVEM4Model : IDeviceModel {
    companion object {
        const val RMS_VOLTAGE = "RMS_VOLTAGE"
        const val AMP_VOLTAGE = "AMP_VOLTAGE"
        const val AMP_TM_VOLTAGE = "AMP_TM_VOLTAGE"
        const val AVRG_VOLTAGE = "AVRG_VOLTAGE"
        const val KTR_RUNTIME = "KTR_RUNTIME"
        const val SERIAL_NUMBER = "SERIAL_NUMBER"
        const val SOFTWARE_DATE = "SOFTWARE_DATE"
        const val KTR_FLASH = "KTR_FLASH"
        const val SHOW_VALUE = "SHOW_VALUE"
    }

    override val registers: Map<String, DeviceRegister> = mapOf(
        RMS_VOLTAGE to DeviceRegister(0x1004, DeviceRegister.RegisterValueType.FLOAT),
        AMP_VOLTAGE to DeviceRegister(0x1000, DeviceRegister.RegisterValueType.FLOAT),
        AMP_TM_VOLTAGE to DeviceRegister(0x1010, DeviceRegister.RegisterValueType.FLOAT),
        AVRG_VOLTAGE to DeviceRegister(0x1002, DeviceRegister.RegisterValueType.FLOAT),
        KTR_RUNTIME to DeviceRegister(0x10BC, DeviceRegister.RegisterValueType.FLOAT),
        SERIAL_NUMBER to DeviceRegister(0x1108, DeviceRegister.RegisterValueType.INT32),
        SOFTWARE_DATE to DeviceRegister(0x1022, DeviceRegister.RegisterValueType.INT32),
        KTR_FLASH to DeviceRegister(0x10CE, DeviceRegister.RegisterValueType.FLOAT),
        SHOW_VALUE to DeviceRegister(0x10D9, DeviceRegister.RegisterValueType.SHORT)
    )

    override fun getRegisterById(idRegister: String) =
        registers[idRegister] ?: error("Такого регистра нет в описанной карте $idRegister")
}
