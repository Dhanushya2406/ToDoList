package com.dhanu.todo.viewModel

import androidx.lifecycle.ViewModel
import com.dhanu.todo.repository.TaskRepository

class LoginViewModel(private val repository: TaskRepository) : ViewModel() {

    fun loginUser(username: String, password: String): Boolean {
        return repository.loginUser(username, password)
    }

    fun isUserRegistered(): Boolean {
        return repository.isUserRegistered()
    }
}