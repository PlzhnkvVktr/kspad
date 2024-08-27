package ru.avem.data.db

import androidx.compose.ui.res.useResource
import org.apache.poi.ss.usermodel.CellType
import org.apache.poi.xssf.usermodel.XSSFWorkbook
//import ru.avem.common.ProtocolBuilder
//import ru.avem.data.enums.TestEnum
//import ru.avem.utils.copyFileFromStream
import java.io.*
import java.nio.file.Files
import java.nio.file.Paths

val sp = File.separatorChar

fun copyFileFromStream(_inputStream: InputStream, dest: File) {
    _inputStream.use { inputStream ->
        try {
            val fileOutputStream = FileOutputStream(dest)
            val buffer = ByteArray(1024)
            var length = inputStream.read(buffer)
            while (length > 0) {
                fileOutputStream.write(buffer, 0, length)
                length = inputStream.read(buffer)
            }
        } catch (_: FileNotFoundException) {
        }
    }
}
fun saveProtocolAsWorkbook(protocol: ProtocolBuilder, path: String = "cfg/lastOpened.xlsx") {
    if(!File("cfg").exists()){
        Files.createDirectories(Paths.get("cfg"))
    }
    val template = File(path)
    if (File("cfg\\protocol.xlsx").exists()) {
        copyFileFromStream(File("cfg/protocol.xlsx").inputStream(), template)
    } else {
        useResource("protocol.xlsx") {
            copyFileFromStream(it, File("cfg/protocol.xlsx"))
        }
        copyFileFromStream(File("cfg/protocol.xlsx").inputStream(), template)
    }

    try {
        XSSFWorkbook(template).use { wb ->
            val sheet = wb.getSheetAt(0)
            for (iRow in 0 until 150) {
                val row = sheet.getRow(iRow)
                if (row != null) {
                    for (iCell in 0 until 150) {
                        val cell = row.getCell(iCell)
                        if (cell != null && (cell.cellType == CellType.STRING)) {

                            
                            when (cell.stringCellValue) {
                                "#TESTITEM_NAME#" -> cell.setCellValue(protocol.name)

                                "#IDTEST#" -> cell.setCellValue(protocol.id)
                                "#OPERATOR#" -> cell.setCellValue(protocol.operator)
                                "#V1#" -> cell.setCellValue(("vibroJ"))
                                "#V2#" -> cell.setCellValue(("vibroF"))
                                "#T1#" -> cell.setCellValue(("t"))
                                "#T2#" -> cell.setCellValue(("tI"))
//                                "#VIUNAME#" -> cell.setCellValue(TestEnum.nameVIU.testName)
//                                "#MGRNAME#" -> cell.setCellValue(TestEnum.nameMGR.testName)
//                                "#IKASNAME#" -> cell.setCellValue(TestEnum.nameIKAS.testName)
//                                "#HHNAME#" -> cell.setCellValue(TestEnum.nameHHandMVZ.testName)
//                                "#TLNAME#" -> cell.setCellValue(TestEnum.nameTL.testName)
                                "#RESULT#" -> cell.setCellValue(("res"))
                                "#BEFORE#" -> cell.setCellValue(("values before"))
                                "#AFTER#" -> cell.setCellValue(("values after"))
                                "#DEVIATION#" -> cell.setCellValue(("deviation"))
                                "#STATOR#" -> cell.setCellValue(("stator"))
                                "#ROTOR#" -> cell.setCellValue(("rotor"))
                                "#OPERATORTITLE#" -> cell.setCellValue(("Operator"))
                                "#DATETITLE#" -> cell.setCellValue(("Date"))
                                "#TIMETITLE#" -> cell.setCellValue(("Time"))
                                "#STARTPARAM#" -> cell.setCellValue(("set values"))
                                "#RECOMENDATION#" -> cell.setCellValue(("recommendations"))

                                "#FN#" -> cell.setCellValue(("Factory number"))
                                "#TO#" -> cell.setCellValue(("test object protocol"))
                                "#TT#" -> cell.setCellValue(("tt"))
                                "#TE#" -> cell.setCellValue(("Test equipment"))
                                "#PN#" -> cell.setCellValue(("Protocol number"))
                                "#RSM#" -> cell.setCellValue(("Rotation speed"))
                                "#DU#" -> cell.setCellValue(("d Iu"))
                                "#DV#" -> cell.setCellValue(("d Iv"))
                                "#DW#" -> cell.setCellValue(("d Iw"))

                                "#DATE#" -> cell.setCellValue(protocol.date)
                                "#TIME#" -> cell.setCellValue(protocol.time)
                                "#SERIAL#" -> cell.setCellValue(protocol.type)
                                "#P2#" -> cell.setCellValue(protocol.specifiedP)
                                "#UN#" -> cell.setCellValue(protocol.specifiedU)
                                "#IN#" -> cell.setCellValue(protocol.specifiedI)
                                "#NASYNC#" -> cell.setCellValue(protocol.specifiedRPM)
                                "#SCHEMETITLE#" -> cell.setCellValue(("scheme"))
                                "#SCHEME#" -> cell.setCellValue(if (protocol.scheme.toBoolean()) "λ" else "∆")
                                //MGR
//                                "#MGRNAME#" -> cell.setCellValue(protocol.R15)




                                "#MGRU#" -> cell.setCellValue(protocol.specifiedMgrU)
                                "#MGRR15#" -> cell.setCellValue(protocol.r15)
                                "#MGRR60#" -> cell.setCellValue(protocol.r60)
                                "#MGRKABS#" -> cell.setCellValue(protocol.kABS)
                                "#MGRTEMP#" -> cell.setCellValue(protocol.mgrT)
                                "#MGRRESULT#" -> cell.setCellValue(protocol.mgrResult)

                                //VIU
                                "#VIUU#" -> cell.setCellValue(protocol.u_viu)
                                "#VIUI#" -> cell.setCellValue(protocol.i_viu)
                                "#VIUTIME#" -> cell.setCellValue(protocol.t_viu)
                                "#VIURESULT#" -> cell.setCellValue(protocol.viuResult)
                                //IKAS
                                "#IKASDEV#" -> cell.setCellValue(protocol.deviation)
                                "#IKASR1#" -> cell.setCellValue(protocol.r_uv_ikas)
                                "#IKASR2#" -> cell.setCellValue(protocol.r_vw_ikas)
                                "#IKASR3#" -> cell.setCellValue(protocol.r_wu_ikas)
                                "#IKASR11#" -> cell.setCellValue(protocol.calc_u_ikas)
                                "#IKASR21#" -> cell.setCellValue(protocol.calc_v_ikas)
                                "#IKASR31#" -> cell.setCellValue(protocol.calc_w_ikas)
                                "#IKASRESULT#" -> cell.setCellValue(protocol.ikasResult)

                                //HH
                                "#HHUAB#" -> cell.setCellValue(protocol.u_uv_hh)
                                "#HHUBC#" -> cell.setCellValue(protocol.u_vw_hh)
                                "#HHUCA#" -> cell.setCellValue(protocol.u_wu_hh)
//                                "#HHUOV#" -> cell.setCellValue(protocol.hhUOV)
                                "#HHIA#" -> cell.setCellValue(protocol.i_u_hh)
                                "#HHIB#" -> cell.setCellValue(protocol.i_v_hh)
                                "#HHIC#" -> cell.setCellValue(protocol.i_w_hh)
//                                "#HHIOV#" -> cell.setCellValue(protocol.hhIOV)
//                                "#HHTEMPTI#" -> cell.setCellValue(protocol.hhTempTI)
//                                "#HHTEMPAMB#" -> cell.setCellValue(protocol.hhTempAmb)
//                                "#HHSPEED#" -> cell.setCellValue(protocol.hhSpeed)
//                                "#HHVIBRO1#" -> cell.setCellValue(protocol.hhVibro1)
//                                "#HHVIBRO2#" -> cell.setCellValue(protocol.hhVibro2)
//                                "#HHTIME#" -> cell.setCellValue(protocol.hhTime)
//                                "#HHP1#" -> cell.setCellValue(protocol.hhP1)
                                "#HHCOS#" -> cell.setCellValue(protocol.cos_hh)
                                "#HHRESULT#" -> cell.setCellValue(protocol.hhResult)


                                //TL
                                "#TLUSET#" -> cell.setCellValue(protocol.u_set_tl)
                                "#TLUIZM#" -> cell.setCellValue(protocol.u_izm_tl)
                                "#TLUI#" -> cell.setCellValue(protocol.i_u_tl)
                                "#TLP#" -> cell.setCellValue(protocol.p_tl)
                                "#TLIND#" -> cell.setCellValue(protocol.induction_tl)
                                "#TLP#" -> cell.setCellValue(protocol.p_steel_tl)
                                "#TLINT#" -> cell.setCellValue(protocol.intensity_tl)
                                "#TLLOS#" -> cell.setCellValue(protocol.losses_tl)

//                                "#TLUCA2#" -> cell.setCellValue(protocol.trUCA2)
//                                "#TLCALCUAB#" -> cell.setCellValue(protocol.trCalcUAB)
//                                "#TLCALCUBC#" -> cell.setCellValue(protocol.trCalcUBC)
//                                "#TLCALCUCA#" -> cell.setCellValue(protocol.trCalcUCA)
                                "#TLRESULT#" -> cell.setCellValue(protocol.tlResult)

//                                "#TRUOV#" -> cell.setCellValue(protocol.trUOV)
//                                "#TRIA#" -> cell.setCellValue(protocol.trIA)
//                                "#TRIB#" -> cell.setCellValue(protocol.trIB)
//                                "#TRIC#" -> cell.setCellValue(protocol.trIC)
//                                "#TRIOV#" -> cell.setCellValue(protocol.trIOV)
//                                "#TRTEMPTI#" -> cell.setCellValue(protocol.trTempTI)
//                                "#TRTEMPAMB#" -> cell.setCellValue(protocol.trTempAmb)
//                                "#TRSPEED#" -> cell.setCellValue(protocol.trSpeed)
//                                "#TRVIBRO1#" -> cell.setCellValue(protocol.trVibro1)
//                                "#TRVIBRO2#" -> cell.setCellValue(protocol.trVibro2)
////                                "#TRTIME#" -> cell.setCellValue(protocol.trTime)
//                                "#TRP1#" -> cell.setCellValue(protocol.trP1)
//                                "#TRCOS#" -> cell.setCellValue(protocol.trCos)
//                                //KZ
//                                "#KZNAME#" -> cell.setCellValue(protocol.R15)
//                                "#KZUAB#" -> cell.setCellValue(protocol.kzUAB)
//                                "#KZUBC#" -> cell.setCellValue(protocol.kzUBC)
//                                "#KZUCA#" -> cell.setCellValue(protocol.kzUCA)
//                                "#KZUOV#" -> cell.setCellValue(protocol.kzUOV)
//                                "#KZIA#" -> cell.setCellValue(protocol.kzIA)
//                                "#KZIB#" -> cell.setCellValue(protocol.kzIB)
//                                "#KZIC#" -> cell.setCellValue(protocol.kzIC)
//                                "#KZIOV#" -> cell.setCellValue(protocol.kzIOV)
//                                "#KZTEMPTI#" -> cell.setCellValue(protocol.kzTempTI)
//                                "#KZTEMPAMB#" -> cell.setCellValue(protocol.kzTempAmb)
//                                "#KZSPEED#" -> cell.setCellValue(protocol.kzSpeed)
//                                "#KZVIBRO1#" -> cell.setCellValue(protocol.kzVibro1)
//                                "#KZVIBRO2#" -> cell.setCellValue(protocol.kzVibro2)
////                                "#KZTIME#" -> cell.setCellValue(protocol.kzTime)
//                                "#KZP1#" -> cell.setCellValue(protocol.kzP1)
//                                "#KZCOS#" -> cell.setCellValue(protocol.kzCos)
//                                "#KZRESULT#" -> cell.setCellValue(protocol.kzResult)
//                                //N
//                                "#NNAME#" -> cell.setCellValue(protocol.R15)
//                                "#NUAB#" -> cell.setCellValue(protocol.idleUAB)
//                                "#NUBC#" -> cell.setCellValue(protocol.idleUBC)
//                                "#NUCA#" -> cell.setCellValue(protocol.idleUCA)
//                                "#NUOV#" -> cell.setCellValue(protocol.idleUOV)
//                                "#NIA#" -> cell.setCellValue(protocol.idleIA)
//                                "#NIB#" -> cell.setCellValue(protocol.idleIB)
//                                "#NIC#" -> cell.setCellValue(protocol.idleIC)
//                                "#NVIBRO1#" -> cell.setCellValue(protocol.idleVibro1)
//                                "#NVIBRO2#" -> cell.setCellValue(protocol.idleVibro2)
////                                "#NIOV#" -> cell.setCellValue(protocol.idle)
//                                "#NSPEED#" -> cell.setCellValue(protocol.idleSpeed)
//                                "#NF#" -> cell.setCellValue(protocol.idleF)
//                                "#NRESULT#" -> cell.setCellValue(protocol.idleResult)
//                                "#NCOS#" -> cell.setCellValue(protocol.idleCos)
//                                "#NP1#" -> cell.setCellValue(protocol.idleP1)
//                                "#NTEMPTI#" -> cell.setCellValue(protocol.idleTempTI)
//                                "#NTEMPAMB#" -> cell.setCellValue(protocol.idleTempAmb)
//                                "#NTIME#" -> cell.setCellValue(protocol.idleTime)
//                                //MV
//
//
//                                "#MVUAB1#" -> cell.setCellValue(protocol.beforeUuv)
//                                "#MVUBC1#" -> cell.setCellValue(protocol.beforeUvw)
//                                "#MVUCA1#" -> cell.setCellValue(protocol.beforeUwu)
//                                "#MVUAB2#" -> cell.setCellValue(protocol.afterUuv)
//                                "#MVUBC2#" -> cell.setCellValue(protocol.afterUvw)
//                                "#MVUCA2#" -> cell.setCellValue(protocol.afterUwu)
////                                "#MVCALC#" -> cell.setCellValue(protocol.de)
//                                "#MVIA1#" -> cell.setCellValue(protocol.beforeIu)
//                                "#MVIB1#" -> cell.setCellValue(protocol.beforeIv)
//                                "#MVIC1#" -> cell.setCellValue(protocol.beforeIw)
//                                "#MVIA2#" -> cell.setCellValue(protocol.afterIu)
//                                "#MVIB2#" -> cell.setCellValue(protocol.afterIv)
//                                "#MVIC2#" -> cell.setCellValue(protocol.afterIw)
//                                "#MVRESULT#" -> cell.setCellValue(protocol.mvResult)
//                                "#MVCALCA#" -> cell.setCellValue(protocol.mvCalcA)
//                                "#MVCALCB#" -> cell.setCellValue(protocol.mvCalcB)
//                                "#MVCALCC#" -> cell.setCellValue(protocol.mvCalcC)
                                else -> {
                                    if (cell.stringCellValue.contains("#")) {
                                        cell.setCellValue("")
                                    }
                                }
                            }
                        }
                    }
                }
            }
            val outStream = ByteArrayOutputStream()
            wb.write(outStream)
            outStream.close()
        }
    } catch (e: FileNotFoundException) {
//        errorNotification(
//            ("error"),
//            "Не удалось сохранить протокол на диск",
//            Pos.BOTTOM_CENTER
//        )
        println("ssssss")
    }
}
