package com.example.studymate.ui.screens

import androidx.compose.runtime.Composable

@Composable
fun NotificationScreen() {
    FeatureDetailScreen(
        title = "Notifications",
        description = "Set reminders to stay consistent.",
        features = listOf(
            "• Daily study reminders",
            "• Subject-specific alerts",
            "• Break reminders during sessions"
        )
    )
}
