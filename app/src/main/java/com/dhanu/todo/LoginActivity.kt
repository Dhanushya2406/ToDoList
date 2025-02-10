package com.dhanu.todo


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.dhanu.todo.databinding.ActivityLoginBinding
import com.dhanu.todo.viewModel.LoginViewModel
import com.dhanu.todo.viewModelFactory.LoginViewModelFactory

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels {
        LoginViewModelFactory((application as TodoApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Login Authentication
        binding.btnLogin.setOnClickListener {
            val username = binding.loginUsername.text.toString()
            val password = binding.loginPassword.text.toString()

            if (!viewModel.isUserRegistered()) {
                Toast.makeText(this, "Account does not exist. Please register first.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (viewModel.loginUser(username, password)) {
                Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, TodoListActivity::class.java)
                intent.putExtra("USERNAME", username)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Invalid credentials. Try again.", Toast.LENGTH_SHORT).show()
            }
        }

        //Don't have an account
        binding.txtRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }



    //Simple app
        /*val etUsername = findViewById<EditText>(R.id.login_username)
        val etPassword = findViewById<EditText>(R.id.login_password)
        val btnLogin = findViewById<Button>(R.id.btn_login)
        val txtRegister = findViewById<TextView>(R.id.txt_register)

        val authManager = AuthManager(this)

        btnLogin.setOnClickListener {

            val username = etUsername.text.toString()
            val password = etPassword.text.toString()

            if (!authManager.isUserRegistered()) {
                Toast.makeText(this, "Account does not exist. Please register first.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (authManager.loginUser(username,password)){
                authManager.saveTasks(username, authManager.getTasks(username))
                Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, TodoListActivity::class.java)
                intent.putExtra("USERNAME", username)
                startActivity(intent)
                finish()
            }else {
                Toast.makeText(this, "Invalid credentials. Try again.", Toast.LENGTH_SHORT).show()
            }
        }

        txtRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }*/

    }
}