package ru.avem.modules.tests

import kotlinx.coroutines.delay
import ru.avem.common.af
import ru.avem.data.db.TestItem
import ru.avem.data.models.TestObject
import java.util.*
import kotlin.math.abs

class TestIKAS : Test() {
    override suspend fun start() {
        initDevices()
        initAVEM4(vm)
        initAVEM7(vm)
        var resistanceAB = 0.0
        var resistanceBC = 0.0
        var resistanceCA = 0.0
        if (isRun) {
            resistanceAB = meas(1, vm.testItem)
            if (resistanceAB.isNaN()) {
                vm.testItem.r_uv_ikas.value = "Обрыв"
            } else {
                vm.testItem.r_uv_ikas.value = String.format(Locale.US, "%.3f", resistanceAB)
            }
        }
        if (isRun) {
            resistanceBC = meas(2, vm.testItem)
            if (resistanceBC.isNaN()) {
                vm.testItem.r_vw_ikas.value = "Обрыв"
            } else {
                vm.testItem.r_vw_ikas.value = String.format(Locale.US, "%.3f", resistanceBC)
            }
        }

        if (isRun) {
            resistanceCA = meas(3, vm.testItem)
            if (resistanceCA.isNaN()) {
                vm.testItem.r_wu_ikas.value = "Обрыв"
            } else {
                vm.testItem.r_wu_ikas.value = String.format(Locale.US, "%.3f", resistanceCA)
            }
        }

        calcRs(resistanceAB, resistanceBC, resistanceCA, vm.testItem)
    }

    private suspend fun meas(order: Int, testObject: TestObject): Double {
        pr102.ikasBa(false)
        pr102.ikasBc(false)
        pr102.ikasA(false)
        pr102.ikasC (false)
        var resistance: Double = Double.NaN
        delay(100)

        if (isRun) {
            when (order) {
                1 -> {
                    pr102.ikasA(true)
                    pr102.ikasBa(true)
                }
                2 -> {
                    pr102.ikasBc(true)
                    pr102.ikasC (true)
                }
                3 -> {
                    pr102.ikasA(true)
                    pr102.ikasC(true)
                }
            }
            delay(5000)
            val voltage = abs(testObject.ikas_v.value.toDouble())
            val current = abs(testObject.ikas_i.value.toDouble())
            appendToLog("v = $voltage --- i = $current")
            resistance = voltage / current

            if (current < 0.01) {
                resistance = Double.NaN
            }
        }

        if (isRun) {
            pr102.ikasBa(false)
            pr102.ikasBc(false)
            pr102.ikasA(false)
            pr102.ikasC (false)
            delay(500)
        }
        return resistance
    }

    private fun calcRs(
        resistanceAB: Double,
        resistanceBC: Double,
        resistanceCA: Double,
//        ti: TestItem,
        testObject: TestObject
    ) {
        val tempRatio = 0.00425

        if (testObject.r_uv_ikas.value == "Обрыв" ||
            testObject.r_vw_ikas.value == "Обрыв" ||
            testObject.r_wu_ikas.value == "Обрыв"
        ) {
            testObject.calc_u_ikas.value = "Обрыв"
            testObject.calc_v_ikas.value = "Обрыв"
            testObject.calc_w_ikas.value = "Обрыв"

        } else {
            if (testObject.selectedTI!!.scheme) {//TODO указать схему звезда
                testObject.calc_u_ikas.value = "%.3f".format(Locale.ENGLISH, ((resistanceCA + resistanceAB - resistanceBC) / 2.0))
                testObject.calc_v_ikas.value = "%.3f".format(Locale.ENGLISH, ((resistanceAB + resistanceBC - resistanceCA) / 2.0))
                testObject.calc_w_ikas.value = "%.3f".format(Locale.ENGLISH, ((resistanceBC + resistanceCA - resistanceAB) / 2.0))
            } else {
                testObject.calc_u_ikas.value =
                    "%.3f".format(
                        Locale.ENGLISH,
                        (2.0 * resistanceAB * resistanceBC / (resistanceAB + resistanceBC - resistanceCA) - (resistanceAB + resistanceBC - resistanceCA) / 2.0)
                    )
                testObject.calc_v_ikas.value =
                    "%.3f".format(
                        Locale.ENGLISH,
                        (2.0 * resistanceBC * resistanceCA / (resistanceBC + resistanceCA - resistanceAB) - (resistanceBC + resistanceCA - resistanceAB) / 2.0)
                    )
                testObject.calc_w_ikas.value =
                    "%.3f".format(
                        Locale.ENGLISH,
                        (2.0 * resistanceCA * resistanceAB / (resistanceCA + resistanceAB - resistanceBC) - (resistanceCA + resistanceAB - resistanceBC) / 2.0)
                    )
            }
            testObject.deviation.value =
                ((maxOf(testObject.calc_u_ikas.value, testObject.calc_v_ikas.value, testObject.calc_w_ikas.value).toDouble() - minOf(testObject.calc_u_ikas.value, testObject.calc_v_ikas.value, testObject.calc_w_ikas.value).toDouble())
                        / maxOf(testObject.calc_u_ikas.value, testObject.calc_v_ikas.value, testObject.calc_w_ikas.value).toDouble() * 100).af()

//        val rA = abs(testItem.calc_uv_ikas.value.toDouble())
//        val rB = abs(testItem.calc_vw_ikas.value.toDouble())
//        val rC = abs(testItem.calc_wu_ikas.value.toDouble())

//        val t = viewModel.tempAmb.value.toDoubleOrDefault(0.0)
//        val rtK = tempRatio // при 20°C
//        val rtT = 20.0

//        calcUV.value = "%.3f".format(Locale.ENGLISH, abs(rA / (1 + rtK * (t - rtT))))
//        calcVW.value = "%.3f".format(Locale.ENGLISH, abs(rB / (1 + rtK * (t - rtT))))
//        calcWU.value = "%.3f".format(Locale.ENGLISH, abs(rC / (1 + rtK * (t - rtT))))



        }
    }
}