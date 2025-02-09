package com.dhanu.todo.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dhanu.todo.model.Task
import com.dhanu.todo.repository.TaskRepository
import kotlinx.coroutines.launch

class TodoListViewModel(private val repository: TaskRepository, private val username: String) : ViewModel() {
    private val _tasks = MutableLiveData<List<Task>>()
    val tasks: LiveData<List<Task>> get() = _tasks

    init {
        loadTasks()
    }

    private fun loadTasks() {
        viewModelScope.launch {
            val taskList = repository.getTasks(username)
            _tasks.value = taskList
        }
    }

    fun addTask(task: Task) {
        viewModelScope.launch {
            repository.addTask(username, task)
            loadTasks()
        }
    }
}

/*
class TodoListViewModel(private val repository: TaskRepository, private val username: String) : ViewModel() {
    private val _tasks = MutableLiveData<List<Task>>()
    val tasks: LiveData<List<Task>> get() = _tasks

    init {
        loadTasks()
    }

    private fun loadTasks() {
        _tasks.value = repository.getTasks(username)
    }

    fun addTask(task: Task) {
        repository.addTask(username, task)
        loadTasks()
    }
}*/
