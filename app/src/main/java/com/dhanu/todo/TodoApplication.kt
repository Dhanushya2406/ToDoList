package com.dhanu.todo

import android.app.Application
import com.dhanu.todo.repository.TaskRepository

class TodoApplication : Application() {
    val repository: TaskRepository by lazy { TaskRepository(this) }
}