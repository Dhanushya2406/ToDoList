package com.dhanu.todo.viewModelFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dhanu.todo.repository.TaskRepository
import com.dhanu.todo.viewModel.TodoListViewModel

class TodoListViewModelFactory(
    private val repository: TaskRepository,
    private val username: String
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TodoListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TodoListViewModel(repository, username) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}