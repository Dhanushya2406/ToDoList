package com.dhanu.todo.viewModel

import androidx.lifecycle.ViewModel
import com.dhanu.todo.repository.TaskRepository

class RegisterViewModel(private val repository: TaskRepository) : ViewModel() {

    fun registerUser(username: String, password: String, confirmPassword: String): Boolean {
        return repository.registerUser(username, password, confirmPassword)
    }
}