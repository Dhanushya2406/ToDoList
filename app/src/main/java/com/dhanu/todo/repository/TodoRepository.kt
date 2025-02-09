package com.dhanu.todo.repository

import com.dhanu.todo.model.Task
import kotlinx.coroutines.delay

class TodoRepository {

    suspend fun getTasks(username: String, page: Int, pageSize: Int): List<Task> {
        // Simulate a network or database call
        delay(1000) // Simulate delay
        return dummyTasks(username, page, pageSize)
    }

    private fun dummyTasks(username: String, page: Int, pageSize: Int): List<Task> {
        // Generate dummy tasks for demonstration
        val start = (page - 1) * pageSize
        val end = start + pageSize
        return (start until end).map {
            Task(
                title = "Task $it for $username",
                tag = "Filter for task $it"
            )
        }
    }
}