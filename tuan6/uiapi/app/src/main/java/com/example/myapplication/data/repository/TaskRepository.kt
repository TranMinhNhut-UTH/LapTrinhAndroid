package com.example.myapplication.data.repository

import com.example.myapplication.data.api.ApiClient
import com.example.myapplication.data.model.Task

class TaskRepository {
    private val apiService = ApiClient.apiService

    suspend fun getTasks(): List<Task> {
        val response = apiService.getTasks()
        return if (response.isSuccess) {
            response.data
        } else {
            emptyList()
        }
    }

    suspend fun getTaskDetail(id: String): Task? {
        val response = apiService.getTaskDetail(id)
        return if (response.isSuccess) {
            response.data
        } else {
            null
        }
    }

    suspend fun deleteTask(id: String): Boolean {
        return apiService.deleteTask(id).isSuccessful
    }
}