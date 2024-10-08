package ru.avem.di

import org.koin.dsl.module
import ru.avem.data.db.DBManager
import ru.avem.data.db.ProtocolBuilder
import ru.avem.modules.devices.CM
import ru.avem.ui.viewmodels.*

val appModule = module {
    single { DBManager }
    single { CM }
//    single { DeviceManager() }
//    single { TestController() }
    single { MainScreenViewModel() }
    single { AuthScreenViewModel() }
    single { TestScreenViewModel() }
    single { ProtocolScreenViewModel() }
    single { AdminScreenViewModel() }
    single { TestObjectScreenViewModel() }
    single { ProtocolBuilder }
}

