package com.example.studymate.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TimerScreen() {
    FeatureDetailScreen(
        title = "Study Timer",
        description = "Boost focus using Pomodoro and custom timers.",
        features = listOf(
            "• 25/5 Pomodoro cycles",
            "• Custom session length",
            "• Daily productivity stats"
        )
    )
}

@Composable
fun FeatureDetailScreen(title: String, description: String, features: List<String>) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Text(title, fontSize = 28.sp, fontWeight = FontWeight.Bold)
        Text(description, fontSize = 16.sp, color = Color.Gray)

        features.forEach {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Text(
                    it,
                    modifier = Modifier.padding(14.dp),
                    fontSize = 16.sp,
                    color = Color.DarkGray
                )
            }
        }
    }
}
