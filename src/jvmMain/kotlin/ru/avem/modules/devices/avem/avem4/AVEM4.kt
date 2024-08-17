package ru.avem.modules.devices.avem.avem4

import ru.avem.kserialpooler.adapters.modbusrtu.ModbusRTUAdapter
import ru.avem.kserialpooler.adapters.utils.ModbusRegister
import ru.avem.kserialpooler.utils.TransportException
import ru.avem.kserialpooler.utils.TypeByteOrder
import ru.avem.kserialpooler.utils.allocateOrderedByteBuffer
import ru.avem.library.polling.DeviceRegister
import ru.avem.library.polling.IDeviceController

import java.lang.Thread.sleep
import java.nio.ByteBuffer
import java.nio.ByteOrder

class AVEM4(
    override val name: String,
    override val protocolAdapter: ModbusRTUAdapter,
    override val id: Byte
) : IDeviceController {
    private val model = AVEM4Model()
    override var isResponding = false
    override var requestTotalCount = 0
    override var requestSuccessCount = 0
    override val pollingRegisters = mutableListOf<DeviceRegister>()
    override val writingRegisters = mutableListOf<Pair<DeviceRegister, Number>>()

    override fun readRegister(register: DeviceRegister) {
        isResponding = try {
            transactionWithAttempts {
                when (register.valueType) {
                    DeviceRegister.RegisterValueType.SHORT -> {
                        val value =
                            protocolAdapter.readHoldingRegisters(id, register.address, 1).first().toShort()
                        register.value = value
                    }

                    DeviceRegister.RegisterValueType.FLOAT -> {
                        val modbusRegister =
                            protocolAdapter.readHoldingRegisters(id, register.address, 2).map(ModbusRegister::toShort)
                        register.value =
                            allocateOrderedByteBuffer(modbusRegister, TypeByteOrder.BIG_ENDIAN, 4).float
                    }

                    DeviceRegister.RegisterValueType.INT32 -> {
                        val modbusRegister =
                            protocolAdapter.readHoldingRegisters(id, register.address, 2).map(ModbusRegister::toShort)
                        register.value =
                            allocateOrderedByteBuffer(modbusRegister, TypeByteOrder.BIG_ENDIAN, 4).int
                    }
                }
            }
            true
        } catch (e: TransportException) {
            false
        }
    }

    override fun <T : Number> writeRegister(register: DeviceRegister, value: T) {
        isResponding = try {
            when (value) {
                is Float -> {
                    val bb = ByteBuffer.allocate(4).putFloat(value).order(ByteOrder.BIG_ENDIAN)
                    val registers = listOf(ModbusRegister(bb.getShort(2)), ModbusRegister(bb.getShort(0)))
                    transactionWithAttempts {
                        protocolAdapter.presetMultipleRegisters(id, register.address, registers)
                    }
                }

                is Int -> {
                    val bb = ByteBuffer.allocate(4).putInt(value).order(ByteOrder.BIG_ENDIAN)
                    val registers = listOf(ModbusRegister(bb.getShort(2)), ModbusRegister(bb.getShort(0)))
                    transactionWithAttempts {
                        protocolAdapter.presetMultipleRegisters(id, register.address, registers)
                    }
                }

                is Short -> {
                    transactionWithAttempts {
                        protocolAdapter.presetSingleRegister(id, register.address, ModbusRegister(value))
                    }
                }

                else -> {
                    throw UnsupportedOperationException("Method can handle only with Float, Int and Short")
                }
            }
            true
        } catch (e: TransportException) {
            false
        }
    }

    override fun readAllRegisters() {
        model.registers.values.forEach {
            readRegister(it)
        }
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

    override fun writeRequest(request: String) {}

    override fun getRegisterById(idRegister: String) = model.getRegisterById(idRegister)

    override fun checkResponsibility() {
        model.registers.values.firstOrNull()?.let {
            readRegister(it)
        }
    }

    fun setShowValue(type: Int) {
        toggleProgrammingMode()
        sleep(100)
        writeRegister(getRegisterById(AVEM4Model.SHOW_VALUE), type.toShort())
    }

    private fun toggleProgrammingMode() {
        val serialNumberRegister = getRegisterById(AVEM4Model.SERIAL_NUMBER)
        readRegister(serialNumberRegister)
        val serialNumber = serialNumberRegister.value.toInt()
//        writeRegister(serialNumberRegister, serialNumber)
        val bb = ByteBuffer.allocate(4).putInt(serialNumber).order(ByteOrder.BIG_ENDIAN)
        val registers = listOf(ModbusRegister(bb.getShort(2)), ModbusRegister(bb.getShort(0)))
        try {
            transactionWithAttempts {
                protocolAdapter.presetMultipleRegisters(
                    id,
                    getRegisterById(AVEM4Model.SERIAL_NUMBER).address,
                    registers
                )
            }
        } catch (_: Exception) {

        }
    }

    fun writeRuntimeKTR(ktr: Float) {
        writeRegister(getRegisterById(AVEM4Model.KTR_RUNTIME), ktr)
    }
}
