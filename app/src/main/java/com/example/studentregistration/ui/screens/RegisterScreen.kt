package com.example.studentregistration.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.studentregistration.data.Student
import com.example.studentregistration.viewmodel.StudentViewModel

@Composable
fun RegisterScreen(viewModel: StudentViewModel, onBack: () -> Unit) {
    var name by remember { mutableStateOf("") }
    var course by remember { mutableStateOf("") }
    var year by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var successMessage by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Register Student", style = MaterialTheme.typography.headlineMedium)
        Text("The Student ID is generated automatically.", style = MaterialTheme.typography.bodySmall)

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = course,
            onValueChange = { course = it },
            label = { Text("Course") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = year,
            onValueChange = { year = it },
            label = { Text("Year (1-4)") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        errorMessage?.let { Text(it, color = MaterialTheme.colorScheme.error) }
        successMessage?.let { Text(it, color = MaterialTheme.colorScheme.primary) }

        Button(
            onClick = {
                successMessage = null
                val yearInt = year.toIntOrNull()   // null if not a valid number

                // VALIDATION – checked before anything is saved
                errorMessage = when {
                    name.isBlank() -> "Please enter a name."
                    course.isBlank() -> "Please enter a course."
                    yearInt == null -> "Year must be a number."
                    yearInt !in 1..4 -> "Year must be between 1 and 4."
                    else -> null
                }

                if (errorMessage == null && yearInt != null) {
                    val student = Student(name = name.trim(), course = course.trim(), year = yearInt)
                    viewModel.insert(student) { newId ->
                        successMessage = "Student registered with ID $newId"
                        name = ""; course = ""; year = ""   // clear the form
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Register")
        }

        OutlinedButton(onClick = onBack, modifier = Modifier.fillMaxWidth()) {
            Text("Back to Home")
        }
    }
}