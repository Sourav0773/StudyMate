package com.example.studymate.ui.screens

import androidx.compose.runtime.Composable

@Composable
fun SubjectsScreen() {
    FeatureDetailScreen(
        title = "Subjects",
        description = "Organize all your subjects and notes in one place.",
        features = listOf(
            "• Add/edit/delete subjects",
            "• Attach notes and materials",
            "• Track study progress per subject"
        )
    )
}
