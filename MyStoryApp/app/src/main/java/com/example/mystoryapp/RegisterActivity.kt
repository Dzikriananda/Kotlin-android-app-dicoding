package com.example.mystoryapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.mystoryapp.databinding.ActivityRegisterBinding
import com.example.mystoryapp.utility.Result
import com.example.mystoryapp.utility.ViewModelFactory
import com.example.mystoryapp.viewmodels.LoginViewModel
import com.example.mystoryapp.viewmodels.RegisterViewModel


class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var registerViewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val viewModelFactory = ViewModelFactory.getInstance(this)
        registerViewModel = ViewModelProvider(
            this@RegisterActivity,
            viewModelFactory
        )[RegisterViewModel::class.java]

        binding.btnRegister.setOnClickListener {
            register()
        }
    }

    fun register(){
        val fullname = binding.edRegisterName.text.toString()
        val email = binding.edRegisterEmail.text.toString()
        val password = binding.edRegisterPassword.text.toString()

        registerViewModel.register(fullname,email,password).observe(this) {
            when (it) {
                is Result.Loading -> {showLoadingIndicator()}
                is Result.Success -> {
                    hideLoadingIndicator()
                    Toast.makeText(this@RegisterActivity, "Register Sukses", Toast.LENGTH_SHORT)
                        .show()
                    finish()
                }
                is Result.Error -> {
                    hideLoadingIndicator()
                    Toast.makeText(this@RegisterActivity, it.error, Toast.LENGTH_LONG).show()
                }
                else -> {}
            }
        }
    }

    fun showLoadingIndicator(){
        if (binding.progressBar.visibility != View.VISIBLE) {
            binding.progressBar.visibility = View.VISIBLE
        }
    }

    fun hideLoadingIndicator(){
        if (binding.progressBar.visibility != View.INVISIBLE) {
            binding.progressBar.visibility = View.INVISIBLE
        }
    }
}