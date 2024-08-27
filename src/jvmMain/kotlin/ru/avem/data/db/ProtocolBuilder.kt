package ru.avem.data.db

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


object ProtocolBuilder: KoinComponent {

    val db by inject<DBManager>()

    var id = ""
    var name =  ""
    var type =  ""
    var operator = ""
    var time = ""
    var date = ""

    var specifiedP = ""
    var specifiedU = ""
    var specifiedI = ""
    var specifiedRPM = ""
    var specifiedIdleTime = ""
    var specifiedRunningTime = ""
    var specifiedMgrU = ""
    var specifiedViuU = ""
    var scheme = ""

    //MGR
    var u = ""
    var r15 = ""
    var r60 = ""
    var kABS = ""
    var mgrT = ""
    var mgrResult = ""

    //VIU
    var u_viu = ""
    var i_viu = ""
    var t_viu = ""
    var viuResult = ""

    //IKAS
    var r_uv_ikas = ""
    var r_vw_ikas = ""
    var r_wu_ikas = ""
    var calc_u_ikas = ""
    var calc_v_ikas = ""
    var calc_w_ikas = ""
    var deviation = ""
    var ikasResult = ""

    //MVZ HH
    var u_uv_hh = ""
    var u_vw_hh = ""
    var u_wu_hh = ""
    var i_u_hh = ""
    var i_v_hh = ""
    var i_w_hh = ""
    var cos_hh = ""
    var hhResult = ""

    //TL
    var u_set_tl = ""
    var u_izm_tl = ""
    var i_u_tl = ""
    var p_tl = ""
    var induction_tl = ""
    var p_steel_tl = ""
    var intensity_tl = ""
    var losses_tl = ""
    var tlResult = ""

    fun clear() {
//        this::class.memberProperties.forEach {
//            it.getter.call()
//        }

        id = ""
        name =  ""
        type =  ""
        operator = ""
        time = ""
        date = ""

        specifiedP = ""
        specifiedU = ""
        specifiedI = ""
        specifiedRPM = ""
        specifiedIdleTime = ""
        specifiedRunningTime = ""
        specifiedMgrU = ""
        specifiedViuU = ""
        scheme = ""

        //MGR
        u = ""
        r15 = ""
        r60 = ""
        kABS = ""
        mgrT = ""

        //IKAS
        r_uv_ikas = ""
        r_vw_ikas = ""
        r_wu_ikas = ""
        calc_u_ikas = ""
        calc_v_ikas = ""
        calc_w_ikas = ""
        deviation = ""
        ikasResult = ""

        //VIU
        u_viu = ""
        i_viu = ""
        t_viu = ""
        viuResult = ""

        //MVZ HH
        u_uv_hh = ""
        u_vw_hh = ""
        u_wu_hh = ""
        i_u_hh = ""
        i_v_hh = ""
        i_w_hh = ""
        cos_hh = ""
        hhResult = ""

        //TL
        u_set_tl = ""
        u_izm_tl = ""
        i_u_tl = ""
        p_tl = ""
        induction_tl = ""
        p_steel_tl = ""
        intensity_tl = ""
        losses_tl = ""
        tlResult = ""
    }

    fun fillProtocol (protocol: TestProtocol) {

        val t = db.getProtocolById(protocol)

        id = t.id.toString()
        name =  t.name
        type =  t.type.toString()
        operator = t.operator.toString()
        time = t.time.toString()
        date = t.date.toString()

        specifiedP = t.specifiedP.toString()
        specifiedU = t.specifiedU.toString()
        specifiedI = t.specifiedI.toString()
        specifiedRPM = t.specifiedRPM.toString()
        specifiedIdleTime = t.specifiedIdleTime.toString()
        specifiedRunningTime = t.specifiedRunningTime.toString()
        specifiedMgrU = t.specifiedMgrU.toString()
        specifiedViuU = t.specifiedViuU.toString()
        scheme = t.scheme.toString()

        //MGR
//        u = t.mgrU.toString()
//        r15 = t.mgrR15.toString()
//        r60 = t.mgrR60.toString()
//        kABS = t.mgrkABS.toString()
////        mgrT = t.mgrT.toString()
//        mgrResult = t.mgrResult.toString()
//
//        //VIU
//        u_viu = t.u_viu.toString()
//        i_viu = t.i_viu.toString()
//        t_viu = t.t_viu.toString()
//        viuResult = t.viuResult.toString()
//
//         //IKAS
//
//        r_uv_ikas = t.ikasRuv.toString()
//        r_vw_ikas = t.ikasRvw.toString()
//        r_wu_ikas = t.ikasRwu.toString()
//        calc_u_ikas = t.calc_u_ikas.toString()
//        calc_v_ikas = t.calc_v_ikas.toString()
//        calc_w_ikas = t.calc_w_ikas.toString()
//        deviation = t.deviation.toString()
//        ikasResult = t.ikasResult.toString()
//
//        //MVZ HH
//        u_uv_hh = t.u_uv_hh.toString()
//        u_vw_hh = t.u_vw_hh.toString()
//        u_wu_hh = t.u_wu_hh.toString()
//        i_u_hh = t.i_u_hh.toString()
//        i_v_hh = t.i_v_hh.toString()
//        i_w_hh = t.i_w_hh.toString()
//        cos_hh = t.cos_hh.toString()
//        hhResult = t.hhResult.toString()
//
//        //TL
//        u_set_tl = t.u_set_tl.toString()
//        u_izm_tl = t.u_izm_tl.toString()
//        i_u_tl = t.i_u_tl.toString()
//        p_tl = t.p_tl.toString()
//        induction_tl = t.induction_tl.toString()
//        p_steel_tl = t.p_steel_tl.toString()
//        intensity_tl = t.intensity_tl.toString()
//        losses_tl = t.losses_tl.toString()
//        tlResult = t.tlResult.toString()


    }

}
