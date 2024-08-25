package ru.avem.kspem.devices.parma

import ru.avem.kserialpooler.adapters.modbusrtu.ModbusRTUAdapter
import ru.avem.kserialpooler.adapters.utils.ModbusRegister
import ru.avem.kserialpooler.utils.TransportException
import ru.avem.library.polling.DeviceRegister
import ru.avem.library.polling.IDeviceController


class ParmaController(
    override val name: String,
    override val protocolAdapter: ModbusRTUAdapter,
    override val id: Byte
) : IDeviceController {
    val model = ParmaModel()
    override var isResponding = false
    override var requestTotalCount = 0
    override var requestSuccessCount = 0
    override val pollingRegisters = mutableListOf<DeviceRegister>()
    override val writingRegisters = mutableListOf<Pair<DeviceRegister, Number>>()


    override fun readRegister(register: DeviceRegister) {
        isResponding = try {
            transactionWithAttempts {
                if (getParam(register).isUShort) {
                    val modbusRegister =
                        protocolAdapter.readInputRegisters(id, register.address, 1).map(ModbusRegister::toUShort)
                    register.value =
                        modbusRegister.first().toInt() * getParam(register).coeff
                } else {
                    val modbusRegister =
                        protocolAdapter.readInputRegisters(id, register.address, 1).map(ModbusRegister::toShort)
                    register.value = modbusRegister.first() * getParam(register).coeff
                }
            }
            true
        } catch (e: TransportException) {
            false
        }
    }

    fun getParam(register: DeviceRegister): ParmaModel.AdditionalParma {
        val kk = model.registers.filter { it.value == register }.keys.first()
        return ParmaModel.AdditionalParma.valueOf(kk)
    }

    override fun readAllRegisters() {
        model.registers.values.forEach {
            readRegister(it)
        }
    }

    override fun <T : Number> writeRegister(register: DeviceRegister, value: T) {

    }

    override fun writeRegisters(register: DeviceRegister, values: List<Short>) {
        val registers = values.map { ModbusRegister(it) }
        isResponding = try {
            transactionWithAttempts {
                protocolAdapter.presetMultipleRegisters(id, register.address, registers)
            }
            true
        } catch (e: TransportException) {
            false
        }
    }

    override fun writeRequest(request: String) {
        TODO("Not yet implemented")
    }

    override fun checkResponsibility() {
        model.registers.values.firstOrNull()?.let {
            readRegister(it)
        }
    }

    override fun getRegisterById(idRegister: String) = model.getRegisterById(idRegister)
}


