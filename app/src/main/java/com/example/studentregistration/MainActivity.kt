package com.example.studentregistration

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.studentregistration.ui.screens.HomeScreen
import com.example.studentregistration.ui.screens.RegisterScreen
import com.example.studentregistration.ui.screens.SearchScreen
import com.example.studentregistration.ui.theme.StudentRegistrationTheme
import com.example.studentregistration.viewmodel.StudentViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StudentRegistrationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AppNavigation(Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun AppNavigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val studentViewModel: StudentViewModel = viewModel()

    NavHost(navController = navController, startDestination = "home", modifier = modifier) {
        composable("home") {
            HomeScreen(
                onRegisterClick = { navController.navigate("register") },
                onSearchClick = { navController.navigate("search") }
            )
        }
        composable("register") {
            RegisterScreen(viewModel = studentViewModel, onBack = { navController.popBackStack() })
        }
        composable("search") {
            SearchScreen(viewModel = studentViewModel, onBack = { navController.popBackStack() })
        }
    }
}