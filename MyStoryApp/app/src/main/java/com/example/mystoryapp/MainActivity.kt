package com.example.mystoryapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mystoryapp.databinding.ActivityMainBinding
import com.example.mystoryapp.recyclerview.MainActivityAdapter
import com.example.mystoryapp.response.ListStoryItem
import com.example.mystoryapp.utility.Preferences
import com.example.mystoryapp.utility.ViewModelFactory
import com.example.mystoryapp.utility.Result
import com.example.mystoryapp.viewmodels.MainViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var preferences: Preferences
    private lateinit var token: String
    private lateinit var mainViewModel: MainViewModel
    private lateinit var listStories: List<ListStoryItem>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setToken()

        val viewModelFactory = ViewModelFactory.getInstance(this)
        mainViewModel = ViewModelProvider(
            this@MainActivity,
            viewModelFactory
        )[MainViewModel::class.java]


        getStories()

    }

    fun setToken(){
        preferences = Preferences(this)
        token = preferences.Get_Token()!!
        Log.i("result",token)
    }

    fun getStories(){
        mainViewModel.getStories(token).observe(this){
            when(it){
                is Result.Success -> {
                    listStories = it.data.listStory
                    setRecyclerView()
                }
                is Result.Error -> {
                    Toast.makeText(this,it.error,Toast.LENGTH_SHORT).show()
                }
                else ->{}

            }
        }
    }

    fun setRecyclerView(){
        val recyclerView = binding.rvStories
        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = MainActivityAdapter(listStories)
        recyclerView.adapter = adapter
    }
}