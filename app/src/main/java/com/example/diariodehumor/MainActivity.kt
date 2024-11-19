package com.example.diariodehumor

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.diariodehumor.model.Mood
import com.example.diariodehumor.ui.theme.DiarioDeHumorTheme
import com.example.diariodehumor.viewmodel.MoodViewModel
import java.util.*
import androidx.compose.foundation.lazy.items


class MainActivity : ComponentActivity() {
    private val viewModel: MoodViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DiarioDeHumorTheme {
                MoodTrackerScreen(viewModel)
            }
        }
    }
}

@Composable
fun MoodTrackerScreen(viewModel: MoodViewModel) {
    // Observa a lista de humores a partir do ViewModel
    val moods by viewModel.allMoods.collectAsState(initial = emptyList())

    var isAddingMood by remember { mutableStateOf(false) }
    var selectedMood by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Diário de Humor") },
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
                items(moods) { mood -> // moods já é do tipo List<Mood>
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
