package com.example.diariodehumor.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.diariodehumor.model.Mood
import com.example.diariodehumor.viewmodel.MoodViewModel
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun MoodTrackerScreen(name: String, navController: NavHostController) {

    val viewModel: MoodViewModel = viewModel()
    val moods by viewModel.allMoods.collectAsState(initial = emptyList())

    var isAddingMood by remember { mutableStateOf(false) }
    var selectedMood by remember { mutableStateOf("") }
    var isDescriptionDialogVisible by remember { mutableStateOf(false) }
    var moodDescription by remember { mutableStateOf("") }

    var selectedFilterMood by remember { mutableStateOf<String?>(null) }
    var selectedFilterDate by remember { mutableStateOf<String?>(null) }

    val filteredMoods = moods.filter { mood ->
        (selectedFilterMood == null || mood.mood == selectedFilterMood) &&
                (selectedFilterDate == null || formatDate(mood.date) == selectedFilterDate)
    }

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
        },
        bottomBar = {
            Row(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Filtros com a opção de limpar
                FilterButton("Humor", moods.map { it.mood }.distinct(), onFilterSelected = { selectedFilterMood = it })
                Spacer(modifier = Modifier.width(8.dp))
                FilterButton("Data", moods.map { formatDate(it.date) }.distinct(), onFilterSelected = { selectedFilterDate = it })
                Spacer(modifier = Modifier.width(8.dp))
                // Botão de limpar filtro
                ClearFiltersButton(
                    onClearFilters = {
                        selectedFilterMood = null
                        selectedFilterDate = null
                    }
                )
            }
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                items(filteredMoods) { mood ->
                    MoodItem(mood)
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
                            MoodButtonList(
                                onMoodSelected = { mood ->
                                    selectedMood = mood
                                    isDescriptionDialogVisible = true
                                }
                            )
                        }
                    },
                    confirmButton = {
                        Button(
                            onClick = {
                                if (selectedMood.isNotEmpty()) {
                                    isDescriptionDialogVisible = true
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
                        viewModel.addMood(
                            Mood(mood = selectedMood, date = Date(), description = moodDescription)
                        )
                        selectedMood = ""
                        moodDescription = ""
                        isAddingMood = false
                    }
                )
            }
        }
    }
}

@Composable
fun FilterButton(
    label: String,
    options: List<String>,
    onFilterSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Column {
        IconButton(onClick = { expanded = !expanded }) {
            Icon(Icons.Outlined.Settings, contentDescription = "Filtrar $label")
        }
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            options.forEach { option ->
                DropdownMenuItem(onClick = {
                    onFilterSelected(option)
                    expanded = false
                }) {
                    Text(text = option)
                }
            }
        }
    }
}

@Composable
fun ClearFiltersButton(onClearFilters: () -> Unit) {
    Button(
        onClick = onClearFilters,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text("Limpar Filtros")
    }
}

@Composable
fun MoodItem(mood: Mood) {

    val dateFormat = SimpleDateFormat("dd/MM/yyyy")
    val dateFormatted = dateFormat.format(mood.date)
    Column(modifier = Modifier.fillMaxWidth()) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            elevation = 4.dp
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Humor: ${mood.mood}",
                    style = MaterialTheme.typography.h6,
                    color = MaterialTheme.colors.primary
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Descrição: ${mood.description}",
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colors.onSurface
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Data: $dateFormatted",
                    style = MaterialTheme.typography.body2,
                    color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f)
                )
            }
        }
    }
}

@Composable
fun MoodButtonList(onMoodSelected: (String) -> Unit) {
    val moods = listOf("Feliz", "Triste", "Neutro", "Zangado", "Outro")
    Column {
        moods.forEach { mood ->
            Button(
                onClick = { onMoodSelected(mood) },
                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
            ) {
                Text(mood)
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

fun formatDate(date: Date): String {
    val dateFormat = SimpleDateFormat("dd/MM/yyyy")
    return dateFormat.format(date)
}
