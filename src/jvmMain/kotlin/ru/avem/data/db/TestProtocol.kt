package ru.avem.data.db

import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.IntIdTable
import org.jetbrains.exposed.sql.Column
import ru.avem.data.db.TestProtocols.nullable
import kotlin.reflect.full.memberProperties

object TestProtocols : IntIdTable() {
//    fun a() {
//        this::class.memberProperties.forEach {
//            println(it.getter.call())
//        }
//    }

    var name = varchar("name", 64)
    var type = varchar("type", 64).nullable()
    var operator = varchar("operator", 256).nullable()
    var time = varchar("time", 64).nullable()
    var date = varchar("date", 64).nullable()

    val specifiedP = varchar("specifiedP", 64).nullable()
    var specifiedU = varchar("specifiedU", 64).nullable()
    val specifiedI = varchar("specifiedI", 64).nullable()
    val specifiedRPM = varchar("specifiedRPM", 64).nullable()
    val specifiedIdleTime = varchar("specifiedIdleTime", 64).nullable()
    var specifiedRunningTime = varchar("specifiedRunningTime", 64).nullable()
    val specifiedMgrU = varchar("specifiedMgrU", 64).nullable()
    val specifiedViuU = varchar("specifiedViuU", 64).nullable()
    val scheme = bool("scheme").nullable()

    //MGR мегер
    val mgrU_1 = varchar("U_1", 64).nullable()
    var mgrR15_1 = varchar("R15_1", 64).nullable()
    val mgrR60_1 = varchar("R60_1", 64).nullable()
    val mgrkABS_1 = varchar("kABS_1", 64).nullable()
    var mgrT_1 = varchar("mgrT_1", 64).nullable()
    val mgrU_2 = varchar("U_2", 64).nullable()
    var mgrR15_2 = varchar("R15_2", 64).nullable()
    val mgrR60_2 = varchar("R60_2", 64).nullable()
    val mgrkABS_2 = varchar("kABS_2", 64).nullable()
    var mgrT_2 = varchar("mgrT_2", 64).nullable()
    val mgrU_3 = varchar("U_3", 64).nullable()
    var mgrR15_3 = varchar("R15_3", 64).nullable()
    val mgrR60_3 = varchar("R60_3", 64).nullable()
    val mgrkABS_3 = varchar("kABS_3", 64).nullable()
    var mgrT_3 = varchar("mgrT_3", 64).nullable()
    var mgrResult_1 = varchar("mgrResult_1", 64).nullable()
    var mgrResult_2 = varchar("mgrResult_2", 64).nullable()
    var mgrResult_3 = varchar("mgrResult_3", 64).nullable()

    //IKAS Измерение сопротивления обмоток постоянному току в практически холодном состоянии

    val ikasRuv1 = varchar("Ruv1", 64).nullable()
    val ikasRvw1 = varchar("Rvw1", 64).nullable()
    val ikasRwu1 = varchar("Rwu1", 64).nullable()
    val ikasRuv2 = varchar("Ruv2", 64).nullable()
    val ikasRvw2 = varchar("Rvw2", 64).nullable()
    val ikasRwu2 = varchar("Rwu2", 64).nullable()
    val ikasRuv3 = varchar("Ruv3", 64).nullable()
    val ikasRvw3 = varchar("Rvw3", 64).nullable()
    val ikasRwu3 = varchar("Rwu3", 64).nullable()
    var ikasResult_1 = varchar("ikasResult_1", 64).nullable()
    var ikasResult_2 = varchar("ikasResult_2", 64).nullable()
    var ikasResult_3 = varchar("ikasResult_3", 64).nullable()

    //VIU Испытание изоляции обмоток относительно корпуса и между фазами на электрическую прочность

    var viuU_1 = varchar("viuU_1", 64).nullable()
    var viuI_1 = varchar("viuI_1", 64).nullable()
    var viuTime_1 = varchar("viuTime_1", 64).nullable()
    var viuU_2 = varchar("viuU_2", 64).nullable()
    var viuI_2 = varchar("viuI_2", 64).nullable()
    var viuTime_2 = varchar("viuTime_2", 64).nullable()
    var viuU_3 = varchar("viuU_3", 64).nullable()
    var viuI_3 = varchar("viuI_3", 64).nullable()
    var viuTime_3 = varchar("viuTime_3", 64).nullable()
    var viuResult_1 = varchar("viuResult_1", 64).nullable()
    var viuResult_2 = varchar("viuResult_2", 64).nullable()
    var viuResult_3 = varchar("viuResult_3", 64).nullable()

    //MV Испытание междувитковой изоляции

    val before_i_u_hh_1 = varchar("before_i_u_hh_1", 64).nullable()
    val before_i_v_hh_1 = varchar("before_i_v_hh_1", 64).nullable()
    val before_i_w_hh_1 = varchar("before_i_w_hh_1", 64).nullable()

    val before_i_u_hh_2 = varchar("before_i_u_hh_2", 64).nullable()
    val before_i_v_hh_2 = varchar("before_i_v_hh_2", 64).nullable()
    val before_i_w_hh_2 = varchar("before_i_w_hh_2", 64).nullable()

    val before_i_u_hh_3 = varchar("before_i_u_hh_3", 64).nullable()
    val before_i_v_hh_3 = varchar("before_i_v_hh_3", 64).nullable()
    val before_i_w_hh_3 = varchar("before_i_w_hh_3", 64).nullable()

    val after_i_u_hh_1 = varchar("after_i_u_hh_1", 64).nullable()
    val after_i_v_hh_1 = varchar("after_i_v_hh_1", 64).nullable()
    val after_i_w_hh_1 = varchar("after_i_w_hh_1", 64).nullable()

    val after_i_u_hh_2 = varchar("after_i_u_hh_2", 64).nullable()
    val after_i_v_hh_2 = varchar("after_i_v_hh_2", 64).nullable()
    val after_i_w_hh_2 = varchar("after_i_w_hh_2", 64).nullable()

    val after_i_u_hh_3 = varchar("after_i_u_hh_3", 64).nullable()
    val after_i_v_hh_3 = varchar("after_i_v_hh_3", 64).nullable()
    val after_i_w_hh_3 = varchar("after_i_w_hh_3", 64).nullable()

    var before_u_uv_mv_1 = varchar("before_u_uv_mv_1", 64).nullable()
    var before_u_vw_mv_1 = varchar("before_u_vw_mv_1", 64).nullable()
    var before_u_wu_mv_1 = varchar("before_u_wu_mv_1", 64).nullable()

    var before_u_uv_mv_2 = varchar("before_u_uv_mv_2", 64).nullable()
    var before_u_vw_mv_2 = varchar("before_u_vw_mv_2", 64).nullable()
    var before_u_wu_mv_2 = varchar("before_u_wu_mv_2", 64).nullable()

    var before_u_uv_mv_3 = varchar("before_u_uv_mv_3", 64).nullable()
    var before_u_vw_mv_3 = varchar("before_u_vw_mv_3", 64).nullable()
    var before_u_wu_mv_3 = varchar("before_u_wu_mv_3", 64).nullable()

    var after_u_uv_mv_1 = varchar("after_u_uv_mv_1", 64).nullable()
    var after_u_vw_mv_1 = varchar("after_u_vw_mv_1", 64).nullable()
    var after_u_wu_mv_1 = varchar("after_u_wu_mv_1", 64).nullable()

    var after_u_uv_mv_2 = varchar("after_u_uv_mv_2", 64).nullable()
    var after_u_vw_mv_2 = varchar("after_u_vw_mv_2", 64).nullable()
    var after_u_wu_mv_2 = varchar("after_u_wu_mv_2", 64).nullable()

    var after_u_uv_mv_3 = varchar("after_u_uv_mv_3", 64).nullable()
    var after_u_vw_mv_3 = varchar("after_u_vw_mv_3", 64).nullable()
    var after_u_wu_mv_3 = varchar("after_u_wu_mv_3", 64).nullable()

    var result_a_mv_1 = varchar("result_a_mv_1", 64).nullable()
    var result_b_mv_1 = varchar("result_b_mv_1", 64).nullable()
    var result_c_mv_1 = varchar("result_c_mv_1", 64).nullable()

    var result_a_mv_2 = varchar("result_a_mv_2", 64).nullable()
    var result_b_mv_2 = varchar("result_b_mv_2", 64).nullable()
    var result_c_mv_2 = varchar("result_c_mv_2", 64).nullable()

    var result_a_mv_3 = varchar("result_a_mv_3", 64).nullable()
    var result_b_mv_3 = varchar("result_b_mv_3", 64).nullable()
    var result_c_mv_3 = varchar("result_c_mv_3", 64).nullable()



    //HH Определение тока и потерь ХХ

    var u_uv_hh_1 = varchar("u_uv_hh_1", 64).nullable()
    var u_vw_hh_1 = varchar("u_vw_hh_1", 64).nullable()
    var u_wu_hh_1 = varchar("u_wu_hh_1", 64).nullable()
    var i_u_hh_1 = varchar("i_u_hh_1", 64).nullable()
    var i_v_hh_1 = varchar("i_v_hh_1", 64).nullable()
    var i_w_hh_1 = varchar("i_w_hh_1", 64).nullable()
    var u_uv_hh_2 = varchar("u_uv_hh_2", 64).nullable()
    var u_vw_hh_2 = varchar("u_vw_hh_2", 64).nullable()
    var u_wu_hh_2 = varchar("u_wu_hh_2", 64).nullable()
    var i_u_hh_2 = varchar("i_u_hh_2", 64).nullable()
    var i_v_hh_2 = varchar("i_v_hh_2", 64).nullable()
    var i_w_hh_2 = varchar("i_w_hh_2", 64).nullable()
    var u_uv_hh_3 = varchar("u_uv_hh_3", 64).nullable()
    var u_vw_hh_3 = varchar("u_vw_hh_3", 64).nullable()
    var u_wu_hh_3 = varchar("u_wu_hh_3", 64).nullable()
    var i_u_hh_3 = varchar("i_u_hh_3", 64).nullable()
    var i_v_hh_3 = varchar("i_v_hh_3", 64).nullable()
    var i_w_hh_3 = varchar("i_w_hh_3", 64).nullable()


}

class TestProtocol(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<TestProtocol>(TestProtocols)

    var name by TestProtocols.name
    var type by TestProtocols.type
    var operator by TestProtocols.operator
    var time by TestProtocols.time
    var date by TestProtocols.date

    var specifiedP by TestProtocols.specifiedP
    var specifiedU by TestProtocols.specifiedU
    var specifiedI by TestProtocols.specifiedI
    var specifiedRPM by TestProtocols.specifiedRPM
    var specifiedIdleTime by TestProtocols.specifiedIdleTime
    var specifiedRunningTime by TestProtocols.specifiedRunningTime
    var specifiedMgrU by TestProtocols.specifiedMgrU
    var specifiedViuU by TestProtocols.specifiedViuU
    var scheme by TestProtocols.scheme

    //MGR
    val mgrU_1 by TestProtocols.mgrU_1
    var mgrR15_1 by TestProtocols.mgrR15_1
    val mgrR60_1 by TestProtocols.mgrR60_1
    val mgrkABS_1 by TestProtocols.mgrkABS_1
    var mgrT_1 by TestProtocols.mgrT_1
    val mgrU_2 by TestProtocols.mgrU_2
    var mgrR15_2 by TestProtocols.mgrR15_2
    val mgrR60_2 by TestProtocols.mgrR60_2
    val mgrkABS_2 by TestProtocols.mgrkABS_2
    var mgrT_2 by TestProtocols.mgrT_2
    val mgrU_3 by TestProtocols.mgrU_3
    var mgrR15_3 by TestProtocols.mgrR15_3
    val mgrR60_3 by TestProtocols.mgrR60_3
    val mgrkABS_3 by TestProtocols.mgrkABS_3
    var mgrT_3 by TestProtocols.mgrT_3
    var mgrResult_1 by TestProtocols.mgrResult_1
    var mgrResult_2 by TestProtocols.mgrResult_2
    var mgrResult_3 by TestProtocols.mgrResult_3


    //IKAS
    val ikasRuv1 by TestProtocols.ikasRuv1
    val ikasRvw1 by TestProtocols.ikasRvw1
    val ikasRwu1 by TestProtocols.ikasRwu1
    val ikasRuv2 by TestProtocols.ikasRuv2
    val ikasRvw2 by TestProtocols.ikasRvw2
    val ikasRwu2 by TestProtocols.ikasRwu2
    val ikasRuv3 by TestProtocols.ikasRuv3
    val ikasRvw3 by TestProtocols.ikasRvw3
    val ikasRwu3 by TestProtocols.ikasRwu3
    var ikasResult_1 by TestProtocols.ikasResult_1
    var ikasResult_2 by TestProtocols.ikasResult_2
    var ikasResult_3 by TestProtocols.ikasResult_3

    //VIU

    var viuU_1 by TestProtocols.viuU_1
    var viuI_1 by TestProtocols.viuI_1
    var viuTime_1 by TestProtocols.viuTime_1
    var viuU_2 by TestProtocols.viuU_2
    var viuI_2 by TestProtocols.viuI_2
    var viuTime_2 by TestProtocols.viuTime_2
    var viuU_3 by TestProtocols.viuU_3
    var viuI_3 by TestProtocols.viuI_3
    var viuTime_3 by TestProtocols.viuTime_3
    var viuResult_1 by TestProtocols.viuResult_1
    var viuResult_2 by TestProtocols.viuResult_2
    var viuResult_3 by TestProtocols.viuResult_3

    //MV
    val before_i_u_hh_1 by TestProtocols.before_i_u_hh_1
    val before_i_v_hh_1 by TestProtocols.before_i_v_hh_1
    val before_i_w_hh_1 by TestProtocols.before_i_w_hh_1
    val before_i_u_hh_2 by TestProtocols.before_i_u_hh_2
    val before_i_v_hh_2 by TestProtocols.before_i_v_hh_2
    val before_i_w_hh_2 by TestProtocols.before_i_w_hh_2
    val before_i_u_hh_3 by TestProtocols.before_i_u_hh_3
    val before_i_v_hh_3 by TestProtocols.before_i_v_hh_3
    val before_i_w_hh_3 by TestProtocols.before_i_w_hh_3
    val after_i_u_hh_1 by TestProtocols.after_i_u_hh_1
    val after_i_v_hh_1 by TestProtocols.after_i_v_hh_1
    val after_i_w_hh_1 by TestProtocols.after_i_w_hh_1
    val after_i_u_hh_2 by TestProtocols.after_i_u_hh_2
    val after_i_v_hh_2 by TestProtocols.after_i_v_hh_2
    val after_i_w_hh_2 by TestProtocols.after_i_w_hh_2
    val after_i_u_hh_3 by TestProtocols.after_i_u_hh_3
    val after_i_v_hh_3 by TestProtocols.after_i_v_hh_3
    val after_i_w_hh_3 by TestProtocols.after_i_w_hh_3
    var before_u_uv_mv_1 by TestProtocols.before_u_uv_mv_1
    var before_u_vw_mv_1 by TestProtocols.before_u_vw_mv_1
    var before_u_wu_mv_1 by TestProtocols.before_u_wu_mv_1
    var before_u_uv_mv_2 by TestProtocols.before_u_uv_mv_2
    var before_u_vw_mv_2 by TestProtocols.before_u_vw_mv_2
    var before_u_wu_mv_2 by TestProtocols.before_u_wu_mv_2
    var before_u_uv_mv_3 by TestProtocols.before_u_uv_mv_3
    var before_u_vw_mv_3 by TestProtocols.before_u_vw_mv_3
    var before_u_wu_mv_3 by TestProtocols.before_u_wu_mv_3
    var after_u_uv_mv_1 by TestProtocols.after_u_uv_mv_1
    var after_u_vw_mv_1 by TestProtocols.after_u_vw_mv_1
    var after_u_wu_mv_1 by TestProtocols.after_u_wu_mv_1
    var after_u_uv_mv_2 by TestProtocols.after_u_uv_mv_2
    var after_u_vw_mv_2 by TestProtocols.after_u_vw_mv_2
    var after_u_wu_mv_2 by TestProtocols.after_u_wu_mv_2
    var after_u_uv_mv_3 by TestProtocols.after_u_uv_mv_3
    var after_u_vw_mv_3 by TestProtocols.after_u_vw_mv_3
    var after_u_wu_mv_3 by TestProtocols.after_u_wu_mv_3
    var result_a_mv_1 by TestProtocols.result_a_mv_1
    var result_b_mv_1 by TestProtocols.result_b_mv_1
    var result_c_mv_1 by TestProtocols.result_c_mv_1
    var result_a_mv_2 by TestProtocols.result_a_mv_2
    var result_b_mv_2 by TestProtocols.result_b_mv_2
    var result_c_mv_2 by TestProtocols.result_c_mv_2
    var result_a_mv_3 by TestProtocols.result_a_mv_3
    var result_b_mv_3 by TestProtocols.result_b_mv_3
    var result_c_mv_3 by TestProtocols.result_c_mv_3

//HH
    var u_uv_hh_1 by TestProtocols.u_uv_hh_1
    var u_vw_hh_1 by TestProtocols.u_vw_hh_1
    var u_wu_hh_1 by TestProtocols.u_wu_hh_1
    var i_u_hh_1 by TestProtocols.i_u_hh_1
    var i_v_hh_1 by TestProtocols.i_v_hh_1
    var i_w_hh_1 by TestProtocols.i_w_hh_1
    var u_uv_hh_2 by TestProtocols.u_uv_hh_2
    var u_vw_hh_2 by TestProtocols.u_vw_hh_2
    var u_wu_hh_2 by TestProtocols.u_wu_hh_2
    var i_u_hh_2 by TestProtocols.i_u_hh_2
    var i_v_hh_2 by TestProtocols.i_v_hh_2
    var i_w_hh_2 by TestProtocols.i_w_hh_2
    var u_uv_hh_3 by TestProtocols.u_uv_hh_3
    var u_vw_hh_3 by TestProtocols.u_vw_hh_3
    var u_wu_hh_3 by TestProtocols.u_wu_hh_3
    var i_u_hh_3 by TestProtocols.i_u_hh_3
    var i_v_hh_3 by TestProtocols.i_v_hh_3
    var i_w_hh_3 by TestProtocols.i_w_hh_3



    override fun toString() = name

}