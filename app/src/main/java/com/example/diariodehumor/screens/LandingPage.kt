package com.example.diariodehumor.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.diariodehumor.viewmodel.MoodViewModel

@Composable
fun LandingPage(navController: NavHostController) {
    val viewModel: MoodViewModel = viewModel()
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Bem-vindo :D !", style = MaterialTheme.typography.h4)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Por-favor, diz-nos o teu nome:", style = MaterialTheme.typography.body1)
        Spacer(modifier = Modifier.height(8.dp))
        var name by remember { mutableStateOf("") }
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nome") }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { navController.navigate("mood_tracker") }) {
            Text("Continuar")
        }
        viewModel.userName(name)

    }

}