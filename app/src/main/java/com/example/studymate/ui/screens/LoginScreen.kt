package com.example.studymate.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.studymate.firebase.AuthViewModel
import com.example.studymate.ui.theme.AppBackground
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    navController: NavController,
    authViewModel: AuthViewModel = AuthViewModel()
) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var loading by remember { mutableStateOf(false) }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    // UI Colors
    val cardBackground = Color(0xFFF4EDFF)
    val titleColor = Color(0xFF2D2A45)
    val subtitleColor = Color(0xFF7A78A1)
    val buttonColor = Color(0xFF6A5AE0)

    AppBackground {
        Scaffold(
            snackbarHost = { SnackbarHost(snackbarHostState) },
            containerColor = Color.Transparent
        ) { paddingValues ->

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(16.dp)
                        .shadow(6.dp, RoundedCornerShape(20.dp)),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFDFEFE))
                ) {

                    Column(
                        modifier = Modifier
                            .padding(32.dp)
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        // Title
                        Text(
                            "Welcome Back 📚",
                            fontSize = 30.sp,
                            fontWeight = FontWeight.Bold,
                            color = titleColor
                        )

                        Spacer(Modifier.height(6.dp))

                        Text(
                            "Log in to StudyMate",
                            fontSize = 16.sp,
                            color = subtitleColor
                        )

                        Spacer(Modifier.height(30.dp))

                        // Email
                        OutlinedTextField(
                            value = email,
                            onValueChange = { email = it },
                            label = { Text("Email") },
                            singleLine = true,
                            enabled = !loading,
                            leadingIcon = { Icon(Icons.Default.Person, null) },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = buttonColor,
                                focusedLabelColor = buttonColor
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(Modifier.height(18.dp))

                        // Password
                        OutlinedTextField(
                            value = password,
                            onValueChange = { password = it },
                            label = { Text("Password") },
                            singleLine = true,
                            enabled = !loading,
                            visualTransformation = PasswordVisualTransformation(),
                            leadingIcon = { Icon(Icons.Default.Lock, null) },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = buttonColor,
                                focusedLabelColor = buttonColor
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(Modifier.height(32.dp))

                        // Login Button
                        Button(
                            onClick = {

                                if (email.isBlank() || password.isBlank()) {
                                    scope.launch {
                                        snackbarHostState.showSnackbar("Enter email & password")
                                    }
                                    return@Button
                                }

                                loading = true

                                authViewModel.login(
                                    email,
                                    password,
                                    onSuccess = {
                                        scope.launch {

                                            navController.navigate("home") {
                                                popUpTo("login") { inclusive = true }
                                                launchSingleTop = true
                                            }

                                            snackbarHostState.showSnackbar("Successfully Logged In!")
                                            delay(200) // smooth transition
                                            loading = false
                                        }
                                    },
                                    onError = { error ->
                                        loading = false
                                        scope.launch {
                                            snackbarHostState.showSnackbar(error)
                                        }
                                    }
                                )

                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(55.dp),
                            shape = RoundedCornerShape(14.dp),
                            enabled = !loading,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = buttonColor,
                                contentColor = Color.White
                            )
                        ) {
                            if (loading) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(24.dp),
                                    color = Color.White,
                                    strokeWidth = 2.dp
                                )
                            } else {
                                Text("LOGIN", fontSize = 17.sp, fontWeight = FontWeight.Bold)
                            }
                        }

                        Spacer(Modifier.height(18.dp))

                        // Signup
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("Don't have an account? ", color = subtitleColor)

                            TextButton(
                                onClick = { navController.navigate("signup") },
                                enabled = !loading
                            ) {
                                Text(
                                    "Sign Up",
                                    color = buttonColor,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                    }
                }
            }
        }
    }
}
