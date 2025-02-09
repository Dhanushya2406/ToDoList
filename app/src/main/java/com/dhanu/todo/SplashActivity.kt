package com.dhanu.todo

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.dhanu.todo.databinding.ActivitySplashBinding
import com.dhanu.todo.utils.AuthManager

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    private lateinit var authManager: AuthManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize ViewBinding
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Hide the action bar and set fullscreen
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        supportActionBar?.hide()

        // Initialize AuthManager
        authManager = AuthManager(this)

        // Delay for 3 seconds and then navigate
        window.decorView.postDelayed({
            navigateToNextScreen()
        }, 3000)
    }

    private fun navigateToNextScreen() {
        val username = authManager.getCurrentUsername()
        if (authManager.isUserLoggedIn() && username.isNotEmpty()) {
            // User is already logged in, go to TodoListActivity
            val intent = Intent(this, TodoListActivity::class.java)
            intent.putExtra("USERNAME", username)
            startActivity(intent)
        } else {
            // User is not logged in, go to LoginActivity
            startActivity(Intent(this, LoginActivity::class.java))
        }
        finish()
    }
}

/*
package com.dhanu.todo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.dhanu.todo.utils.AuthManager

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
        actionBar?.hide()

        //This is simple app
        val authManager = AuthManager(this)

        window.decorView.postDelayed({
            val username = authManager.getCurrentUsername()
            if(authManager.isUserLoggedIn() && username.isNotEmpty()){
                // User is already logged in, go to Todo List
                val intent = Intent(this, TodoListActivity::class.java)
                intent.putExtra("USERNAME", username)
                startActivity(intent)
            }else{
                startActivity(Intent(this,LoginActivity::class.java))
            }
            finish()
        },3000)

        setContentView(R.layout.activity_splash)
    }
}*/
