package com.dhanu.todo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import com.dhanu.todo.databinding.ActivityRegisterBinding
import com.dhanu.todo.utils.AuthManager
import com.dhanu.todo.viewModel.RegisterViewModel
import com.dhanu.todo.viewModelFactory.RegisterViewModelFactory

class RegisterActivity : AppCompatActivity() {

    //Using MVVM
    private lateinit var binding: ActivityRegisterBinding
    private val viewModel: RegisterViewModel by viewModels {
        RegisterViewModelFactory((application as TodoApplication).repository)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegister.setOnClickListener {
            val username = binding.registerUsername.text.toString()
            val password = binding.registerPassword.text.toString()
            val confirmPassword = binding.registerConfirmPassword.text.toString()

            if (viewModel.registerUser(username, password, confirmPassword)) {
                Toast.makeText(this, "Registration successful!", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Registration failed! Check details.", Toast.LENGTH_SHORT).show()
            }
        } //MVVM


        //Without MVVM

        /*val editUsername = findViewById<EditText>(R.id.register_username)
        val editPassword = findViewById<EditText>(R.id.register_password)
        val editConfirmPassword = findViewById<EditText>(R.id.register_confirm_password)
        val btnRegister = findViewById<Button>(R.id.btn_register)

        val authManager = AuthManager(this)

        btnRegister.setOnClickListener {
            val username = editUsername.text.toString()
            val password = editPassword.text.toString()
            val confirmPassword = editConfirmPassword.text.toString()

            if (authManager.registerUser(username,password,confirmPassword)){
                Toast.makeText(this, "Registration successful!", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }else{
                Toast.makeText(this, "Registration failed! Check details.", Toast.LENGTH_SHORT).show()
            }

        }*/
    }
}