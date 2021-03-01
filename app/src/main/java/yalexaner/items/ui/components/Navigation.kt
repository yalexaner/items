package yalexaner.items.ui.components

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import yalexaner.items.ui.screens.MainScreen

@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@Composable
fun MainNavHost() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Route.MAIN) {
        composable(route = Route.MAIN) { MainScreen() }
    }
}

object Route {
    const val MAIN: String = "main"
}