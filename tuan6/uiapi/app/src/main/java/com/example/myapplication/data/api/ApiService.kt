package com.example.myapplication.data.api

import com.example.myapplication.data.model.Task
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("researchUTH/tasks")
    suspend fun getTasks(): ApiResponse<List<Task>>

    @GET("researchUTH/task/{id}")
    suspend fun getTaskDetail(@Path("id") id: String): ApiResponse<Task>

    @DELETE("researchUTH/task/{id}")
    suspend fun deleteTask(@Path("id") id: String): Response<Unit>
}
