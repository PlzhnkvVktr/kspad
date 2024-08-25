package ru.avem.ui.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import ru.avem.ui.screens.*

fun NavGraphBuilder.buildNavGraph(navController: NavController) {
    composable(route = NavRoutes.MainScreen.uri) {
        MainScreen(
            modifier = Modifier,
            onTestScreen = {
                navController.navigate(route = NavRoutes.TestScreen.uri) {
                    popUpTo(NavRoutes.TestScreen.uri) {
                        inclusive = true
                    }
                }
            },
            onProtocolScreen = {
                navController.navigate(route = NavRoutes.ProtocolScreen.uri) {
                    popUpTo(NavRoutes.ProtocolScreen.uri) {
                        inclusive = true
                    }
                }
            },
            onAdminScreen = {
                navController.navigate(route = NavRoutes.AdminScreen.uri) {
                    popUpTo(NavRoutes.AdminScreen.uri) {
                        inclusive = true
                    }
                }
            }
        )
    }
    composable(route = NavRoutes.TestScreen.uri) {
        TestScreen(
            modifier = Modifier,
            onMainScreen = {
                navController.navigate(route = NavRoutes.MainScreen.uri) {
                    popUpTo(NavRoutes.TestScreen.uri) {
                        inclusive = true
                    }
                }
            }
        )
    }
    composable(route = NavRoutes.ProtocolScreen.uri) {
        ProtocolScreen(
            modifier = Modifier,
            onMainScreen = {
                navController.navigate(route = NavRoutes.MainScreen.uri) {
                    popUpTo(NavRoutes.TestScreen.uri) {
                        inclusive = true
                    }
                }
            },
            onAdminScreen = {
                navController.navigate(route = NavRoutes.AdminScreen.uri) {
                    popUpTo(NavRoutes.AdminScreen.uri) {
                        inclusive = true
                    }
                }
            }
            )
    }
    composable(route = NavRoutes.AdminScreen.uri) {
        AdminScreen(
            modifier = Modifier,
            onMainScreen = {
                navController.navigate(route = NavRoutes.MainScreen.uri) {
                    popUpTo(NavRoutes.TestScreen.uri) {
                        inclusive = true
                    }
                }
            },
            onProtocolScreen = {
                navController.navigate(route = NavRoutes.ProtocolScreen.uri) {
                    popUpTo(NavRoutes.ProtocolScreen.uri) {
                        inclusive = true
                    }
                }
            }
        )
    }
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