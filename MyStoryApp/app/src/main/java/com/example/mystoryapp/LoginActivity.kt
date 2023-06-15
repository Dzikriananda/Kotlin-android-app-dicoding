package com.example.mystoryapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.mystoryapp.databinding.ActivityLoginBinding
import com.example.mystoryapp.utility.Preferences
import com.example.mystoryapp.utility.ViewModelFactory
import com.example.mystoryapp.viewmodels.LoginViewModel
import com.example.mystoryapp.utility.Result

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var preferences: Preferences
    private lateinit var email: String
    private lateinit var password: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        preferences = Preferences(this)

        checkIfLogin()

        val viewModelFactory = ViewModelFactory.getInstance(this)
        loginViewModel = ViewModelProvider(
            this@LoginActivity,
            viewModelFactory
        )[LoginViewModel::class.java]
        preferences = Preferences(this)

        binding.btnLogin.setOnClickListener {
            Login()
        }

    }

    fun Login(){
        email = binding.edLoginEmail.text.toString()
        password = binding.edLoginPassword.text.toString()
        Log.i("login",email)
        Log.i("login",password)
        loginViewModel.login(email,password).observe(this){
            when(it){
                is Result.Success -> {
                    Toast.makeText(this@LoginActivity, it.data.loginResult.name, Toast.LENGTH_SHORT).show()
                    Log.i("login","sukses")
                    val token = it.data.loginResult.token
                    preferences.Save_Token(token)

                }
                is Result.Error -> {
                    Toast.makeText(this@LoginActivity, it.error, Toast.LENGTH_LONG).show()
                    Log.i("login","gagal")

                }

                else -> {}
            }

        }
    }

    fun checkIfLogin(){
        if(preferences.Get_Token()!=null){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}