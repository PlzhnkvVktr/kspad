package ru.avem.ui.navigation

sealed class NavRoutes(val uri: String) {
    data object MainScreen : NavRoutes("MainScreen")
    data object TestScreen : NavRoutes("TestScreen")
    data object ProtocolScreen : NavRoutes("ProtocolScreen")
    data object AuthScreen : NavRoutes("AuthScreen")
    data object SettingScreen : NavRoutes("SettingScreen")
}