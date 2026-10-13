package com.example.studentregistration.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.map
import com.example.studentregistration.data.Student
import com.example.studentregistration.viewmodel.StudentViewModel

private data class SearchResult(val student: Student?)

@Composable
fun SearchScreen(viewModel: StudentViewModel, onBack: () -> Unit) {
    var idInput by remember { mutableStateOf("") }
    var searchedId by remember { mutableStateOf<Int?>(null) }
    var inputError by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Search Student", style = MaterialTheme.typography.headlineMedium)

        OutlinedTextField(
            value = idInput,
            onValueChange = { idInput = it },
            label = { Text("Student ID") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                val id = idInput.toIntOrNull()
                if (id == null) {
                    inputError = "Please enter a valid numeric ID."
                    searchedId = null
                } else {
                    inputError = null
                    searchedId = id
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Search")
        }

        inputError?.let { Text(it, color = MaterialTheme.colorScheme.error) }

        searchedId?.let { id -> StudentResult(viewModel, id) }

        OutlinedButton(onClick = onBack, modifier = Modifier.fillMaxWidth()) {
            Text("Back to Home")
        }
    }
}

@Composable
private fun StudentResult(viewModel: StudentViewModel, id: Int) {

    val result by remember(id) {
        viewModel.getStudentById(id).map { SearchResult(it) }
    }.observeAsState()

    val current = result
    when {
        current == null -> Text("Searching...")

        current.student == null ->
            Text("No student found with ID $id.", color = MaterialTheme.colorScheme.error)

        else -> Card(modifier = Modifier.fillMaxWidth()) {
            Column(Modifier.padding(16.dp)) {
                Text("Name: ${current.student.name}", style = MaterialTheme.typography.titleMedium)
                Text("Course: ${current.student.course}")
            }
        }
    }
}