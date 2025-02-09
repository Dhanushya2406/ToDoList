package com.dhanu.todo.repository

import android.content.Context
import com.dhanu.todo.model.Task
import com.dhanu.todo.utils.AuthManager

class TaskRepository (context: Context) {

    // Full of MVVM
    private val authManager = AuthManager(context)

    fun getTasks(username: String): List<Task> {
        return authManager.getTasks(username)
    }

    fun saveTasks(username: String, tasks: List<Task>) {
        authManager.saveTasks(username, tasks)
    }

    fun addTask(username: String, task: Task) {
        val tasks = getTasks(username).toMutableList()
        tasks.add(task)
        saveTasks(username, tasks)
    }

    fun loginUser(username: String, password: String): Boolean {
        return authManager.loginUser(username, password)
    }

    fun registerUser(username: String, password: String, confirmPassword: String): Boolean {
        return authManager.registerUser(username, password, confirmPassword)
    }

    fun isUserRegistered(): Boolean {
        return authManager.isUserRegistered()
    }

}