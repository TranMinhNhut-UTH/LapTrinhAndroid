package com.example.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.model.Task
import com.example.myapplication.data.repository.TaskRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TaskViewModel : ViewModel() {
    private val repository = TaskRepository()

    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    val tasks = _tasks.asStateFlow()

    private val _selectedTask = MutableStateFlow<Task?>(null)
    val selectedTask = _selectedTask.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage = _errorMessage.asStateFlow()

    fun getAllTasks() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val result = repository.getTasks()
                if (result.isEmpty()) {
                    _errorMessage.value = "No Tasks Yet!"
                    _tasks.value = emptyList()
                } else {
                    _errorMessage.value = null
                    _tasks.value = result
                }
            } catch (e: Exception) {
                _errorMessage.value = "Không thể tải dữ liệu."
                _tasks.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun getTaskById(id: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            _selectedTask.value = null
            try {
                val result = repository.getTaskDetail(id)
                if (result == null) {
                    _errorMessage.value = "No Task Found!"
                } else {
                    _errorMessage.value = null
                }
                _selectedTask.value = result
            } catch (e: Exception) {
                _selectedTask.value = null
                _errorMessage.value = "Không thể tải chi tiết công việc."
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun deleteTask(id: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val success = repository.deleteTask(id)
                if (success) {
                    getAllTasks()
                } else {
                    _errorMessage.value = "Xóa thất bại!"
                }
            } catch (e: Exception) {
                _errorMessage.value = "Đã xảy ra lỗi khi xóa."
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun clearSelectedTask() {
        _selectedTask.value = null
        _errorMessage.value = null
    }
}