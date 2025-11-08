package com.example.myapplication.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.myapplication.ui.components.EmptyView
import com.example.myapplication.ui.components.TaskCard
import com.example.myapplication.viewmodel.TaskViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController, viewModel: TaskViewModel = viewModel()) {
    val tasks by viewModel.tasks.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getAllTasks()
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("SmartTasks") }) },
        floatingActionButton = {
            FloatingActionButton(onClick = { /* thêm task mới sau */ }) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            when {
                isLoading -> {
                    CircularProgressIndicator()
                }
                errorMessage != null -> {
                    EmptyView(message = errorMessage!!)
                }
                tasks.isEmpty() -> {
                    EmptyView()
                }
                else -> {
                    LazyColumn(modifier = Modifier.fillMaxSize()) { // fillMaxSize
                        items(tasks) { task ->
                            TaskCard(task) {
                                navController.navigate("detail/${task.id}")
                            }
                        }
                    }
                }
            }
        }
    }
}