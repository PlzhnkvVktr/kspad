package ru.avem.kspem.devices.parma

import ru.avem.library.polling.DeviceRegister
import ru.avem.library.polling.IDeviceModel

class ParmaModel : IDeviceModel {
    val F_REGISTER = "F_REGISTER"
    val P_REGISTER = "P_REGISTER"
    val Q_REGISTER = "Q_REGISTER"
    val S_REGISTER = "S_REGISTER"
    val U_AB_REGISTER = "U_AB_REGISTER"
    val U_BC_REGISTER = "U_BC_REGISTER"
    val U_CA_REGISTER = "U_CA_REGISTER"
    val I_A_REGISTER = "I_A_REGISTER"
    val I_B_REGISTER = "I_B_REGISTER"
    val I_C_REGISTER = "I_C_REGISTER"
    val COS_REGISTER = "COS_REGISTER"
    val U_A_REGISTER = "U_A_REGISTER"
    val U_B_REGISTER = "U_B_REGISTER"
    val U_C_REGISTER = "U_C_REGISTER"
    val P_A_REGISTER = "P_A_REGISTER"
    val P_B_REGISTER = "P_B_REGISTER"
    val P_C_REGISTER = "P_C_REGISTER"
    val COS_A_REGISTER = "COS_A_REGISTER"

    enum class AdditionalParma(val isUShort: Boolean, val coeff: Double) {
        F_REGISTER(true, 50.0 / 50000),
        P_REGISTER(false,3300.0 / 16500),
        Q_REGISTER(false,3300.0 / 16500),
        S_REGISTER(true, 3300.0 / 16500),
        U_AB_REGISTER(true, 380.0 / 19000),
        U_BC_REGISTER(true, 380.0 / 19000),
        U_CA_REGISTER(true, 380.0 / 19000),
        I_A_REGISTER(true, 5.0 / 25000),
        I_B_REGISTER(true, 5.0 / 25000),
        I_C_REGISTER(true, 5.0 / 25000),
        U_A_REGISTER(true, 220.0 / 22000),
        U_B_REGISTER(true, 220.0 / 22000),
        U_C_REGISTER(true, 220.0 / 22000),
        P_A_REGISTER(false, 1100.0 / 11000),
        P_B_REGISTER(false, 1100.0 / 11000),
        P_C_REGISTER(false, 1100.0 / 11000),
        COS_REGISTER(false, 1.0 / 10000),
        COS_A_REGISTER(false, 1.0 / 10000)
    }

    override val registers: Map<String, DeviceRegister> = mapOf(
        F_REGISTER to DeviceRegister(0x0000, DeviceRegister.RegisterValueType.SHORT),
        P_REGISTER to DeviceRegister(0x0001, DeviceRegister.RegisterValueType.SHORT),
        Q_REGISTER to DeviceRegister(0x0002, DeviceRegister.RegisterValueType.SHORT),
        S_REGISTER to DeviceRegister(0x0003, DeviceRegister.RegisterValueType.SHORT),
        U_AB_REGISTER to DeviceRegister(0x0004, DeviceRegister.RegisterValueType.SHORT),
        U_BC_REGISTER to DeviceRegister(0x0005, DeviceRegister.RegisterValueType.SHORT),
        U_CA_REGISTER to DeviceRegister(0x0006, DeviceRegister.RegisterValueType.SHORT),
        I_A_REGISTER to DeviceRegister(0x0007, DeviceRegister.RegisterValueType.SHORT),
        I_B_REGISTER to DeviceRegister(0x0008, DeviceRegister.RegisterValueType.SHORT),
        I_C_REGISTER to DeviceRegister(0x0009, DeviceRegister.RegisterValueType.SHORT),

        U_A_REGISTER to DeviceRegister(0x000B, DeviceRegister.RegisterValueType.SHORT),
        U_B_REGISTER to DeviceRegister(0x000C, DeviceRegister.RegisterValueType.SHORT),
        U_C_REGISTER to DeviceRegister(0x000D, DeviceRegister.RegisterValueType.SHORT),


        P_A_REGISTER to DeviceRegister(0x000F, DeviceRegister.RegisterValueType.SHORT),
        P_B_REGISTER to DeviceRegister(0x0010, DeviceRegister.RegisterValueType.SHORT),
        P_C_REGISTER to DeviceRegister(0x0011, DeviceRegister.RegisterValueType.SHORT),

        COS_REGISTER to DeviceRegister(0x001D, DeviceRegister.RegisterValueType.SHORT),
        COS_A_REGISTER to DeviceRegister(0x0018, DeviceRegister.RegisterValueType.SHORT)
    )


    override fun getRegisterById(idRegister: String) =
        registers[idRegister] ?: error("Такого регистра нет в описанной карте $idRegister")
}
