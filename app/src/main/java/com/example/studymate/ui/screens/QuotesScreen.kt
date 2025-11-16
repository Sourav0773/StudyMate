package com.example.studymate.ui.screens

import androidx.compose.runtime.Composable

@Composable
fun QuotesScreen() {
    FeatureDetailScreen(
        title = "Motivation Quotes",
        description = "Receive inspiring quotes to stay motivated.",
        features = listOf(
            "• Daily motivational popup",
            "• Pull new quotes from API",
            "• Save favorite quotes"
        )
    )
}
