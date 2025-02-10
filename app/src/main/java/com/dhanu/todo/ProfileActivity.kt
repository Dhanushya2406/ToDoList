package com.dhanu.todo

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dhanu.todo.databinding.ActivityProfileBinding
import com.dhanu.todo.utils.AuthManager

class ProfileActivity : AppCompatActivity() {


    // Using MVVM
    private lateinit var binding: ActivityProfileBinding
    private lateinit var currentUsername: String
    private lateinit var authManager: AuthManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize AuthManager
        authManager = AuthManager(this)

        // Get the username from the intent
        currentUsername = intent.getStringExtra("USERNAME") ?: ""

        // Check if the username is empty
        if (currentUsername.isEmpty()) {
            Toast.makeText(this, "Invalid user. Please log in again.", Toast.LENGTH_SHORT).show()
            navigateToLogin()
            return
        }

        // Set the username in the TextView
        binding.profileUserName.text = currentUsername

        // Log Out Button
        binding.profileLogout.setOnClickListener {
            authManager.logoutUser()
            Toast.makeText(this, "Logged out successfully.", Toast.LENGTH_SHORT).show()
            navigateToLogin()
        }

        // Delete Account Button
        binding.profileDelete.setOnClickListener {
            if (authManager.deleteUser(currentUsername)) {
                Toast.makeText(this, "Account deleted successfully.", Toast.LENGTH_SHORT).show()
                navigateToLogin()
            } else {
                Toast.makeText(this, "Error deleting account.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
    }
}



//Without MVVM
/*
package com.dhanu.todo
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.dhanu.todo.utils.AuthManager



class ProfileActivity : AppCompatActivity() {

    private lateinit var currentUsername: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        currentUsername = intent.getStringExtra("USERNAME") ?: ""

        val authManager = AuthManager(this)

        val usernameTextView = findViewById<TextView>(R.id.profile_user_name)
        usernameTextView.text = currentUsername

        //For Log Out
        val logoutButton = findViewById<ImageView>(R.id.profile_logout_button)
        logoutButton.setOnClickListener {
            authManager.logoutUser()
            Toast.makeText(this, "Logged out successfully.", Toast.LENGTH_SHORT).show()

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }


        // For Delete Account
        val deleteButton = findViewById<ImageView>(R.id.profile_delete_button)
        deleteButton.setOnClickListener {
            if (authManager.deleteUser()) {
                Toast.makeText(this, "Account deleted successfully.", Toast.LENGTH_SHORT).show()

                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }else{
                Toast.makeText(this, "Error deleting account.", Toast.LENGTH_SHORT).show()
            }
        }

    }

}*/
