package com.example.studymate.navigation

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.studymate.ui.screens.*

@Composable
fun NavGraph(startDestination: String = "splash") {

    val navController = rememberNavController()
    val duration = 320

    // ========== ANIMATION HELPERS ==========

    // New screen enters → slide from right + slight scale
    fun AnimatedContentTransitionScope<*>.enterTransition() = slideIntoContainer(
        AnimatedContentTransitionScope.SlideDirection.Left,
        tween(duration)
    ) + scaleIn(initialScale = 1.05f, animationSpec = tween(duration))

    // Current screen exits → slide to left + fade
    fun AnimatedContentTransitionScope<*>.exitTransition() = slideOutOfContainer(
        AnimatedContentTransitionScope.SlideDirection.Left,
        tween(duration)
    ) + fadeOut(tween(duration)) + scaleOut(targetScale = 0.95f, animationSpec = tween(duration))

    // Back navigation → slide from left
    fun AnimatedContentTransitionScope<*>.popEnterTransition() = slideIntoContainer(
        AnimatedContentTransitionScope.SlideDirection.Right,
        tween(duration)
    ) + scaleIn(initialScale = 0.95f, animationSpec = tween(duration)) + fadeIn(tween(duration))

    // Back navigation → exit to right
    fun AnimatedContentTransitionScope<*>.popExitTransition() = slideOutOfContainer(
        AnimatedContentTransitionScope.SlideDirection.Right,
        tween(duration)
    ) + scaleOut(targetScale = 1.05f, animationSpec = tween(duration))


    // ========== NAV HOST ==========

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {

        composable(
            "splash",
            enterTransition = { enterTransition() },
            exitTransition = { exitTransition() },
            popEnterTransition = { popEnterTransition() },
            popExitTransition = { popExitTransition() }
        ) {
            SplashScreen(navController)
        }

        composable(
            "login",
            enterTransition = { enterTransition() },
            exitTransition = { exitTransition() },
            popEnterTransition = { popEnterTransition() },
            popExitTransition = { popExitTransition() }
        ) {
            LoginScreen(navController)
        }

        composable(
            "signup",
            enterTransition = { enterTransition() },
            exitTransition = { exitTransition() },
            popEnterTransition = { popEnterTransition() },
            popExitTransition = { popExitTransition() }
        ) {
            SignupScreen(navController)
        }

        composable(
            "home",
            enterTransition = { enterTransition() },
            exitTransition = { exitTransition() },
            popEnterTransition = { popEnterTransition() },
            popExitTransition = { popExitTransition() }
        ) {
            HomeScreen(navController)
        }

        composable("timer") { TimerScreen() }
        composable("subjects") { SubjectsScreen() }
        composable("tasks") { TasksScreen() }
        composable("notifications") { NotificationScreen() }
        composable("quotes") { QuotesScreen() }

    }
}
