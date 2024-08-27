package ru.avem.ui.viewmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.avem.data.db.DBManager
import ru.avem.data.db.ProtocolBuilder
import ru.avem.data.db.TestProtocol
import ru.avem.data.db.saveProtocolAsWorkbook
import java.awt.Desktop
import java.io.File
import java.io.IOException

class ProtocolScreenViewModel: ViewModel(), KoinComponent {

    val db by inject<DBManager>()
    val protocolBuilder by inject<ProtocolBuilder>()

    private val scope = CoroutineScope(Dispatchers.Default)
    var protocolList = mutableStateListOf<TestProtocol>()
    var textFind = mutableStateOf("")

    private fun openFile(file: File) {
        try {
            Desktop.getDesktop().open(file)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun getProtocols() {
        scope.launch {
            protocolList.clear()
            protocolList.addAll(db.getAllProtocols())
        }
    }

    fun performSearch(predicate: String) {
        scope.launch {
            val values = db.findProtocol(predicate)
            protocolList.clear()
            protocolList.addAll(values)
        }
    }

    fun deleteProtocol(protocol: TestProtocol) {
        scope.launch {
            db.deleteProtocolItemByName1(protocol)
            protocolList.remove(protocol)
        }
    }

//    fun savePDF (protocol: TestProtocol) {
//        scope.launch {
//            ProtocolBuilder.fillProtocol(protocol)
//            PdfCreator().createPDF(ProtocolBuilder)
//            openFile(File("./report.pdf"))
//            ProtocolBuilder.clear()
//        }
//    }

    fun saveExcel (protocol: TestProtocol) {
        scope.launch {
            protocolBuilder.fillProtocol(protocol)
            saveProtocolAsWorkbook(protocolBuilder)
            openFile(File("cfg/lastOpened.xlsx"))
            protocolBuilder.clear()
        }
    }
}