package com.dhanu.todo.utils

import android.content.Context
import android.content.SharedPreferences
import com.dhanu.todo.model.Task
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class AuthManager(context: Context) {

    //Using MVVM
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
    private val gson = Gson()

    fun registerUser(username: String, password: String, confirmPassword: String): Boolean {
        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            return false
        }

        if (password != confirmPassword) {
            return false
        }

        val editor = sharedPreferences.edit()
        editor.putString("USERNAME", username)
        editor.putString("PASSWORD", password)
        editor.putBoolean("IS_LOGGED_IN", true)
        editor.apply()
        return true
    }

    fun loginUser(username: String, password: String): Boolean {
        val savedUsername = sharedPreferences.getString("USERNAME", null)
        val savedPassword = sharedPreferences.getString("PASSWORD", null)

        return if (savedUsername == username && savedPassword == password) {
            val editor = sharedPreferences.edit()
            editor.putBoolean("IS_LOGGED_IN", true)
            editor.apply()
            true
        } else {
            false
        }
    }

    fun isUserRegistered(): Boolean {
        return sharedPreferences.contains("USERNAME")
    }

    fun saveTasks(username: String, tasks: List<Task>) {
        val editor = sharedPreferences.edit()
        val json = gson.toJson(tasks)
        editor.putString("TASKS_$username", json)
        editor.apply()
    }

    fun getTasks(username: String): List<Task> {
        val json = sharedPreferences.getString("TASKS_$username", null)
        return if (json != null) {
            val type = object : TypeToken<List<Task>>() {}.type
            gson.fromJson(json, type)
        } else {
            emptyList()
        }
}

    fun logoutUser() {
        val editor = sharedPreferences.edit()
        editor.putBoolean("IS_LOGGED_IN", false)
        editor.apply()
    }

    fun deleteUser(username: String): Boolean {
        val editor = sharedPreferences.edit()

        // Remove user-specific data
        editor.remove("USERNAME")
        editor.remove("PASSWORD")
        editor.remove("IS_LOGGED_IN")
        editor.remove("TASKS_$username") // Remove tasks associated with the user

        // Commit changes
        return editor.commit()
    }
    fun isUserLoggedIn(): Boolean {
        return sharedPreferences.getBoolean("IS_LOGGED_IN", false)
    }

    fun getCurrentUsername(): String {
        return sharedPreferences.getString("USERNAME", "") ?: ""
    }




//Without MVVM

    /*private val sharedPreferences: SharedPreferences = context.getSharedPreferences("UserPrefs",Context.MODE_PRIVATE)
    private val gson = Gson()

    // full of Simple app not MVVM architecture!!
    fun registerUser(username: String, password: String, confirmPassword: String): Boolean{
        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()){
            return false
        }

        if (password != confirmPassword){
            return false
        }

        val editor = sharedPreferences.edit()
        editor.putString("USERNAME",username)
        editor.putString("PASSWORD",password)
        editor.putBoolean("IS_LOGGED_IN", true)
        editor.apply()
        return true
    }

    fun loginUser(username: String,password: String): Boolean{
        val savedUsername = sharedPreferences.getString("USERNAME",null)
        val savedPassword = sharedPreferences.getString("PASSWORD", null)

        return if(savedUsername == username && savedPassword == password){
            val editor = sharedPreferences.edit()
            editor.putBoolean("IS_LOGGED_IN", true)
            editor.apply()
            true
        }else{
            false
        }
    }

    fun getCurrentUsername(): String {
        return sharedPreferences.getString("USERNAME", "") ?: ""
    }

    fun isUserLoggedIn(): Boolean{
        return sharedPreferences.getBoolean("IS_LOGGED_IN",false)
    }

    fun isUserRegistered():Boolean{
        return sharedPreferences.contains("USERNAME")
    }

    fun logoutUser() {
        val editor = sharedPreferences.edit()
        editor.putBoolean("IS_LOGGED_IN", false)
        editor.apply()
    }

    fun deleteUser(): Boolean{
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
        return true
    }

    fun saveTasks(username: String, tasks: List<Task>){
        val editor = sharedPreferences.edit()
        val json = gson.toJson(tasks)
        editor.putString("TASKS_$username",json)
        editor.apply()
    }

    fun getTasks(username: String): List<Task>{
        val json = sharedPreferences.getString("TASKS_$username",null)
        return if (json != null) {
            val type = object : TypeToken<List<Task>>() {}.type
            gson.fromJson(json, type)
        } else {
            emptyList()
        }

    }*/
}