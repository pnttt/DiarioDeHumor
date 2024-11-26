package com.example.diariodehumor.screens

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import com.example.diariodehumor.model.Mood
import com.example.diariodehumor.viewmodel.MoodViewModel
import java.util.Date

@Composable
fun MoodTrackerScreen(name: String, navController: NavHostController) {

    val viewModel: MoodViewModel = viewModel()

    val moods by viewModel.allMoods.collectAsState(initial = emptyList())

    var isAddingMood by remember { mutableStateOf(false) }
    var selectedMood by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Diário de Humor - $name") },
                backgroundColor = MaterialTheme.colors.primary
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { isAddingMood = true }) {
                Icon(Icons.Default.Add, contentDescription = "Adicionar Humor")
            }
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            // Lista de Humores
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                items(moods) { mood ->
                    Text("Humor: ${mood.mood}, Data: ${mood.date}", style = MaterialTheme.typography.body1)
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }

            // BottomSheet para Adicionar Humor
            if (isAddingMood) {
                AlertDialog(
                    onDismissRequest = { isAddingMood = false },
                    title = { Text("Adicionar Humor") },
                    text = {
                        Column {
                            Text("Escolha o humor:")
                            Spacer(modifier = Modifier.height(16.dp))
                            Row {
                                Button(onClick = { selectedMood = "Feliz" }) {
                                    Text("😊 Feliz")
                                }
                                Spacer(modifier = Modifier.width(8.dp))
                                Button(onClick = { selectedMood = "Triste" }) {
                                    Text("😢 Triste")
                                }
                                Spacer(modifier = Modifier.width(8.dp))
                                Button(onClick = { selectedMood = "Neutro" }) {
                                    Text("😐 Neutro")
                                }
                            }
                        }
                    },
                    confirmButton = {
                        Button(
                            onClick = {
                                if (selectedMood.isNotEmpty()) {
                                    viewModel.addMood(
                                        Mood(
                                            mood = selectedMood,
                                            date = Date()
                                        )
                                    )
                                    selectedMood = ""
                                    isAddingMood = false
                                }
                            }
                        ) {
                            Text("Salvar")
                        }
                    },
                    dismissButton = {
                        Button(onClick = { isAddingMood = false }) {
                            Text("Cancelar")
                        }
                    }
                )
            }
        }
    }
}
