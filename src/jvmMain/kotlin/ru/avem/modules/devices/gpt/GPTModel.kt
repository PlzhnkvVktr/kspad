package ru.avem.modules.devices.gpt

import ru.avem.library.polling.DeviceRegister
import ru.avem.library.polling.IDeviceModel

class GPTModel : IDeviceModel {
    companion object {
        const val VOLTAGE = "VOLTAGE"
        const val AMPERAGE = "AMPERAGE"
        const val DCR = "DCR"
        const val RESULT = "RESULT"
    }

    override val registers: Map<String, DeviceRegister> = mapOf(
        VOLTAGE to DeviceRegister(0, DeviceRegister.RegisterValueType.SHORT, k = 1000.0),
        AMPERAGE to DeviceRegister(1, DeviceRegister.RegisterValueType.SHORT),
        DCR to DeviceRegister(1, DeviceRegister.RegisterValueType.SHORT),
        RESULT to DeviceRegister(2, DeviceRegister.RegisterValueType.SHORT)
    )

    override fun getRegisterById(idRegister: String) =
        registers[idRegister] ?: error("Такого регистра нет в описанной карте $idRegister")
}
