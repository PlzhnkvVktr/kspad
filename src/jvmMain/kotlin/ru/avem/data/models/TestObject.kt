package ru.avem.data.models

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import ru.avem.data.db.TestItem

data class TestObject(
    val name: MutableState<String> = mutableStateOf(""),

    var selectedTI: TestItem? = null,

    //mgr
    var specifiedMgrU: MutableState<String> = mutableStateOf(""),
    val mgrU: MutableState<String> = mutableStateOf(""),
    val r15: MutableState<String> = mutableStateOf(""),
    val r60: MutableState<String> = mutableStateOf(""),
    val kABS: MutableState<String> = mutableStateOf(""),
    val timeMGR: MutableState<String> = mutableStateOf(""),
    val res_mgr: MutableState<String> = mutableStateOf(""),

    //viu
    var specifiedU_viu: MutableState<String> = mutableStateOf(""),
    var specifiedI_viu: MutableState<String> = mutableStateOf(""),
    var specifiedT_viu: MutableState<String> = mutableStateOf(""),
    var u_viu: MutableState<String> = mutableStateOf(""),
    var i_viu: MutableState<String> = mutableStateOf(""),
    var t_viu: MutableState<String> = mutableStateOf(""),
    var res_viu: MutableState<String> = mutableStateOf(""),

    //ikas
    var ikas_i: MutableState<String> = mutableStateOf(""),
    var ikas_v: MutableState<String> = mutableStateOf(""),
    var r_uv_ikas: MutableState<String> = mutableStateOf(""),
    var r_vw_ikas: MutableState<String> = mutableStateOf(""),
    var r_wu_ikas: MutableState<String> = mutableStateOf(""),
    var calc_u_ikas: MutableState<String> = mutableStateOf(""),
    var calc_v_ikas: MutableState<String> = mutableStateOf(""),
    var calc_w_ikas: MutableState<String> = mutableStateOf(""),
    var deviation: MutableState<String> = mutableStateOf(""),
    val res_ikas: MutableState<String> = mutableStateOf(""),

    //hh mvz
//    var u_uv_hh: MutableState<String> = mutableStateOf(""),
//    var u_vw_hh: MutableState<String> = mutableStateOf(""),
//    var u_wu_hh: MutableState<String> = mutableStateOf(""),
//    var i_u_hh: MutableState<String> = mutableStateOf(""),
//    var i_v_hh: MutableState<String> = mutableStateOf(""),
//    var i_w_hh: MutableState<String> = mutableStateOf(""),
    var u_uv_hh: MutableState<String> = mutableStateOf(""),
    var u_vw_hh: MutableState<String> = mutableStateOf(""),
    var u_wu_hh: MutableState<String> = mutableStateOf(""),
    var i_u_hh: MutableState<String> = mutableStateOf(""),
    var i_v_hh: MutableState<String> = mutableStateOf(""),
    var i_w_hh: MutableState<String> = mutableStateOf(""),
    var cos_hh: MutableState<String> = mutableStateOf(""),
    var hhResult: MutableState<String> = mutableStateOf(""),
    var i_deviation_hh: MutableState<String> = mutableStateOf(""),
    var hh_result: MutableState<String> = mutableStateOf(""),

    //mv
//    var u_uv_mv: MutableState<String> = mutableStateOf(""),
//    var u_vw_mv: MutableState<String> = mutableStateOf(""),
//    var u_wu_mv: MutableState<String> = mutableStateOf(""),
//    var before_u_uv_mv: MutableState<String> = mutableStateOf(""),
//    var before_u_vw_mv: MutableState<String> = mutableStateOf(""),
//    var before_u_wu_mv: MutableState<String> = mutableStateOf(""),
//    var after_u_uv_mv: MutableState<String> = mutableStateOf(""),
//    var after_u_vw_mv: MutableState<String> = mutableStateOf(""),
//    var after_u_wu_mv: MutableState<String> = mutableStateOf(""),
//    var i_v_mv: MutableState<String> = mutableStateOf(""),
//    var i_w_mv: MutableState<String> = mutableStateOf(""),
//    var i_u_mv: MutableState<String> = mutableStateOf(""),
//    var before_i_u_mv: MutableState<String> = mutableStateOf(""),
//    var before_i_v_mv: MutableState<String> = mutableStateOf(""),
//    var before_i_w_mv: MutableState<String> = mutableStateOf(""),
//    var after_i_u_mv: MutableState<String> = mutableStateOf(""),
//    var after_i_v_mv: MutableState<String> = mutableStateOf(""),
//    var after_i_w_mv: MutableState<String> = mutableStateOf(""),

    //tl
//    val u_set_tl: MutableState<String> = mutableStateOf(""),
//    val u_izm_tl: MutableState<String> = mutableStateOf(""),
//    val i_u_tl: MutableState<String> = mutableStateOf(""),
//    val p_tl: MutableState<String> = mutableStateOf(""),
//    val induction_tl: MutableState<String> = mutableStateOf(""),
//    val p_steel_tl: MutableState<String> = mutableStateOf(""),
//    val intensity_tl: MutableState<String> = mutableStateOf(""),
//    val losses_tl: MutableState<String> = mutableStateOf(""),
//    val res_tl: MutableState<String> = mutableStateOf(""),

)



