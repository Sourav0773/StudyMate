package com.example.studymate.ui.screens

import androidx.compose.runtime.Composable

@Composable
fun TasksScreen() {
    FeatureDetailScreen(
        title = "Tasks & Goals",
        description = "Stay on top of assignments, homework, and deadlines.",
        features = listOf(
            "• Add tasks with priority",
            "• Daily, weekly reminder tasks",
            "• Mark tasks as completed"
        )
    )
}
