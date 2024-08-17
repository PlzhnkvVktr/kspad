package ru.avem.ui.navigation

import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import ru.avem.ui.screens.AuthScreen
import ru.avem.ui.screens.MainScreen
import ru.avem.ui.screens.TestScreen
import ru.avem.ui.viewmodels.TestScreenViewModel

fun NavGraphBuilder.buildNavGraph(navController: NavController) {
    composable(route = NavRoutes.MainScreen.uri) {
        MainScreen()
    }
    composable(route = NavRoutes.TestScreen.uri) {
        TestScreen(Modifier)
    }
//    composable(route = NavRoutes.ProtocolScreen.uri) {
//        ProtocolScreen(Modifier)
//    }
//    composable(route = NavRoutes.SettingScreen.uri) {
//        SettingScreen(Modifier)
//    }
    composable(route = NavRoutes.AuthScreen.uri) {
        AuthScreen(Modifier) {
            navController.navigate(route = NavRoutes.TestScreen.uri) {
                popUpTo(NavRoutes.TestScreen.uri) {
                    inclusive = true
                }
            }
        }
    }
//    composable(route = "screen_1") {
//        Screen1 {
//            navController.navigate("screen_2") {
//
//                popUpTo("screen_2") {
//                    inclusive = true
//                }
//
//            }
//        }
//    }
//    composable(route = "screen_2") {
//        Screen2 {
//            navController.navigate("MainScreen") {
//                popUpTo("MainScreen") {
//                    inclusive = true
//                }
//            }
//        }
//    }
//    composable(route = "screen_3") {
//        Screen3 {
//            navController.navigate("screen_1") {
//                popUpTo("screen_1") {
//                    inclusive = true
//                }
//            }
//        }
//    }
//    composable(route = NavRoutes.TestScreen.uri) {
//        TestScreen(modifier = Modifier.fillMaxSize())
//    }
}