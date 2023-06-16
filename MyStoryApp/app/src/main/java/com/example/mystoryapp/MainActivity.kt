package com.example.mystoryapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.mystoryapp.databinding.ActivityMainBinding
import com.example.mystoryapp.recyclerview.MainActivityAdapter
import com.example.mystoryapp.recyclerview.OnItemClickListener
import com.example.mystoryapp.response.ListStoryItem
import com.example.mystoryapp.utility.Preferences
import com.example.mystoryapp.utility.ViewModelFactory
import com.example.mystoryapp.utility.Result
import com.example.mystoryapp.viewmodels.MainViewModel

class MainActivity : AppCompatActivity(), OnItemClickListener {
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

        binding.FabButton.setOnClickListener{
            val intent = Intent(this, AddStoryActivity::class.java)
            startActivity(intent)
        }

    }

    fun setToken(){
        preferences = Preferences(this)
        token = preferences.Get_Token()!!
        Log.i("result",token)
    }

    fun getStories(){
        mainViewModel.getStories(token).observe(this){
            when(it){
                is Result.Loading -> {
                    showLoadingIndicator()
                }
                is Result.Success -> {
                    hideLoadingIndicator()
                    listStories = it.data.listStory
                    setRecyclerView()
                }
                is Result.Error -> {
                    hideLoadingIndicator()
                    Toast.makeText(this,it.error,Toast.LENGTH_SHORT).show()
                }
                else ->{}

            }
        }
    }

    fun setRecyclerView(){
        val recyclerView = binding.rvStories
        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = MainActivityAdapter(listStories,this)
        recyclerView.adapter = adapter
    }

    override fun itemclick(position: Int) {
        val item=listStories[position]
        showDetail(item!!)
    }

    fun showDetail(item: ListStoryItem) {
        val builder = AlertDialog.Builder(this)
        val inflater = layoutInflater
        builder.setTitle("Story Detail")
        val dialogLayout = inflater.inflate(R.layout.detail_story, null)
        val name  = dialogLayout.findViewById<TextView>(R.id.tv_detail_name)
        val description = dialogLayout.findViewById<TextView>(R.id.tv_detail_description)
        val image = dialogLayout.findViewById<ImageView>(R.id.iv_detail_photo)
        name.text = item.name
        description.text = item.description
        Glide.with(this)
            .load(item.photoUrl)
            .into(image)
        builder.setView(dialogLayout)
        builder.setPositiveButton("OK") { dialogInterface, i ->  }
        builder.show()
    }

    fun showLoadingIndicator(){
        binding.progressBar.setVisibility(View.VISIBLE)
    }

    fun hideLoadingIndicator(){
        binding.progressBar.setVisibility(View.INVISIBLE)
    }
}