package ru.avem.di

import org.koin.dsl.module
import ru.avem.data.db.DBManager
import ru.avem.ui.viewmodels.AuthScreenViewModel
import ru.avem.ui.viewmodels.MainScreenViewModel
import ru.avem.ui.viewmodels.TestScreenViewModel

val appModule = module {
    single { DBManager }
    single { MainScreenViewModel() }
    single { AuthScreenViewModel() }
    single { TestScreenViewModel() }
}

