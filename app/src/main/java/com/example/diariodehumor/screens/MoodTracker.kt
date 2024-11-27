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
import androidx.compose.material.TextField
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
    var isDescriptionDialogVisible by remember { mutableStateOf(false) }
    var moodDescription by remember { mutableStateOf("") }

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
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                items(moods) { mood ->
                    Text("Humor: ${mood.mood}, Data: ${mood.date}, Description: ${mood.description}", style = MaterialTheme.typography.body1)
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }

            if (isAddingMood) {
                AlertDialog(
                    onDismissRequest = { isAddingMood = false },
                    title = { Text("Adicionar Humor") },
                    text = {
                        Column {
                            Text("Escolha o humor:")
                            Spacer(modifier = Modifier.height(16.dp))
                            Column {
                                Button(onClick = {
                                    selectedMood = "Feliz"
                                    isDescriptionDialogVisible = true
                                }) {
                                    Text("😊 Feliz")
                                }
                                Spacer(modifier = Modifier.width(8.dp))
                                Button(onClick = {
                                    selectedMood = "Triste"
                                    isDescriptionDialogVisible = true
                                }) {
                                    Text("😢 Triste")
                                }
                                Spacer(modifier = Modifier.width(8.dp))
                                Button(onClick = {
                                    selectedMood = "Neutro"
                                    isDescriptionDialogVisible = true
                                }) {
                                    Text("😐 Neutro")
                                }
                                Spacer(modifier = Modifier.width(8.dp))
                                Button(onClick = {
                                    selectedMood = "Zangado"
                                    isDescriptionDialogVisible = true
                                }) {
                                    Text("😡 Zangado")
                                }
                                Spacer(modifier = Modifier.width(8.dp))
                                Button(onClick = {
                                    selectedMood = "Outro"
                                    isDescriptionDialogVisible = true
                                }) {
                                    Text("Outro")
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
                                            date = Date(),
                                            description = moodDescription
                                        )
                                    )
                                    selectedMood = ""
                                    moodDescription = ""
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

            if (isDescriptionDialogVisible) {
                DescriptionDialog(
                    onDismiss = { isDescriptionDialogVisible = false },
                    onSave = { description ->
                        moodDescription = description
                        isDescriptionDialogVisible = false
                    }
                )
            }
        }
    }
}

@Composable
fun DescriptionDialog(
    onDismiss: () -> Unit,
    onSave: (String) -> Unit
) {
    var description by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text("Adicionar descrição") },
        text = {
            Column {
                Text("Escreva a descrição do seu humor:")
                Spacer(modifier = Modifier.height(16.dp))
                TextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Descrição") }
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onSave(description)
                }
            ) {
                Text("Salvar")
            }
        },
        dismissButton = {
            Button(onClick = { onDismiss() }) {
                Text("Cancelar")
            }
        }
    )
}
