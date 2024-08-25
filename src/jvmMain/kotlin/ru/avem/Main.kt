package ru.avem

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import org.koin.compose.KoinApplication
import org.koin.core.context.startKoin
import ru.avem.di.appModule
import ru.avem.ui.navigation.NavRoutes
import ru.avem.ui.navigation.buildNavGraph
import ru.avem.ui.screens.MainScreen
import ru.avem.ui.viewmodels.TestScreenViewModel


fun main() = application {

    KoinApplication(application = {
        modules(appModule)
    }) {
        val navController = rememberNavController()
        Window(
            onCloseRequest = ::exitApplication,
            title = "КСПЭМ",
            state = rememberWindowState(placement = WindowPlacement.Maximized),
            icon = painterResource("ru/logo/logo.ico")
        ) {
            NavHost(
                modifier = Modifier,
                navController = navController,
                startDestination = "MainScreen"
            ) {
                buildNavGraph(navController)
            }
        }
    }
}


//@Composable
//fun Screen1 (onClick: () -> Unit) {
////    var vm: TestScreenViewModel = viewModel()
//
//    Column(
//        modifier = Modifier.fillMaxSize(),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center
//    ) {
//        Text(
//            text = "Screen 1",
//            fontSize = 30.sp
//        )
//        Spacer(modifier = Modifier.height(30.dp))
//    }
//    Button(
//        onClick = {onClick()}
//    ) {
//        Text("Next")
//    }
//}
//
//@Composable
//fun Screen2 (onClick: () -> Unit) {
//    Column(
//        modifier = Modifier.fillMaxSize(),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center
//    ) {
//        Text(
//            text = "Screen 2",
//            fontSize = 30.sp
//        )
//        Spacer(modifier = Modifier.height(30.dp))
//    }
//    Button(
//        onClick = { onClick() }
//    ) {
//        Text("Next")
//    }
//}
//@Composable
//fun Screen3 (onClick: () -> Unit) {
//    Column(
//        modifier = Modifier.fillMaxSize(),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center
//    ) {
//        Text(
//            text = "Screen 3",
//            fontSize = 30.sp
//        )
//        Spacer(modifier = Modifier.height(30.dp))
//    }
//    Button(
//        onClick = {onClick()}
//    ) {
//        Text("Next")
//    }
//}
