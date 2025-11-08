package com.example.myapplication.data.api

data class ApiResponse<T>(
    val isSuccess: Boolean,
    val message: String,
    val data: T
)
