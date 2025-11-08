package com.example.myapplication.data.model

data class Task(
    val id: String,
    val title: String,
    val description: String,
    val dueDate: String,
    val priority: String,
    val status: String,
    val subtasks: List<Subtask>,
    val attachments: List<Attachment>
)

data class Subtask(
    val title: String,
    val isCompleted: Boolean
)

data class Attachment(
    val fileName: String,
    val url: String
)
