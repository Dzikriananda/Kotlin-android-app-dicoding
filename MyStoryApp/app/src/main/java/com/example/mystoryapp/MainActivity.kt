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
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mystoryapp.databinding.ActivityMainBinding
import com.example.mystoryapp.recyclerview.OnItemClickListener
import com.example.mystoryapp.recyclerview.StoryAdapter
import com.example.mystoryapp.response.ListStoryItem
import com.example.mystoryapp.utility.MainViewModelFactory
import com.example.mystoryapp.utility.Preferences
import com.example.mystoryapp.viewmodels.MainViewModel
import com.example.mystoryapp.utility.Result

class MainActivity : AppCompatActivity(), OnItemClickListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var preferences: Preferences
    private lateinit var token: String
    private lateinit var mainViewModel: MainViewModel
    private  lateinit var adapter: StoryAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        preferences = Preferences(this)

        setToken()


        val viewModelFactory = MainViewModelFactory.getInstance(this)
        mainViewModel = ViewModelProvider(
            this@MainActivity,
            viewModelFactory
        )[MainViewModel::class.java]

        getStories()

        binding.FabButton.setOnClickListener{
            val intent = Intent(this, AddStoryActivity::class.java)
            startActivity(intent)
        }

        binding.actionLogout.setOnClickListener{
            logOutAlert(it)
        }

        binding.actionMaps.setOnClickListener {
            getMap()
            val intent = Intent(this,MapsActivity::class.java)
            startActivity(intent)
        }

    }

    fun setToken(){
        token = preferences.Get_Token()!!
        Log.i("result",token)
    }

    override fun onResume() {
        super.onResume()
        refreshData()
    }


   /* fun getStories(){
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
    } */

    fun getStories(){
        adapter = StoryAdapter(this)
        binding.rvStories.layoutManager =LinearLayoutManager(this)
        binding.rvStories.adapter = adapter
        mainViewModel.story.observe(this,{
            adapter.submitData(lifecycle,it)
        })

        adapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                if (positionStart == 0) {
                    binding.rvStories.scrollToPosition(0)
                }
            }
        })
    }

    /* fun setRecyclerView(){
        val recyclerView = binding.rvStories
        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = MainActivityAdapter(listStories,this)
        recyclerView.adapter = adapter
    } */

    override fun itemclick(data: ListStoryItem) {
        showDetail(data)
    }

    fun showDetail(item: ListStoryItem) {
        val builder = AlertDialog.Builder(this)
        val inflater = layoutInflater
        builder.setTitle("Story Detail")
        val dialogLayout = inflater.inflate(R.layout.detail_story, null)
        val name  = dialogLayout.findViewById<TextView>(R.id.tv_detail_name)
        val description = dialogLayout.findViewById<TextView>(R.id.tv_detail_description)
        val image = dialogLayout.findViewById<ImageView>(R.id.iv_detail_photo)
        val lat = dialogLayout.findViewById<TextView>(R.id.tv_detail_lat)
        val lon = dialogLayout.findViewById<TextView>(R.id.tv_detail_lon)
        val timestamp = dialogLayout.findViewById<TextView>(R.id.tv_detail_timestamp)
        name.text = getString(R.string.detail_name,item.name)
        description.text = getString(R.string.detail_desc,item.description)
        timestamp.text = getString(R.string.detail_time,item.createdAt)
        if(item.lat!=null){
            lat.text= getString(R.string.detail_lat,item.lat)
        }
        else{
            lat.text = getString(R.string.latitude_not_ava)
        }

        if(item.lon!=null){
            lon.text= getString(R.string.detail_lon,item.lon)
        }
        else{
            lon.text = getString(R.string.longitued_not_ava)
        }

        Glide.with(this)
            .load(item.photoUrl)
            .into(image)
        builder.setView(dialogLayout)
        builder.setPositiveButton("OK") { dialogInterface, i ->  }
        builder.show()
    }

    /*
    fun showLoadingIndicator(){
        binding.progressBar.setVisibility(View.VISIBLE)
    }

    fun hideLoadingIndicator(){
        binding.progressBar.setVisibility(View.INVISIBLE)
    }*/

    override fun onBackPressed() {
        val intent = Intent(Intent.ACTION_MAIN)
        intent.addCategory(Intent.CATEGORY_HOME)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    fun logOutAlert(view: View) {
        val builder = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val dialogLayout = inflater.inflate(R.layout.log_out_alert, null)
        builder.setView(dialogLayout)
        builder.setPositiveButton("OK") { dialogInterface, i -> Toast.makeText(applicationContext, "Logout", Toast.LENGTH_SHORT).show();logout() }
        builder.show()
    }

    fun logout(){
        preferences.Logout()
        val intent = Intent(this, LoginRegisterActivity::class.java)
        startActivity(intent)
    }

    private fun refreshData(){
        adapter.refresh()
    }

    fun getMap(){
        mainViewModel.getStoryWithLoc().observe(this){
            when(it){
                is Result.Success -> {
                    Log.i("map",it.data.listStory.toString())
                }
                is Result.Error -> {
                    Log.i("map", it.error)
                }
                else ->{}
            }
        }
    }
}