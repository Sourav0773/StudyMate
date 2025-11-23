package com.example.studymate.ui.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import com.example.studymate.db.modules.DatabaseModule
import com.example.studymate.ui.theme.AppBackground
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SplashScreen(navController: NavController) {

    val context = LocalContext.current
    val db = remember { DatabaseModule.getDb(context) }
    val scope = rememberCoroutineScope()

    // Run auto login logic
    LaunchedEffect(true) {
        delay(1800)

        val user = db.userDao().getUser()

        if (user != null) {
            // Auto-login
            navController.navigate("home") {
                popUpTo("splash") { inclusive = true }
            }
        } else {
            // Go to login
            navController.navigate("login") {
                popUpTo("splash") { inclusive = true }
            }
        }
    }

    // Animation
    var start by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { start = true }

    val scale by animateFloatAsState(
        targetValue = if (start) 1f else 0.6f,
        animationSpec = tween(900)
    )

    val alpha by animateFloatAsState(
        targetValue = if (start) 1f else 0f,
        animationSpec = tween(1000)
    )

    AppBackground {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {

                Icon(
                    imageVector = Icons.Default.School,
                    contentDescription = "Study Icon",
                    tint = Color.White,
                    modifier = Modifier
                        .size(90.dp)
                        .scale(scale)
                        .alpha(alpha)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "StudyMate",
                    color = Color.White,
                    fontSize = 34.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.alpha(alpha)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Stay focused. Stay ahead.",
                    color = Color.White.copy(alpha = 0.9f),
                    fontSize = 16.sp,
                    modifier = Modifier.alpha(alpha)
                )
            }
        }
    }
}
