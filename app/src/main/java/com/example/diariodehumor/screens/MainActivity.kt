package com.example.diariodehumor.screens

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.*
import com.example.diariodehumor.ui.theme.DiarioDeHumorTheme
import com.example.diariodehumor.viewmodel.MoodViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.diariodehumor.model.Name
import kotlinx.coroutines.flow.firstOrNull


class MainActivity : ComponentActivity() {
    private val viewModel: MoodViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DiarioDeHumorTheme {
                AppNavigation(viewModel)
            }
        }
    }
}



@Composable
fun AppNavigation(viewModel: MoodViewModel) {
    val navController: NavHostController = rememberNavController()


    var hasNavigated by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        if (!hasNavigated) {
            val name = viewModel.getLastRegisteredName()


            if (name.isEmpty()) {
                navController.navigate("landing")
            } else {
                navController.navigate("moodTracker/$name")
            }
            hasNavigated = true
        }
    }

    NavHost(navController = navController, startDestination = "landing") {
        composable("landing") { LandingPage(navController) }
        composable("moodTracker/{name}") { backStackEntry ->
            val name = backStackEntry.arguments?.getString("name") ?: ""
            MoodTrackerScreen(name = name, navController = navController)
        }
    }
}
