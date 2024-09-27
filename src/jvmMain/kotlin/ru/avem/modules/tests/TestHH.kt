package ru.avem.modules.tests

import kotlinx.coroutines.delay
import ru.avem.common.af
import ru.avem.data.enums.LogType

class TestHH : Test() {
    override suspend fun start() {
        initDevices()
        var a_u = 0.0
        var b_u = 0.0
        var c_u = 0.0
        var a_i = 0.0
        var b_i = 0.0
        var c_i = 0.0

        if (isRun) {
            initPM130(vm)
            if (isRun) {
                pr102.km1(true)
                delay(500)
            }
            if (isRun) {
                pr102.i_max5(true)
                delay(500)
            }
            delay(5000)
            a_u = vm.u_uv.value.toDouble()
            b_u = vm.u_vw.value.toDouble()
            c_u = vm.u_wu.value.toDouble()
            a_i = vm.i_u.value.toDouble()
            b_i = vm.i_v.value.toDouble()
            c_i = vm.i_w.value.toDouble()
            var u_uv_res = a_u.toString()
            var u_vw_res = b_u.toString()
            var u_wu_res = c_u.toString()
            var i_u_res = a_i.toString()
            var i_v_res = b_i.toString()
            var i_w_res = c_i.toString()
            var cos_res = vm.cos.value

            vm.testItem.u_uv_hh.value = a_u.toString()
            vm.testItem.u_vw_hh.value = b_u.toString()
            vm.testItem.u_wu_hh.value = c_u.toString()
            vm.testItem.i_u_hh.value = a_i.toString()
            vm.testItem.i_v_hh.value = b_i.toString()
            vm.testItem.i_w_hh.value = c_i.toString()
//                vm.listTestItems[it].cos_hh.value = cos.value

            if (isRun) {
                pr102.i_max5(false)
                delay(500)
            }
            if (isRun) {
                pr102.km1(false)
                delay(500)
            }

            vm.testItem.i_deviation_hh.value = ((maxOf(a_i, b_i, c_i) - minOf(a_i, b_i, c_i)) / maxOf(a_i, b_i, c_i) * 100).af()
            if (vm.testItem.i_deviation_hh.value == "NaN") vm.testItem.i_deviation_hh.value = "0.0"
            vm.testItem.hhResult.value = if (vm.testItem.i_deviation_hh.value.toDouble() > vm.testItem.selectedTI!!.i_mz.toDouble()) {
                "Отклонение > ${vm.testItem.selectedTI!!.i_mz}"
                ""
            } else {
                "Успешно"
            }
            vm.u_uv.value = u_uv_res
            vm.u_vw.value = u_vw_res
            vm.u_wu.value = u_wu_res
            vm.i_u.value = i_u_res
            vm.i_v.value = i_v_res
            vm.i_w.value = i_w_res
            vm.cos.value = cos_res

        } else {
            isRun = false
            appendToLog("Испытание прервано", LogType.ERROR)
        }
    }
}