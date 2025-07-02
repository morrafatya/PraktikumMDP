package com.example.praktikummdp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.praktikummdp.Screen.HomeScreen
import com.example.praktikummdp.Screen.LoginScreen
import com.example.praktikummdp.Screen.RegistrasiScreen
import com.example.praktikummdp.Screen.ResultScreen
import com.example.praktikummdp.Screen.WelcomeScreen


@Composable
fun SetupNavGraph(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = Screen.Login.route,
        modifier = modifier
    ) {
        composable(Screen.Login.route) {
            LoginScreen(navController)
        }
        composable(Screen.Registrasi.route) {
            RegistrasiScreen(navController)
        }
        composable(Screen.Home.route) {
            HomeScreen(navController)
        }
        composable(Screen.Result.route + "/{name}") { backStackEntry ->
            val name = backStackEntry.arguments?.getString("name") ?: ""
            ResultScreen(name = name, navController = navController)
        }
        composable(Screen.Profile.route) {
            WelcomeScreen()
        }
    }
}
