package com.example.diariodehumor.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.diariodehumor.model.Name
import com.example.diariodehumor.viewmodel.MoodViewModel

@Composable
fun LandingPage(navController: NavHostController) {
    val viewModel: MoodViewModel = viewModel()
    var name by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFFC0)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp)
        ) {
            // Title
            Text(
                text = "Bem-vindo!",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Por favor diz-nos o teu nome, para que possamos continuar :)",
                fontSize = 16.sp,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Nome") },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    backgroundColor = Color(0xFFD3D3D3)
                )
            )
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    viewModel.userName(Name(name = name))
                    navController.navigate("moodTracker/$name")
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFF90EE90)
                )
            ) {
                Text(text = "Pronto!")
            }
        }
    }
}
