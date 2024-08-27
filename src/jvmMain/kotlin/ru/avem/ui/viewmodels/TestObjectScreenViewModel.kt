package ru.avem.ui.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.avem.data.db.DBManager
import ru.avem.data.models.ParamOI

class TestObjectScreenViewModel: ViewModel(), KoinComponent {

    val db by inject<DBManager>()

    val dialogWindow = mutableStateOf(false)
    val dialogText = mutableStateOf("Готово")
    var typesTI = db.getAllTI().ifEmpty { listOf("") }
    var selectedTI = mutableStateOf(typesTI.first())

    val scope = CoroutineScope(Dispatchers.Main)

//    var typesSteel = db.getAllSteelMarks().ifEmpty { listOf("") }
//    var selectedSteel = mutableStateOf(typesSteel.first())

    var addNewTI: MutableState<Boolean> = mutableStateOf(false)

    var name = mutableStateOf("")
    var scheme = mutableStateOf(true)
    var isolation = mutableStateOf(false)
    var power = mutableStateOf("")
    var u_linear = mutableStateOf("")
    var i = mutableStateOf("")
    var i_viu = mutableStateOf("")
    var i_mz = mutableStateOf("")
    var u_viu = mutableStateOf("")
    var u_mgr = mutableStateOf("")
    var t_viu = mutableStateOf("")
    var t_hh = mutableStateOf("")
    var t_mv = mutableStateOf("")
    var r_max = mutableStateOf("")
    var r_min = mutableStateOf("")
    var r20_max = mutableStateOf("")
    var r20_min = mutableStateOf("")
    var t = mutableStateOf("")
    var d_inside = mutableStateOf("")
    var d_outer = mutableStateOf("")
    var stator_length = mutableStateOf("")
    var height_slot = mutableStateOf("")
    var material = mutableStateOf(false)

    val paramsList = mutableStateListOf(
        ParamOI("Мощность, кВт", power, 1, 200),
        ParamOI("Напряжение линейное, В", u_linear, 380, 400),
        ParamOI("Ток, А", i, 1, 100),
        ParamOI("Напряжение ВИУ, В", u_viu, 0, 3000),
        ParamOI("Напряжение испытания мегаомметром, В", u_mgr, 500, 2500),
        ParamOI("Сопротивление изоляции максимальное, МОм", r_max, 50, 200000),
        ParamOI("Сопротивление изоляции минимальное, МОм", r_min, 50, 200000),
        ParamOI("Сопротивление фазы статора при 20°С максимальное, Ом", r20_max, 0, 999999999),
        ParamOI("Сопротивление фазы статора при 20°С минимальное, Ом", r20_min, 0, 99999999),
        ParamOI("Время испытания ВИУ, сек", t_viu, 0, 3600),
//        ParamOI("Время испытания ХХ, мин", t_hh, 0, 999999999),
        ParamOI("Время испытания МВЗ, сек", t_mv, 0, 60),
        ParamOI("Допустимый ток утечки ВИУ, мА", i_viu, 0, 240),
        ParamOI("Допустимое отклонение токов при МВЗ, %", i_mz, 0, 20),

        ParamOI("Внутренний диаметр статора, мм", d_inside, 0, 9999),
        ParamOI("Внешний диаметр статора, мм", d_outer, 0, 9999),
        ParamOI("Длинна пакета статора, мм", stator_length, 0, 9999),
        ParamOI("Высота спинки, мм", height_slot, 0, 9999),
    )

    fun openDialogAndUpdate (text: String) {
        scope.launch {
            dialogText.value = text
            dialogWindow.value = true
            typesTI = DBManager.getAllTI().ifEmpty { listOf("") }
            selectedTI.value = typesTI.first()
            delay(1500)
            dialogWindow.value = false
        }
    }

    fun initTestTI () {
        scope.launch {
            addNewTI.value = false
            val ti = DBManager.getTI(selectedTI.value)
            name.value = ti.name
            scheme.value = ti.scheme
            power.value = ti.power
            u_linear.value = ti.u_linear
            i.value = ti.i
            i_viu.value = ti.i_viu
            i_mz.value = ti.i_mz
            u_viu.value = ti.u_viu
            u_mgr.value = ti.u_mgr
            t_viu.value = ti.t_viu
            t_mv.value = ti.t_mv
            r_max.value = ti.r_max
            r_min.value = ti.r_min
            r20_max.value = ti.r20_max
            r20_min.value = ti.r20_min
//            selectedSteel.value = ti.steel_mark
//            isolation.value = ti.isolation
//            d_inside.value = ti.d_inside
//            d_outer.value = ti.d_outer
//            stator_length.value = ti.stator_length
//            height_slot.value = ti.height_slot
//            material.value = ti.material
        }
    }

    fun clearFields() {
        scope.launch {
            name.value = ""
            scheme.value = false
            power.value = ""
            u_linear.value = ""
            i.value = ""
            i_viu.value = ""
            i_mz.value = ""
            u_viu.value = ""
            u_mgr.value = ""
            t_viu.value = ""
            t_hh.value = ""
            t_mv.value = ""
            r_max.value = ""
            r_min.value = ""
            r20_max.value = ""
            r20_min.value = ""
            t.value = ""
//            selectedSteel.value = "2013"
//            isolation.value = false
//            d_inside.value = ""
//            d_outer.value = ""
//            stator_length.value = ""
//            height_slot.value = ""
//            material.value = false
        }
    }

    fun deleteTI () {
        scope.launch {
            DBManager.deleteTestItemById(selectedTI.value)
        }
    }

    fun addNewTI () {
        scope.launch {
            if (!addNewTI.value) {
                db.deleteTestItemById(selectedTI.value)
            }
            db.addTI(
                name.value,
                scheme.value,
                power.value,
                u_linear.value,
                i.value,
                i_viu.value,
                i_mz.value,
                u_viu.value,
                u_mgr.value,
                t_viu.value,
//                t_hh.value,
                t_mv.value,
                r_max.value,
                r_min.value,
                r20_max.value,
                r20_min.value,
                r20_min.value,
                r20_min.value
//                isolation.value,
//                d_inside.value,
//                d_outer.value,
//                stator_length.value,
//                height_slot.value,
//                material.value
            )
            addNewTI.value = false
        }
    }
}