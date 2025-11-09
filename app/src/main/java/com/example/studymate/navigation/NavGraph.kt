package com.example.studymate.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.studymate.ui.screens.HomeScreen
import com.example.studymate.ui.screens.SplashScreen
import com.example.studymate.ui.screens.LoginScreen
import com.example.studymate.ui.screens.SignupScreen

@Composable
fun NavGraph(startDestination: String = "splash") {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        // Splash Screen
        composable("splash") { SplashScreen(navController) }

        // Login Screen
        composable("login") { LoginScreen(navController) }

        //Signup Screen
        composable(route = "signup"){ SignupScreen(navController) }

        //Home Screen
        composable(route = "home"){ HomeScreen(navController) }
    }
}
