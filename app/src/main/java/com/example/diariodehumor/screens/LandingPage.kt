package com.example.diariodehumor.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun LandingPage(navController: NavHostController) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Bem-vindo :D !", style = MaterialTheme.typography.h4)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { navController.navigate("moodTracker") }) {
            Text(text = "Go to Mood Tracker")
        }
    }

}