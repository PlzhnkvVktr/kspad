package ru.avem.di

import org.koin.dsl.module
import ru.avem.data.db.DBManager
import ru.avem.modules.devices.CM
import ru.avem.modules.tests.CustomController
import ru.avem.ui.viewmodels.AuthScreenViewModel
import ru.avem.ui.viewmodels.MainScreenViewModel
import ru.avem.ui.viewmodels.TestScreenViewModel

val appModule = module {
    single { DBManager }
    single { CM }
    single { CustomController() }
    single { MainScreenViewModel() }
    single { AuthScreenViewModel() }
    single { TestScreenViewModel() }
}

