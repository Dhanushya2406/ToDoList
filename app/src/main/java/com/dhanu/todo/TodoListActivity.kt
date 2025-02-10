package com.dhanu.todo

import android.R
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.dhanu.todo.adapter.TaskAdapter
import com.dhanu.todo.databinding.ActivityTodolistBinding
import com.dhanu.todo.databinding.DialogAddTaskBinding
import com.dhanu.todo.model.Task
import com.dhanu.todo.viewModel.TodoListViewModel
import com.dhanu.todo.viewModelFactory.TodoListViewModelFactory


class TodoListActivity : AppCompatActivity() {

    // Using MVVM
    private lateinit var binding: ActivityTodolistBinding

    private val currentUsername: String by lazy {
        intent.getStringExtra("USERNAME") ?: throw IllegalStateException("Username must be provided")
    }
    private val viewModel: TodoListViewModel by viewModels {
        TodoListViewModelFactory((application as TodoApplication).repository,intent.getStringExtra("USERNAME") ?: "")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTodolistBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = TaskAdapter()
        binding.recyclerview.layoutManager = LinearLayoutManager(this)
        binding.recyclerview.adapter = adapter

        viewModel.tasks.observe(this, Observer {
            adapter.submitList(it)
        })

        // Add a New Task
        binding.fabAddTask.setOnClickListener {
            showAddTaskDialog()
        }

        //Settings
        binding.imgSettings.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            intent.putExtra("USERNAME", currentUsername)
            startActivity(intent)
        }
    }

    private fun showAddTaskDialog() {

        val dialogBinding = DialogAddTaskBinding.inflate(layoutInflater)
        val dialogView = dialogBinding.root

        // Set up the Spinner
        val filterOptions = arrayOf("Work", "Personal", "High Priority", "Medium Priority", "Low Priority")
        val adapter = ArrayAdapter(this, R.layout.simple_spinner_item, filterOptions)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        dialogBinding.tagNewFilter.adapter = adapter

        // Create the AlertDialog
        val dialog = AlertDialog.Builder(this)
            .setTitle("Add Task")
            .setView(dialogView)
            .setPositiveButton("Add") { _, _ ->
                // Get the task details from the dialog
                val newTitle = dialogBinding.taskNewTitle.text.toString()
                val newSelectedFilter = dialogBinding.tagNewFilter.selectedItem as String

                if (newTitle.isNotEmpty()) {
                    // Create a new task
                    val newTask = Task(newSelectedFilter, newTitle)

                    // Add the task via the ViewModel
                    viewModel.addTask(newTask)
                } else {
                    Toast.makeText(this, "Please fill in the description", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .create()

        // Show the dialog
        dialog.show()
    }




    /*//Without MVVM

    private lateinit var taskAdapter: TaskAdapter // Declare TaskAdapter here
    private val taskList = mutableListOf<Task>()
    private lateinit var authManager: AuthManager
    private lateinit var currentUsername: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todolist)

        val fabAdd: FloatingActionButton = findViewById(R.id.fab_add_task)
        val settings: ImageView = findViewById(R.id.img_settings)
        val recyclerview: RecyclerView = findViewById(R.id.recyclerview)

        authManager = AuthManager(this)
        currentUsername =authManager.getCurrentUsername()

        if (currentUsername.isEmpty()) {
            Toast.makeText(this, "Error: No user found!", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        taskList.addAll(authManager.getTasks(currentUsername))

        recyclerview.layoutManager = LinearLayoutManager(this)

        taskAdapter = TaskAdapter(taskList)
        recyclerview.adapter = taskAdapter

        fabAdd.setOnClickListener {
            showAddTaskDialog()
        }

        settings.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            intent.putExtra("USERNAME", currentUsername)
            startActivity(intent)
        }
    }

    private fun showAddTaskDialog() {

        val dialogView = layoutInflater.inflate(R.layout.dialog_add_task, null)

        val tagFilter = dialogView.findViewById<Spinner>(R.id.tag_new_filter)
        val titleInput = dialogView.findViewById<EditText>(R.id.task_new_title)

        val filterOptions = arrayOf("Work", "Personal" , "High Priority", "Medium Priority", "Low Priority")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, filterOptions)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        tagFilter.adapter = adapter

        // Create the dialog
        val dialog = AlertDialog.Builder(this)
            .setTitle("Add Task")
            .setView(dialogView)
            .setPositiveButton("Add") { _, _ ->

                val newTitle = titleInput.text.toString()
                val newSelectedFilter = tagFilter.selectedItem as String

                if (newTitle.isNotEmpty()) {
                    val newTask = Task(newSelectedFilter,newTitle)
                    taskList.add(newTask)
                    taskAdapter.notifyItemInserted(taskList.size - 1)
                    authManager.saveTasks(currentUsername, taskList)
                    Toast.makeText(this, "Task Added: $newTitle \n Filter: $newSelectedFilter", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Please fill in the description", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss() // Close the dialog
            }
            .create()

        // Show the dialog
        dialog.show()
    }*/
}