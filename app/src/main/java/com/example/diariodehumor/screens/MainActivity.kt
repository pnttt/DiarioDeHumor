package com.example.diariodehumor.screens

import android.os.Bundle
import android.util.Log
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
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


class MainActivity : ComponentActivity() {
    private val viewModel: MoodViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DiarioDeHumorTheme {
                AppNavigation()
            }
        }
    }
}



@Composable
fun AppNavigation() {
    val navController: NavHostController = rememberNavController()

    NavHost(navController = navController, startDestination = "landing") {
        composable("landing") { LandingPage(navController) }
        composable("moodTracker") { MoodTrackerScreen(navController) }
    }
}
